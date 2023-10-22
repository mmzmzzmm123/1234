package com.ruoyi.office.domain.vo;

import lombok.Data;

@Data
public class MiniOrderChargeReq {
    private Long orderId;
    private Long charePackId;

    /**
     * 支付方式
     */
    private Integer payType;

    /**
     * 使用卡券
     */
    private Long couponId;
}
