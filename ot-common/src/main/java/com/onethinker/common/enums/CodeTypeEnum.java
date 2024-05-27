package com.onethinker.common.enums;

import lombok.Getter;

/**
 * @author yangyouqi
 * @date 2024/5/19
 * 验证码枚举
 */
@Getter
public enum CodeTypeEnum {

    PHONE("手机"),

    MAIL("邮箱"),

    IMAGE("图形");

    String msg;

    CodeTypeEnum(String msg) {
        this.msg = msg;
    }
}
