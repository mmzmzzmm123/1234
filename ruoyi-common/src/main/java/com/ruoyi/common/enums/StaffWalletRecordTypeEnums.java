package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * @author LAM
 * @date 2023/10/10 16:32
 */
@Getter
public enum StaffWalletRecordTypeEnums {

    ENTRY("0", "佣金入账"),
    DEDUCTION("1", "平台扣款"),
    SETTLEMENT("2","薪资结算");

    private String code;
    private String desc;

    StaffWalletRecordTypeEnums(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
