package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * @author LAM
 * @date 2023/10/10 12:53
 */
@Getter
public enum UserWalletRecordTypeEnums {

    RECHARGE("0","充值"),
    ORDER("1", "订单"),
    REWARD("2", "打赏"),
    GIFT("3", "礼物"),
    REFUND("4", "退款");

    private String code;
    private String desc;

    UserWalletRecordTypeEnums(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

}
