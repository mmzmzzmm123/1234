package com.ruoyi.office.domain.vo;

import lombok.Data;

@Data
public class MiniOrderChargeReq {
    private Long roomId;
    private Long chargePackId;

    /**
     * 支付方式
     */
    private Integer payType;

    /**
     * 使用卡券
     */
    private Long couponId;
}
