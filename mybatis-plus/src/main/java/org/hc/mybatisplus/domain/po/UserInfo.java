package org.hc.mybatisplus.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 叽哒嘎叽
 * @since 2024/11/23
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UserInfo {
    private Integer age;
    private String intro;
    private String gender;
}
