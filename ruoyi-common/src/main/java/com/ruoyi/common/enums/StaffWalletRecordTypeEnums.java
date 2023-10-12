package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * @author LAM
 * @date 2023/10/10 16:32
 */
@Getter
public enum StaffWalletRecordTypeEnums {

    ENTRY("0", "入账"),
    DEDUCTION("1", "扣款"),
    SETTLEMENT("2","结算");

    private String code;
    private String desc;

    StaffWalletRecordTypeEnums(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
