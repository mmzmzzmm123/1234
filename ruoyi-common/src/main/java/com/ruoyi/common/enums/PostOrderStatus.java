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


    init(1,"已创建"),

    pay(2,"已支付"),
    /**
     * 未接单
     */
    ORDER_PENDING(3, "未接单"),

    /**
     * 已接单
     */
    ORDER_RECEIVED(4, "已接单"),

    /**
     * 已打烊
     */
    SHOP_CLOSED(5, "已打烊"),

    /**
     * 制作中
     */
    IN_PRODUCTION(6, "制作中"),
    IN_PRODUCTION_COMPLETED(6, "制作完成"),

    CONFIRM(7,"同意发货"),

    /**
     * 已发货
     */
    SHIPPED(8, "已发货"),

//    SHIPPED(8, "已收货"),
//    SHIPPED(8, "拒绝收货"),
//    SHIPPED(8, "退货"),

    /**
     * 已完成
     */
    COMPLETED(9, "已完成"),//这里指的订单完成

    /**
     * 已撤销
     */
    CANCELLED(9, "已撤销"),

    /**
     * 仲裁中
     */
    IN_ARBITRATION(10, "仲裁中"),

    /**
     * 已超时
     */
    TIMEOUT(11, "已超时");

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
