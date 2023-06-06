package com.ruoyi.office.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class GetRoomPriceVo {
    private Long roomId;
    private Date startTime;
    private Date endTime;
    private BigDecimal totalAmount;
}
