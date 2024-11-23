package org.hc.mybatisplus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author 叽哒嘎叽
 * @since 2024/11/23
 */
@Getter
public enum UserStatusEnum {
    NORMAL(1, "正常"),
    FREEZE(2, "冻结");

    @EnumValue
    private final Integer code;
    @JsonValue
    private final String desc;

    UserStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
