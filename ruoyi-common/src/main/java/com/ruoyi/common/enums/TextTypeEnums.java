package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * @author LAM
 * @date 2023/9/24 21:44
 */
@Getter
public enum TextTypeEnums {

    PLATFORM("0","平台协议"),
    STAFF("1", "店员协议");

    private String code;
    private String desc;

    TextTypeEnums(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
