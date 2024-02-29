package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 车系管理对象 vehicle_series
 * 
 * @author carol
 * @date 2024-02-29
 */
public class VehicleSeries extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 品牌id */
    @Excel(name = "品牌id")
    private Long brandId;

    /** 车系名 */
    @Excel(name = "车系名")
    private String name;

    /** 指导价 */
    @Excel(name = "指导价")
    private BigDecimal price;

    /** 车系图片 */
    @Excel(name = "车系图片")
    private String url;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setBrandId(Long brandId) 
    {
        this.brandId = brandId;
    }

    public Long getBrandId() 
    {
        return brandId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("brandId", getBrandId())
            .append("name", getName())
            .append("price", getPrice())
            .append("url", getUrl())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
