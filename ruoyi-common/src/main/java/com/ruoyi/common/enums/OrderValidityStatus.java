package com.ruoyi.common.enums;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-18 15:41:49
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-18     张李鑫                     1.0         1.0 Version
 */
public enum OrderValidityStatus {
    /**
     * 正常
     */
    NORMAL(1, "正常"),

    /**
     * 已取消
     */
    CANCELLED(2, "已取消"),
    Arbitrations(3,"仲裁中"),

    /**
     * 已超时
     */
    SAMPLE_TIMEOUT(4, "打样已超时"),
    ORDER_TIMEOUT(5, "订单已超时");


    private final int value;
    private final String remark;

    OrderValidityStatus(int value, String remark) {
        this.value = value;
        this.remark = remark;
    }

    public static String getRemarkByValue(Integer value) {
        OrderValidityStatus[] values = OrderValidityStatus.values();
        for (OrderValidityStatus orderValidityStatus : values) {
            if (value.equals(orderValidityStatus.getValue())) {
                return orderValidityStatus.getRemark();
            }
        }
        return "";
    }

    public int getValue() {
        return value;
    }

    public String getRemark() {
        return remark;
    }
}
