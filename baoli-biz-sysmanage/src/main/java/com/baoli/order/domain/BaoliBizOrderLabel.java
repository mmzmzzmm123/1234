package com.baoli.order.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单标签管理对象 baoli_biz_order_label
 * 
 * @author niujs
 * @date 2024-04-24
 */
public class BaoliBizOrderLabel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 标签类型 */
    @Excel(name = "标签类型")
    private String labelType;

    /** 发送者 */
    @Excel(name = "发送者")
    private Long sendUserId;

    /** 接收者 */
    @Excel(name = "接收者")
    private Long receiveUserId;

    /** 订单id */
    @Excel(name = "订单id")
    private Long orderId;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    private String orderStatus;
    private String customerName;
    private String orderNumber;
    private Long loanAmount;

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Long loanAmount) {
        this.loanAmount = loanAmount;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setLabelType(String labelType) 
    {
        this.labelType = labelType;
    }

    public String getLabelType() 
    {
        return labelType;
    }
    public void setSendUserId(Long sendUserId) 
    {
        this.sendUserId = sendUserId;
    }

    public Long getSendUserId() 
    {
        return sendUserId;
    }
    public void setReceiveUserId(Long receiveUserId) 
    {
        this.receiveUserId = receiveUserId;
    }

    public Long getReceiveUserId() 
    {
        return receiveUserId;
    }
    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("labelType", getLabelType())
            .append("sendUserId", getSendUserId())
            .append("receiveUserId", getReceiveUserId())
            .append("orderId", getOrderId())
            .append("status", getStatus())
            .append("remark", getRemark())
            .toString();
    }
}
