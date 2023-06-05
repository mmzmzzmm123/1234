package com.ruoyi.office.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BuyCouponReq {
    private Long userId;
    private Long counponId;
    private BigDecimal price;
}
