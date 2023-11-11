package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * @author LAM
 * @date 2023/10/5 23:26
 */
@Getter
public enum PayWayEnums {

    WEI_XIN_PAY("0", "微信支付"),
    AMOUNT_PAY("1", "余额支付");

    private String code;
    private String desc;

    PayWayEnums(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
