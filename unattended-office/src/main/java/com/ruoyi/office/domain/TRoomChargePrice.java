package com.ruoyi.office.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 房间续费套餐对象 t_room_charge_price
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
public class TRoomChargePrice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    private String name;

    /** room_id */
    @Excel(name = "room_id")
    private Long roomId;

    /** 续费时长(分钟) */
    @Excel(name = "续费时长(分钟)")
    private Long minutes;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal price;

    /** 会员单价 */
    @Excel(name = "会员单价")
    private BigDecimal memberPrice;

    /** 开始时间 */
    @Excel(name = "开始时间")
    private Long startTime;

    /** 结束时间 */
    @Excel(name = "结束时间")
    private Long stopTime;

    /** 状态(空闲/已预约/使用中/预约超时未使用) */
    @Excel(name = "状态(空闲/已预约/使用中/预约超时未使用)")
    private Long status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setRoomId(Long roomId) 
    {
        this.roomId = roomId;
    }

    public Long getRoomId() 
    {
        return roomId;
    }
    public void setMinutes(Long minutes) 
    {
        this.minutes = minutes;
    }

    public Long getMinutes() 
    {
        return minutes;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setMemberPrice(BigDecimal memberPrice) 
    {
        this.memberPrice = memberPrice;
    }

    public BigDecimal getMemberPrice() 
    {
        return memberPrice;
    }
    public void setStartTime(Long startTime) 
    {
        this.startTime = startTime;
    }

    public Long getStartTime() 
    {
        return startTime;
    }
    public void setStopTime(Long stopTime) 
    {
        this.stopTime = stopTime;
    }

    public Long getStopTime() 
    {
        return stopTime;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("roomId", getRoomId())
            .append("minutes", getMinutes())
            .append("price", getPrice())
            .append("memberPrice", getMemberPrice())
            .append("startTime", getStartTime())
            .append("stopTime", getStopTime())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
