package com.ruoyi.carpool.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value ="POrder" ,description = "订单模块入参")
public class POrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** autoID */
    @ApiModelProperty(value = "自增id")
    private Long id;

    /** 订单号 */
    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String orderNum;

    /** 出发地 */
    @Excel(name = "出发地")
    @ApiModelProperty(value = "出发地")
    private String departure;

    /** 目的地 */
    @Excel(name = "目的地")
    @ApiModelProperty(value = "目的地")
    private String destination;

    /** 出发时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "出发时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "出发时间")
    private Date departureTime;

    /** 当前人数 */
    @Excel(name = "当前人数")
    @ApiModelProperty(value = "当前人数")
    private Integer member;

    /** 订单当前的状态，0：拼单中；1：拼单完成；2：取消拼单 */
    @Excel(name = "订单当前的状态，0：拼单中；1：拼单完成；2：取消拼单")
    @ApiModelProperty(value = "订单当前的状态，0：拼单中；1：拼单完成；2：取消拼单")
    private String state;

    /** 逻辑删除：1：删除 */
    @Excel(name = "逻辑删除：1：删除")
    @ApiModelProperty(value = "逻辑删除")
    private String isdelete;

    /** 用户唯一平台ID */
    @Excel(name = "用户唯一平台ID")
    @ApiModelProperty(value = "用户唯一平台ID")
    private String createUser;

    /** 订单更新人ID，使用逗号分割 */
    @Excel(name = "订单更新人ID，使用逗号分割")
    @ApiModelProperty(value = "订单更新人ID，使用逗号分割")
    private String updateUser;

    @Excel(name = "是否接单")
    @ApiModelProperty(value = "是否接单")
    private String isTake;

    @Excel(name = "司机平台ID")
    @ApiModelProperty(value = "司机平台ID")
    private String driverId;

    @Excel(name = "司机姓名")
    @ApiModelProperty(value = "司机姓名")
    private String driverName;

    @Excel(name = "司机微信端的openid")
    @ApiModelProperty(value = "司机微信端的openid")
    private String driverOpenId;

    @Excel(name = "发起者姓名")
    @ApiModelProperty(value = "发起者姓名")
    private String createrName;

    @Excel(name = "发起者openId")
    @ApiModelProperty(value = "发起者openId")
    private String createrOpenId;

    @Excel(name = "发起者手机号")
    @ApiModelProperty(value = "发起者手机号")
    private String createrPhone;

    @Excel(name = "参与者openID")
    @ApiModelProperty(value = "参与者openID")
    private String memberOpenId;


    /**
     * 订单成员信息
     */
    private List<POrderMember> orderMemberList;

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

    public String getDriverOpenId() {
        return driverOpenId;
    }

    public void setDriverOpenId(String driverOpenId) {
        this.driverOpenId = driverOpenId;
    }


    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public String getCreaterPhone() {
        return createrPhone;
    }

    public void setCreaterPhone(String createrPhone) {
        this.createrPhone = createrPhone;
    }

    public String getCreaterOpenId() {
        return createrOpenId;
    }

    public void setCreaterOpenId(String createrOpenId) {
        this.createrOpenId = createrOpenId;
    }

    public String getMemberOpenId() {
        return memberOpenId;
    }

    public void setMemberOpenId(String memberOpenId) {
        this.memberOpenId = memberOpenId;
    }

    public List<POrderMember> getOrderMemberList() {
        return orderMemberList;
    }

    public void setOrderMemberList(List<POrderMember> orderMemberList) {
        this.orderMemberList = orderMemberList;
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
            .append("driverOpenId", getDriverOpenId())
            .append("createrName", getCreaterName())
            .append("CreaterPhone", getCreaterPhone())
            .append("createrOpenId", getCreaterOpenId())
            .append("memberOpenId", getMemberOpenId())
            .toString();
    }
}
