package com.ruoyi.hotel.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 房间价格对象 htl_room_price
 * 
 * @author sucheng
 * @date 2020-12-24
 */
public class HtlRoomPrice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 房型ID */
    @Excel(name = "房型ID")
    private Long roomTypeId;

    /** 房价类别ID */
    @Excel(name = "房价类别ID")
    private Long priceTypeId;

    /** 酒店ID */
    @Excel(name = "酒店ID")
    private Long hotelId;

    /** 房价 */
    @Excel(name = "房价")
    private BigDecimal roomPrice;

    public void setRoomTypeId(Long roomTypeId) 
    {
        this.roomTypeId = roomTypeId;
    }

    public Long getRoomTypeId() 
    {
        return roomTypeId;
    }
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
    public void setRoomPrice(BigDecimal roomPrice) 
    {
        this.roomPrice = roomPrice;
    }

    public BigDecimal getRoomPrice() 
    {
        return roomPrice;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("roomTypeId", getRoomTypeId())
            .append("priceTypeId", getPriceTypeId())
            .append("hotelId", getHotelId())
            .append("roomPrice", getRoomPrice())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
