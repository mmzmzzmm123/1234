package com.ruoyi.office.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 房间套餐价格对象 t_room_package
 * 
 * @author ruoyi
 * @date 2023-09-14
 */
public class TRoomPackage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** room_id */
    @Excel(name = "room_id")
    private Long roomId;

    /** 套餐名称 */
    @Excel(name = "套餐名称")
    private String name;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal price;

    /** 会员单价 */
    @Excel(name = "会员单价")
    private BigDecimal memberPrice;

    /** 套餐时间，4小时起 */
    @Excel(name = "套餐时间，4小时起")
    private Long minutes;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setRoomId(Long roomId) 
    {
        this.roomId = roomId;
    }

    public Long getRoomId() 
    {
        return roomId;
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
    public void setMemberPrice(BigDecimal memberPrice) 
    {
        this.memberPrice = memberPrice;
    }

    public BigDecimal getMemberPrice() 
    {
        return memberPrice;
    }
    public void setMinutes(Long minutes) 
    {
        this.minutes = minutes;
    }

    public Long getMinutes() 
    {
        return minutes;
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
            .append("roomId", getRoomId())
            .append("name", getName())
            .append("price", getPrice())
            .append("memberPrice", getMemberPrice())
            .append("minutes", getMinutes())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
