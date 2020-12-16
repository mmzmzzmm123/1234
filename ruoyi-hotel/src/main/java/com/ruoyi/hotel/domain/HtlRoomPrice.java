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
 * @date 2020-11-22
 */
public class HtlRoomPrice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 房价ID */
    private Long roomRateId;

    /** 酒店ID */
    @Excel(name = "酒店ID")
    private Long hotelId;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Integer orderNum;

    /** 房间类型 */
    @Excel(name = "房间类型")
    private String roomType;

    /** 房价1 */
    @Excel(name = "房价1")
    private BigDecimal roomPrice1;

    /** 房价2 */
    @Excel(name = "房价2")
    private BigDecimal roomPrice2;

    /** 房价3 */
    @Excel(name = "房价3")
    private BigDecimal roomPrice3;

    /** 房价4 */
    @Excel(name = "房价4")
    private BigDecimal roomPrice4;

    public void setRoomRateId(Long roomRateId) 
    {
        this.roomRateId = roomRateId;
    }

    public Long getRoomRateId() 
    {
        return roomRateId;
    }
    public void setHotelId(Long hotelId) 
    {
        this.hotelId = hotelId;
    }

    public Long getHotelId() 
    {
        return hotelId;
    }
    public void setOrderNum(Integer orderNum) 
    {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum() 
    {
        return orderNum;
    }
    public void setRoomType(String roomType) 
    {
        this.roomType = roomType;
    }

    public String getRoomType() 
    {
        return roomType;
    }
    public void setRoomPrice1(BigDecimal roomPrice1) 
    {
        this.roomPrice1 = roomPrice1;
    }

    public BigDecimal getRoomPrice1() 
    {
        return roomPrice1;
    }
    public void setRoomPrice2(BigDecimal roomPrice2) 
    {
        this.roomPrice2 = roomPrice2;
    }

    public BigDecimal getRoomPrice2() 
    {
        return roomPrice2;
    }
    public void setRoomPrice3(BigDecimal roomPrice3) 
    {
        this.roomPrice3 = roomPrice3;
    }

    public BigDecimal getRoomPrice3() 
    {
        return roomPrice3;
    }
    public void setRoomPrice4(BigDecimal roomPrice4) 
    {
        this.roomPrice4 = roomPrice4;
    }

    public BigDecimal getRoomPrice4() 
    {
        return roomPrice4;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("roomRateId", getRoomRateId())
            .append("hotelId", getHotelId())
            .append("orderNum", getOrderNum())
            .append("roomType", getRoomType())
            .append("roomPrice1", getRoomPrice1())
            .append("roomPrice2", getRoomPrice2())
            .append("roomPrice3", getRoomPrice3())
            .append("roomPrice4", getRoomPrice4())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
