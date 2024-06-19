package com.ruoyi.common.enums;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-19 16:09:24
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-19     张李鑫                     1.0         1.0 Version
 */
public enum OrderAssignmentStatus {
    UNACCEPTED(1, "未接单"),
    ACCEPTED(2, "已接单"),
    CANCELLED(3, "撤销"),
    DEPOSIT_REFUNDED(4, "保证金已退还"), // Deposit Refunded
    PAYMENT_RECEIVED(5, "货款已到账"), // Payment Received
    COMPLETED(9, "已完成"),//这里指的订单完成
    IN_ARBITRATION(10, "仲裁中"),
    ARBITRATION_ENDED(11, "仲裁结束"),
    TIMEOUT(12, "已超时");


    private final int value;
    private final String remark;

    OrderAssignmentStatus(int value, String remark) {
        this.value = value;
        this.remark = remark;
    }

    public int getValue() {
        return value;
    }

    public String getRemark() {
        return remark;
    }
}
