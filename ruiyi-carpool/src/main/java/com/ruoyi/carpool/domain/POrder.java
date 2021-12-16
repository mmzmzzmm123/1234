package com.ruoyi.carpool.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单信息对象 p_order
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
public class POrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** autoID */
    private Long id;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderNum;

    /** 出发地 */
    @Excel(name = "出发地")
    private String departure;

    /** 目的地 */
    @Excel(name = "目的地")
    private String destination;

    /** 出发时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "出发时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date departureTime;

    /** 当前人数 */
    @Excel(name = "当前人数")
    private Integer member;

    /** 订单当前的状态，0：拼单中；1：拼单完成；2：取消拼单 */
    @Excel(name = "订单当前的状态，0：拼单中；1：拼单完成；2：取消拼单")
    private String state;

    /** 逻辑删除：1：删除 */
    @Excel(name = "逻辑删除：1：删除")
    private String isdelete;

    /** 用户唯一平台ID */
    @Excel(name = "用户唯一平台ID")
    private String createUser;

    /** 订单更新人ID，使用逗号分割 */
    @Excel(name = "订单更新人ID，使用逗号分割")
    private String updateUser;

    @Excel(name = "是否接单")
    private String isTake;

    @Excel(name = "司机平台ID")
    private String driverId;

    @Excel(name = "司机姓名")
    private String driverName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrderNum(String orderNum) 
    {
        this.orderNum = orderNum;
    }

    public String getOrderNum() 
    {
        return orderNum;
    }
    public void setDeparture(String departure) 
    {
        this.departure = departure;
    }

    public String getDeparture() 
    {
        return departure;
    }
    public void setDestination(String destination) 
    {
        this.destination = destination;
    }

    public String getDestination() 
    {
        return destination;
    }
    public void setDepartureTime(Date departureTime) 
    {
        this.departureTime = departureTime;
    }

    public Date getDepartureTime() 
    {
        return departureTime;
    }
    public void setMember(Integer member) 
    {
        this.member = member;
    }

    public Integer getMember() 
    {
        return member;
    }
    public void setState(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return state;
    }
    public void setIsdelete(String isdelete)
    {
        this.isdelete = isdelete;
    }

    public String getIsdelete()
    {
        return isdelete;
    }
    public void setCreateUser(String createUser) 
    {
        this.createUser = createUser;
    }

    public String getCreateUser() 
    {
        return createUser;
    }
    public void setUpdateUser(String updateUser) 
    {
        this.updateUser = updateUser;
    }

    public String getUpdateUser() 
    {
        return updateUser;
    }

    public String getIsTake() {
        return isTake;
    }

    public void setIsTake(String isTake) {
        this.isTake = isTake;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderNum", getOrderNum())
            .append("departure", getDeparture())
            .append("destination", getDestination())
            .append("departureTime", getDepartureTime())
            .append("member", getMember())
            .append("state", getState())
            .append("isdelete", getIsdelete())
            .append("isTake", getIsTake())
            .append("driverID", getDriverId())
            .append("driverName", getDriverName())
            .append("createUser", getCreateUser())
            .append("createTime", getCreateTime())
            .append("updateUser", getUpdateUser())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
