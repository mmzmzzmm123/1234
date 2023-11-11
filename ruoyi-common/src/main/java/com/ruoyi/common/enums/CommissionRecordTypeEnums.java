package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * @author LAM
 * @date 2023/11/6 0:18
 */
@Getter
public enum CommissionRecordTypeEnums {

    ENTRY("0","入账"),
    WITHDRAW("1", "提现");

    private String code;
    private String desc;

    CommissionRecordTypeEnums(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
