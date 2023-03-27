package com.ruoyi.gauge.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 心理咨询订单支付信息对象 psy_order_pay
 * 
 * @author ruoyi
 * @date 2022-10-12
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PsyOrderPay extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 订单号 */
    @Excel(name = "订单号")
    private Long orderId;

    /** 支付状态(1-待支付,2-已支付，3-取消支付) */
    @Excel(name = "支付状态(1-待支付,2-已支付，3-取消支付)")
    private Integer payStatus;

    /** 支付金额 */
    @Excel(name = "支付金额")
    private BigDecimal amount;

    /** 支付ID */
    @Excel(name = "支付ID")
    private String payId;

    /** 支付时间 */
    @Excel(name = "支付时间")
    private Date payTime;

    /** 支付方式 */
    @Excel(name = "支付方式")
    private int payType;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }
    public void setPayStatus(Integer payStatus) 
    {
        this.payStatus = payStatus;
    }

    public Integer getPayStatus() 
    {
        return payStatus;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setPayId(String payId) 
    {
        this.payId = payId;
    }

    public String getPayId() 
    {
        return payId;
    }
    public void setPayTime(Date payTime)
    {
        this.payTime = payTime;
    }

    public Date getPayTime()
    {
        return payTime;
    }

    public int getPayType(){return payType;}

    public void setPayType(int payType) { this.payType = payType; }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("payStatus", getPayStatus())
            .append("amount", getAmount())
            .append("payId", getPayId())
            .append("payTime", getPayTime().toString())
            .append("payType", getPayType())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .toString();
    }
}
