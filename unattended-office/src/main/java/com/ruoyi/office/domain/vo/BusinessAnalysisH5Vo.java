package com.ruoyi.office.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.office.domain.TRoom;
import com.ruoyi.office.domain.TRoomPackage;
import com.ruoyi.office.domain.TRoomPrice;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class BusinessAnalysisH5Vo {
    private BigDecimal todayIncome;

    private BigDecimal thisMonthIncome;

    private BigDecimal totalIncome;

    private BigDecimal todayTurnover;

    private BigDecimal thisMonthTurnover;

    private BigDecimal totalTurnover;

    private Long todayOrderNum;

    private Long thisMonthOrderNum;

    private Long totalOrderNum;
}
