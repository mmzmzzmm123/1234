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

    CREATED( 1, "已创建"),               // 1
    PAY_TODO( 2, "待支付"),              // 2
    PAY_PROCESS( 3, "支付中"),           // 4
    PAID( 4, "已支付"),                  // 8

    CANCEL_REQUESTED( 15, "申请撤销"),    // 16
    CANCELLED( 6, "已撤销"),             // 32
    TIMEOUT( 7, "已超时"),               // 64

    ORDER_RECEIVED( 8, "已接单"),        // 128
    SHOP_SAMPLE( 9, "已打样"),           // 256
    SHOP_SAMPLE_CONFIRMED( 10, "确认打样"), // 512
    CONFIRM_SHIPMENT( 11, "发货确认"), // 512


    AGREED_TO_SHIP( 12, "同意发货"),   // 4096
    SHIPPED_RECEIVED( 13, "确认收货"),   // 8192
    RETURN_REQUESTED( 14, "申请退货"),   // 16384
    SHIPPED_RETURNED( 15, "确认退货"),   // 16384

    COMPLETED( 16, "已完成");
    
    

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
