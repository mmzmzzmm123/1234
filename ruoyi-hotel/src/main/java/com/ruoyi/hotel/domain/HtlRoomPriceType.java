package com.ruoyi.hotel.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 房价类型对象 htl_room_price_type
 * 
 * @author sucheng
 * @date 2020-12-23
 */
public class HtlRoomPriceType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 房价类别ID */
    private Long roomPriceTypeId;

    /** 酒店ID */
    @Excel(name = "酒店ID")
    private Long hotelId;

    /** 房价类别名称 */
    @Excel(name = "房价类别名称")
    private String typeName;

    /** 是否展示类别 */
    @Excel(name = "是否展示类别")
    private Integer isVisible;

    public void setRoomPriceTypeId(Long roomPriceTypeId) 
    {
        this.roomPriceTypeId = roomPriceTypeId;
    }

    public Long getRoomPriceTypeId() 
    {
        return roomPriceTypeId;
    }
    public void setHotelId(Long hotelId) 
    {
        this.hotelId = hotelId;
    }

    public Long getHotelId() 
    {
        return hotelId;
    }
    public void setTypeName(String typeName) 
    {
        this.typeName = typeName;
    }

    public String getTypeName() 
    {
        return typeName;
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
            .append("roomPriceTypeId", getRoomPriceTypeId())
            .append("hotelId", getHotelId())
            .append("typeName", getTypeName())
            .append("isVisible", getIsVisible())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
