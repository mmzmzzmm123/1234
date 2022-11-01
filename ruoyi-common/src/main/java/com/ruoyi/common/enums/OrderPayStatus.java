package com.ruoyi.common.enums;

/**
 * @User hogan
 * @Time 2022/10/24 17:07
 * @e-mail hkcugwh@163.com
 * 测评完成情况
 **/

public enum OrderPayStatus {
    NEED_PAY("待支付" ,1),
    FINISHED("已支付" ,2),
    CLOSED("取消支付" ,3),
    ;

    private String msg;

    private int value;

    OrderPayStatus(String msg, int value) {
        this.msg = msg;
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public int getValue() {
        return value;
    }
}
