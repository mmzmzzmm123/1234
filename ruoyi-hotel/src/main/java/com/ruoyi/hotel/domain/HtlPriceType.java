package com.ruoyi.hotel.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 房价类型对象 htl_price_type
 * 
 * @author sucheng
 * @date 2020-12-24
 */
public class HtlPriceType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 房价类别ID */
    private Long priceTypeId;

    /** 酒店ID */
    @Excel(name = "酒店ID")
    private Long hotelId;

    /** 房价类别 */
    @Excel(name = "房价类别")
    private String priceType;

    /** 是否展示 */
    @Excel(name = "是否展示")
    private Integer isVisible;

    public void setPriceTypeId(Long priceTypeId) 
    {
        this.priceTypeId = priceTypeId;
    }

    public Long getPriceTypeId() 
    {
        return priceTypeId;
    }
    public void setHotelId(Long hotelId) 
    {
        this.hotelId = hotelId;
    }

    public Long getHotelId() 
    {
        return hotelId;
    }
    public void setPriceType(String priceType) 
    {
        this.priceType = priceType;
    }

    public String getPriceType() 
    {
        return priceType;
    }
    public void setIsVisible(Integer isVisible) 
    {
        this.isVisible = isVisible;
    }

    public Integer getIsVisible() 
    {
        return isVisible;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("priceTypeId", getPriceTypeId())
            .append("hotelId", getHotelId())
            .append("priceType", getPriceType())
            .append("isVisible", getIsVisible())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
