package com.stdiet.custom.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EveryMonthTotalAmount {

    private String yearMonth;

    private BigDecimal totalAmount;
}
