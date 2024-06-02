package com.baoli.task.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商户月度任务对象 baoli_biz_month_task_store
 * 
 * @author niujs
 * @date 2024-05-14
 */
public class BaoliBizMonthTaskStore extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 省份 */
    @Excel(name = "省份")
    private String provinceId;

    /** 城市 */
    @Excel(name = "城市")
    private String cityId;

    /** 年份 */
    @Excel(name = "年份")
    private String year;

    /** 月份 */
    @Excel(name = "月份")
    private String month;

    /** 金额 */
    @Excel(name = "金额")
    private Long amount;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 商户 */
    @Excel(name = "商户")
    private Long storeId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProvinceId(String provinceId) 
    {
        this.provinceId = provinceId;
    }

    public String getProvinceId() 
    {
        return provinceId;
    }
    public void setCityId(String cityId) 
    {
        this.cityId = cityId;
    }

    public String getCityId() 
    {
        return cityId;
    }
    public void setYear(String year) 
    {
        this.year = year;
    }

    public String getYear() 
    {
        return year;
    }
    public void setMonth(String month) 
    {
        this.month = month;
    }

    public String getMonth() 
    {
        return month;
    }
    public void setAmount(Long amount) 
    {
        this.amount = amount;
    }

    public Long getAmount() 
    {
        return amount;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setStoreId(Long storeId) 
    {
        this.storeId = storeId;
    }

    public Long getStoreId() 
    {
        return storeId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("provinceId", getProvinceId())
            .append("cityId", getCityId())
            .append("year", getYear())
            .append("month", getMonth())
            .append("amount", getAmount())
            .append("status", getStatus())
            .append("storeId", getStoreId())
            .toString();
    }
}
