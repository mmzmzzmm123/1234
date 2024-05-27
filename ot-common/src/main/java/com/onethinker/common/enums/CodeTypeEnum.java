package com.onethinker.common.enums;

import lombok.Getter;

/**
 * @author yangyouqi
 * @date 2024/5/19
 * 验证码枚举
 */
@Getter
public enum CodeTypeEnum {

    PHONE("手机验证码"),

    MAIL("邮箱验证码"),

    IMAGE("图形验证码");

    String msg;

    CodeTypeEnum(String msg) {
        this.msg = msg;
    }
}
