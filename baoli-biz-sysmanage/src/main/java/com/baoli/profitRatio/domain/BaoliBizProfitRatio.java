package com.baoli.profitRatio.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 利润率对象 baoli_biz_profit_ratio
 * 
 * @author niujs
 * @date 2024-05-20
 */
public class BaoliBizProfitRatio extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    @Excel(name = "")
    private String year;

    /**  */
    @Excel(name = "")
    private String month;

    /**  */
    @Excel(name = "")
    private String provinceId;

    /**  */
    @Excel(name = "")
    private String cityId;

    private String regionName;
    private String regionType;
    private String monthly;

    public String getMonthly() {
        return monthly;
    }

    public void setMonthly(String monthly) {
        this.monthly = monthly;
    }

    public String getRegionType() {
        return regionType;
    }

    public void setRegionType(String regionType) {
        this.regionType = regionType;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("year", getYear())
            .append("month", getMonth())
            .append("provinceId", getProvinceId())
            .append("cityId", getCityId())
            .toString();
    }
}
