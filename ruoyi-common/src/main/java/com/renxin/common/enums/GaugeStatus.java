package com.renxin.common.enums;

/**
 * @User hogan
 * @Time 2022/10/24 17:07
 * @e-mail hkcugwh@163.com
 * 测评完成情况
 **/

public enum GaugeStatus {
    FINISHED("已完成" ,1),
    UNFINISHED("未完成" ,2),
    ;

    private String msg;

    private int value;

    GaugeStatus(String msg, int value) {
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
