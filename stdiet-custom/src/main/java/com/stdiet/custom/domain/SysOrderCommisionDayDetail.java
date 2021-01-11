package com.stdiet.custom.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 每个订单的提成详情
 * */
public class SysOrderCommisionDayDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**订单成交时间*/
    private LocalDateTime orderTime;

    /**客户姓名*/
    private String name;

    /**服务结束时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "成交时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDate serverStartDate;

    /**服务结束时间*/
    private LocalDate serverEndDate;

    /**服务月数*/
    private Integer serverMonth;

    /**赠送天数*/
    private Integer giveDay;

    /**订单金额*/
    private BigDecimal orderAmount;

    /**服务天数*/
    private Integer serverDay;

    /**每天金额*/
    private BigDecimal dayMoney;

    /**每年每月暂停天数*/
    private Map<String, Integer> everyYearMonthPauseDay;

    /**每年每月服务天数**/
    private Map<String, Integer> everyYearMonthServerDay;

    /**每年每月对应金额*/
    private Map<String, BigDecimal> everyYearMonthServerMoney;

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getServerStartDate() {
        return serverStartDate;
    }

    public void setServerStartDate(LocalDate serverStartDate) {
        this.serverStartDate = serverStartDate;
    }

    public LocalDate getServerEndDate() {
        return serverEndDate;
    }

    public void setServerEndDate(LocalDate serverEndDate) {
        this.serverEndDate = serverEndDate;
    }

    public Integer getServerMonth() {
        return serverMonth;
    }

    public void setServerMonth(Integer serverMonth) {
        this.serverMonth = serverMonth;
    }

    public Integer getGiveDay() {
        return giveDay;
    }

    public void setGiveDay(Integer giveDay) {
        this.giveDay = giveDay;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getServerDay() {
        return serverDay;
    }

    public void setServerDay(Integer serverDay) {
        this.serverDay = serverDay;
    }

    public BigDecimal getDayMoney() {
        return dayMoney;
    }

    public void setDayMoney(BigDecimal dayMoney) {
        this.dayMoney = dayMoney;
    }

    public Map<String, Integer> getEveryYearMonthPauseDay() {
        return everyYearMonthPauseDay;
    }

    public void setEveryYearMonthPauseDay(Map<String, Integer> everyYearMonthPauseDay) {
        this.everyYearMonthPauseDay = everyYearMonthPauseDay;
    }

    public Map<String, Integer> getEveryYearMonthServerDay() {
        return everyYearMonthServerDay;
    }

    public void setEveryYearMonthServerDay(Map<String, Integer> everyYearMonthServerDay) {
        this.everyYearMonthServerDay = everyYearMonthServerDay;
    }

    public Map<String, BigDecimal> getEveryYearMonthServerMoney() {
        return everyYearMonthServerMoney;
    }

    public void setEveryYearMonthServerMoney(Map<String, BigDecimal> everyYearMonthServerMoney) {
        this.everyYearMonthServerMoney = everyYearMonthServerMoney;
    }
}
