package com.stdiet.custom.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 每个订单的提成详情
 * */
@Data
public class SysOrderCommisionDayDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**订单成交时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;

    /**客户姓名*/
    private String name;

    /**服务结束时间*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate serverStartDate;

    /**服务结束时间*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate serverEndDate;

    /**服务月数*/
    private Integer serverMonth;

    /**赠送天数*/
    private Integer giveDay;

    /**订单金额*/
    private BigDecimal orderAmount;

    /**订单总提成**/
    private BigDecimal orderCommission;

    /**服务天数*/
    private Integer serverDay;

    /**每天金额*/
    private BigDecimal dayMoney;

    /** 暂停总天数 **/
    private int pauseTotalDay;

    /**每年每月暂停天数*/
    private Map<String, Integer> everyYearMonthPauseDay;

    /**每年每月服务天数**/
    private Map<String, Integer> everyYearMonthServerDay;

    /**每年每月对应金额*/
    private Map<String, BigDecimal> everyYearMonthServerMoney;

    /**每年每月对应提成*/
    private Map<String, BigDecimal> everyYearMonthServerCommission;

    //该笔订单成交的当月的总成交额，用于确定提成比例
    private BigDecimal monthOrderTotalAmount;

    //该笔订单对应提成比例
    private Float commissionRate;
}
