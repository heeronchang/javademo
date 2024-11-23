package org.hc.mybatisplus.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hc.mybatisplus.domain.po.UserInfo;
import org.hc.mybatisplus.enums.UserStatusEnum;

import java.util.List;

/**
 * @author 叽哒嘎叽
 * @since 2024/11/23
 */
@Data
@ApiModel(description = "用户VO")
public class UserVO {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("详细信息")
    private UserInfo info;

    @ApiModelProperty("使用状态（1正常 2冻结）")
    private UserStatusEnum status;

    @ApiModelProperty("账户余额")
    private Integer balance;

    @ApiModelProperty("地址列表")
    private List<AddressVO> addresses;
}
