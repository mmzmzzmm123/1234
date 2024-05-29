package com.onethinker.common.enums;

import lombok.Getter;

/**
 * @author yangyouqi
 * @date 2024/5/29
 */
@Getter
public enum CaptchaTypeEnum {

    /**
     * 纯数字
     */
    MATH("math"),

    /**
     * 字符
     */
    CHAR("char");

    final String value;

    CaptchaTypeEnum(String value) {
        this.value = value;
    }
}
