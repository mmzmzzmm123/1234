package com.ruoyi.office.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 微信用户余额对象 t_wx_user_amount
 *
 * @author ruoyi
 * @date 2023-05-30
 */
public class TWxUserAmount extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 微信id
     */
    @Excel(name = "微信id")
    private Long wxUserId;

    /**
     * 商家id(sys_user)
     */
    @Excel(name = "商家id(sys_user)")
    private Long userId;

    /**
     * 余额
     */
    @Excel(name = "余额")
    private BigDecimal amount;

    /**
     * 本金余额
     */
    @Excel(name = "本金余额")
    private BigDecimal cashAmount;

    /**
     * 赠送余额
     */
    @Excel(name = "赠送余额")
    private BigDecimal welfareAmount;

    /**
     * 积分
     */
    @Excel(name = "积分")
    private Long point;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Long status;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setWxUserId(Long wxUserId) {
        this.wxUserId = wxUserId;
    }

    public Long getWxUserId() {
        return wxUserId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setWelfareAmount(BigDecimal welfareAmount) {
        this.welfareAmount = welfareAmount;
    }

    public BigDecimal getWelfareAmount() {
        return welfareAmount;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public Long getPoint() {
        return point;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("wxUserId", getWxUserId())
                .append("userId", getUserId())
                .append("amount", getAmount())
                .append("amount", getCashAmount())
                .append("amount", getWelfareAmount())
                .append("point", getPoint())
                .append("status", getStatus())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
