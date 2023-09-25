package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * @author LAM
 * @date 2023/9/21 20:24
 */
@Getter
public enum SysYesNoEnums {

    YES("Y","是"),
    NO("N", "否");

    private String code;
    private String desc;

    SysYesNoEnums(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
