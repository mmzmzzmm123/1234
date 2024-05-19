package com.onethinker.common.enums;

/**
 * @author yangyouqi
 * @date 2024/5/19
 */
public enum PlatformUserTypeEnum {

    WX("微信用户", "com.onethinker.user.platform.impl.WxUserStorage"),

    WEB("网页端用户", "com.onethinker.user.platform.impl.WebUserStorage");

    final String msg;

    final String interfaceClass;

    PlatformUserTypeEnum(String msg, String interfaceClass) {
        this.msg = msg;
        this.interfaceClass = interfaceClass;
    }

    public String getMsg() {
        return msg;
    }

    public String getInterfaceClass() {
        return interfaceClass;
    }
}
