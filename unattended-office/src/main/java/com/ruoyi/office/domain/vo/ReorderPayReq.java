package com.ruoyi.office.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ReorderPayReq {

    /**
     * 订单id
     */
    private Long orderId;


    /**
     * 支付方式
     */
    private Integer payType;

    /**
     * 使用卡券
     */
    private Long couponId;

}
