package org.hc.mybatisplus.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hc.mybatisplus.domain.po.UserInfo;

/**
 * @author 叽哒嘎叽
 * @since 2024/11/23
 */
@Data
@ApiModel(description = "用户表")
public class UserFormDTO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("注册手机号")
    private String phone;

    @ApiModelProperty("详细信息，JSON风格")
    private UserInfo info;

    @ApiModelProperty("账户余额")
    private Integer balance;
}
