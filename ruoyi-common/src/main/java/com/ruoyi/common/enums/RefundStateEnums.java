package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * @author LAM
 * @date 2023/10/11 3:14
 */
@Getter
public enum RefundStateEnums {

    CANCEL("-1","已取消"),
    SUCCESS("0", "已退款"),
    APPLY("1", "退款中");

    private String code;
    private String desc;

    RefundStateEnums(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
