package com.ruoyi.project.benyi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

public class ByDatetime extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date day;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 月总共天数
     */
    private Long dayscount;

    /**
     * 月份
     */
    @JsonFormat(pattern = "yyyy-MM")
    private Date month;

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Long getDayscount() {
        return dayscount;
    }

    public void setDayscount(Long dayscount) {
        this.dayscount = dayscount;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("day", getDay())
                .append("sort", getSort())
                .append("dayscount", getDayscount())
                .append("month", getMonth())
                .toString();
    }
}
