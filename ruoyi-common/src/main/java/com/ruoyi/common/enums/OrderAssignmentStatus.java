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


//    UNACCEPTED(1, "未接单"),
//    ACCEPTED(2, "已接单"),
//    CANCELLED(3, "申请撤销"),
//    CANCELLED1(3, "已撤销"),
//    /**
//     * 已打烊
//     */
//    SHOP_SAMPLE(5, "已打样"),
//    /**
//     * 制作中
//     */
//    IN_PRODUCTION(6, "制作中"),
//    IN_PRODUCTION1(6, "制作完成"),
//    /**
//     * 已发货
//     */
//    SHIPPED(8, "已发货"),
//
//
//    DEPOSIT_REFUNDED(4, "保证金已退还"), // Deposit Refunded
//    PAYMENT_RECEIVED(5, "货款已到账"), // Payment Received
//    COMPLETED(9, "已完成"),//这里指的订单完成
//    TIMEOUT(12, "已超时");

    UNACCEPTED(1, "未接单"),                  // 1
    ACCEPTED(2, "已接单"),                    // 2

    CANCEL_REQUESTED(3, "申请撤销"),           // 3
    CANCELLED(4, "已撤销"),                   // 4

    SHOP_CLOSED(5, "已打烊"),                 // 5

    IN_PRODUCTION(6, "制作中"),                // 6
    PRODUCTION_COMPLETED(7, "制作完成"),       // 7

    SHIPPED(8, "已发货"),                     // 8
    DEPOSIT_REFUNDED(9, "保证金已退还"),       // 9
    PAYMENT_RECEIVED(10, "货款已到账"),        // 10

    SHIPPED_RETURNED(11, "确认退货"),          // 11
    SHIPPED_RECEIVED(12, "确认收货"),          // 12

    COMPLETED(13, "已完成"),                  // 13
    TIMEOUT(14, "已超时");                    // 14




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
