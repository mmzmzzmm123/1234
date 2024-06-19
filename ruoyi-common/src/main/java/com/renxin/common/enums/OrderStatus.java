package com.renxin.common.enums;

/**
 * @User hogan
 * @Time 2022/10/24 17:07
 * @e-mail hkcugwh@163.com
 * 测评完成情况
 **/

public enum OrderStatus {
    CREATE("创建" ,1),
    FINISHED("完成" ,2),
    CLOSED("关闭" ,3),
    ;

    private String msg;

    private int value;

    OrderStatus(String msg, int value) {
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
