package org.hc.mybatisplus.domain.dto;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hc.mybatisplus.domain.po.User;
import org.hc.mybatisplus.domain.vo.UserVO;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 叽哒嘎叽
 * @since 2024/11/23
 */
@Data
@ApiModel(description = "分页结果")
public class PageDTO<T> {
    @ApiModelProperty("总条数")
    private Long total;
    @ApiModelProperty("总页数")
    private Long pages;
    @ApiModelProperty("集合")
    private List<T> list;

    public static <T, E> PageDTO<T> of(Page<E> page, Class<T> clazz) {
        PageDTO<T> dto = new PageDTO<>();
        dto.setTotal(page.getTotal());
        dto.setPages(page.getPages());
        List<E> records = page.getRecords();
        if (CollUtil.isEmpty(records)) {
            dto.setList(Collections.EMPTY_LIST);
            return dto;
        }
        List<T> vos = BeanUtil.copyToList(records, clazz);
        dto.setList(vos);

        return dto;
    }

    public static <T, E> PageDTO<T> of(Page<E> page, Function<E, T> convertor) {
        PageDTO<T> dto = new PageDTO<>();
        dto.setTotal(page.getTotal());
        dto.setPages(page.getPages());
        List<E> records = page.getRecords();
        if (CollUtil.isEmpty(records)) {
            dto.setList(Collections.EMPTY_LIST);
            return dto;
        }
        List<T> vos = records.stream().map(convertor).collect(Collectors.toList());
        dto.setList(vos);

        return dto;
    }
}
