package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * @author LAM
 * @date 2023/10/5 22:43
 */
@Getter
public enum PayStateEnums {

    CANCEL("-1","已取消"),
    SUCCESS("0", "已完成"),
    WAIT_PAY("1", "待支付"),
    REFUND("2", "已退款");

    private String code;
    private String desc;

    PayStateEnums(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
