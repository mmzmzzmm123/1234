package com.renxin.gauge.dto;

import com.renxin.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderQueryDTO extends BaseEntity {
    private String OrderId;
    private Integer status;
    private String userName;
    private BigDecimal lowAmount;
    private BigDecimal highAmount;
}
