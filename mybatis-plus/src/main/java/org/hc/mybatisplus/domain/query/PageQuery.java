package org.hc.mybatisplus.domain.query;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 叽哒嘎叽
 * @since 2024/11/23
 */
@Data
@ApiModel(description = "分页查询条件")
public class PageQuery {
    @ApiModelProperty("页码")
    private Long pageNo;
    @ApiModelProperty("页码")
    private Long pageSize;
    @ApiModelProperty("排序字段")
    private String sortBy;
    @ApiModelProperty("是否升序")
    private Boolean isAsc;

    public <T> Page<T> toPage(OrderItem... items) {
        Page<T> page = Page.of(pageNo, pageSize);
        if (StrUtil.isNotBlank(sortBy)) {
            page.addOrder(new OrderItem(sortBy, isAsc));
        } else if (items != null) {
            page.addOrder(items);
        }
        return page;
    }
}
