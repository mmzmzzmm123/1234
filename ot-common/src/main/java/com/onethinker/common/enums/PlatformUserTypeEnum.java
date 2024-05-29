package com.onethinker.common.enums;

import lombok.Getter;

/**
 * @author yangyouqi
 * @date 2024/5/19
 */
@Getter
public enum PlatformUserTypeEnum {

    /**
     * 平台用户状态枚举
     */
    WX("微信用户", "com.onethinker.user.platform.impl.WxUserStorage"),

    WEB("网页端用户", "com.onethinker.user.platform.impl.WebUserStorage");

    final String msg;

    final String interfaceClass;

    PlatformUserTypeEnum(String msg, String interfaceClass) {
        this.msg = msg;
        this.interfaceClass = interfaceClass;
    }

}
