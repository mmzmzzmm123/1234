package com.onethinker.common.enums;

import lombok.Getter;

/**
 * @author yangyouqi
 * @date 2024/5/26
 *
 */
@Getter
public enum SysStatusTypeEnum {

    /**
     * 系统状态枚举
     */
    STATUS_TYPE_ENABLED(0,"启用"),

    STATUS_TYPE_DISABLE(1,"禁用"),

    STATUS_TYPE_DELETE(4,"删除");

    int code;

    String desc;

    SysStatusTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
