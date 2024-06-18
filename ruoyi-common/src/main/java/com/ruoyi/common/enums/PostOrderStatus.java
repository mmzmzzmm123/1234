package com.ruoyi.common.enums;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-18 14:55:56
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-18     张李鑫                     1.0         1.0 Version
 */
public enum PostOrderStatus {

    /**
     * 已接单
     */
    ORDER_RECEIVED(1, "已接单"),

    /**
     * 未接单
     */
    ORDER_PENDING(2, "未接单"),

    /**
     * 已打烊
     */
    SHOP_CLOSED(3, "已打烊"),

    /**
     * 制作中
     */
    IN_PRODUCTION(4, "制作中"),

    /**
     * 已发货
     */
    SHIPPED(5, "已发货"),

    /**
     * 已完成
     */
    COMPLETED(6, "已完成"),

    /**
     * 已撤销
     */
    CANCELLED(7, "已撤销"),

    /**
     * 仲裁中
     */
    IN_ARBITRATION(8, "仲裁中"),

    /**
     * 已超时
     */
    TIMEOUT(9, "已超时");

    private final int value;
    private final String remark;

    PostOrderStatus(int value, String remark) {
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
