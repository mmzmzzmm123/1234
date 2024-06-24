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

    //把异常的状态放在前面
    CANCEL_REQUESTED( 1, "申请撤销"),    // 1
    CANCELLED( 2, "已撤销"),             // 2
    TIMEOUT( 3, "已超时"),               // 4


    CREATED( 4, "已创建"),               // 8
    PAY_TODO( 5, "待支付"),              // 16
    PAY_PROCESS( 6, "支付中"),           // 32
    PAID( 7, "已支付"),                  // 64

    ORDER_RECEIVED( 8, "已接单"),        // 128


    SHOP_SAMPLE( 9, "已打样"),           //
    SHOP_SAMPLE_CONFIRMED( 10, "确认打样"), //
    CONFIRM_SHIPMENT( 11, "发货确认"), //


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
