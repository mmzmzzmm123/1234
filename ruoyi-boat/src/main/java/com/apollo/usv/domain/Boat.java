package com.apollo.usv.domain;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 航船对象 usv_boat
 * 
 * @author lwq
 * @date 2021-07-02
 */
public class Boat extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 航船ID */
    private Long id;

    /** 编号 */
    @Excel(name = "编号")
    private String sn;

    /** 厂家 */
    @Excel(name = "厂家")
    private String manufacturer;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contact;

    /** 购买日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "购买日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date purchaseDate;

    /** 电池容量 */
    @Excel(name = "电池容量")
    private Long batteryPower;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSn(String sn) 
    {
        this.sn = sn;
    }

    public String getSn() 
    {
        return sn;
    }
    public void setManufacturer(String manufacturer) 
    {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() 
    {
        return manufacturer;
    }
    public void setContact(String contact) 
    {
        this.contact = contact;
    }

    public String getContact() 
    {
        return contact;
    }
    public void setPurchaseDate(Date purchaseDate) 
    {
        this.purchaseDate = purchaseDate;
    }

    public Date getPurchaseDate() 
    {
        return purchaseDate;
    }
    public void setBatteryPower(Long batteryPower) 
    {
        this.batteryPower = batteryPower;
    }

    public Long getBatteryPower() 
    {
        return batteryPower;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sn", getSn())
            .append("manufacturer", getManufacturer())
            .append("contact", getContact())
            .append("purchaseDate", getPurchaseDate())
            .append("batteryPower", getBatteryPower())
            .toString();
    }
}
