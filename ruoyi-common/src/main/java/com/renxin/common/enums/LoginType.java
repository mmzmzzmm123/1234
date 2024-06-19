package com.renxin.common.enums;

public enum LoginType {

    WX("微信登录"),
    ;

    private String type;

    LoginType(String type) {
        this.type = type;
    }
}
