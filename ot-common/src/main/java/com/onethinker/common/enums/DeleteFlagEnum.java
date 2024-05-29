package com.onethinker.common.enums;

import lombok.Getter;

/**
 * @author yangyouqi
 * @date 2024/5/23
 */
@Getter
public enum DeleteFlagEnum {
    /**
     * 删除状态枚举
     */
    delete(1,"已删除"),
    enabled(0,"正常");

    final int code;
    final String desc;

    DeleteFlagEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
