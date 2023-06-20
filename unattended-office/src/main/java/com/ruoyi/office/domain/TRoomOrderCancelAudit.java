package com.ruoyi.office.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 房间预约取消审核对象 t_room_order_cancel_audit
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
public class TRoomOrderCancelAudit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 原订单编号 */
    @Excel(name = "原订单编号")
    private Long orderNo;

    /** 退款申请wx编号 */
    @Excel(name = "退款申请wx编号")
    private Long refundOrderNo;

    /** 退单金额 */
    @Excel(name = "退单金额")
    private BigDecimal refundAmount;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 微信支付退款单号 */
    @Excel(name = "微信支付退款单号")
    private String refundId;

    /** 商户退款单号 */
    @Excel(name = "商户退款单号")
    private String outRefundNo;

    /** 微信支付订单号 */
    @Excel(name = "微信支付订单号")
    private String transactionId;

    /** 商户订单号 */
    @Excel(name = "商户订单号")
    private String outTradeNo;

    /** 退款渠道枚举值：
            ORIGINAL：原路退款
            BALANCE：退回到余额
            OTHER_BALANCE：原账户异常退到其他余额账户
            OTHER_BANKCARD：原银行卡异常退到其他银行卡 */
    @Excel(name = "退款渠道枚举值：ORIGINAL：原路退款; BALANCE：退回到余额  OTHER_BALANCE：原账户异常退到其他余额账户 OTHER_BANKCARD：原银行卡异常退到其他银行卡")
    private String channel;

    /** 退款入账账户 */
    @Excel(name = "退款入账账户")
    private String userReceivedAccount;

    /** 退款成功时间 */
    @Excel(name = "退款成功时间")
    private String successTime;

    /** 退款创建时间 */
    @Excel(name = "退款创建时间")
    private String refundCreateTime;

    /** 退款状态 */
    @Excel(name = "退款状态")
    private String refundStatus;

    /** 订单金额 */
    @Excel(name = "订单金额")
    private Long total;

    /** 退款金额 */
    @Excel(name = "退款金额")
    private Long refund;

    /** 用户支付金额 */
    @Excel(name = "用户支付金额")
    private Long payerTotal;

    /** 用户退款金额 */
    @Excel(name = "用户退款金额")
    private Long payerRefund;

    /** 应结退款金额 */
    @Excel(name = "应结退款金额")
    private Long settlementRefund;

    /** 应结订单金额 */
    @Excel(name = "应结订单金额")
    private Long settlementTotal;

    /** 优惠退款金额 */
    @Excel(name = "优惠退款金额")
    private Long discountRefund;

    /** 退款币种 */
    @Excel(name = "退款币种")
    private String currency;

    /** 手续费退款金额 */
    @Excel(name = "手续费退款金额")
    private Long refundFee;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrderNo(Long orderNo) 
    {
        this.orderNo = orderNo;
    }

    public Long getOrderNo() 
    {
        return orderNo;
    }
    public void setRefundOrderNo(Long refundOrderNo) 
    {
        this.refundOrderNo = refundOrderNo;
    }

    public Long getRefundOrderNo() 
    {
        return refundOrderNo;
    }
    public void setRefundAmount(BigDecimal refundAmount) 
    {
        this.refundAmount = refundAmount;
    }

    public BigDecimal getRefundAmount() 
    {
        return refundAmount;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setRefundId(String refundId) 
    {
        this.refundId = refundId;
    }

    public String getRefundId() 
    {
        return refundId;
    }
    public void setOutRefundNo(String outRefundNo) 
    {
        this.outRefundNo = outRefundNo;
    }

    public String getOutRefundNo() 
    {
        return outRefundNo;
    }
    public void setTransactionId(String transactionId) 
    {
        this.transactionId = transactionId;
    }

    public String getTransactionId() 
    {
        return transactionId;
    }
    public void setOutTradeNo(String outTradeNo) 
    {
        this.outTradeNo = outTradeNo;
    }

    public String getOutTradeNo() 
    {
        return outTradeNo;
    }
    public void setChannel(String channel) 
    {
        this.channel = channel;
    }

    public String getChannel() 
    {
        return channel;
    }
    public void setUserReceivedAccount(String userReceivedAccount) 
    {
        this.userReceivedAccount = userReceivedAccount;
    }

    public String getUserReceivedAccount() 
    {
        return userReceivedAccount;
    }
    public void setSuccessTime(String successTime) 
    {
        this.successTime = successTime;
    }

    public String getSuccessTime() 
    {
        return successTime;
    }
    public void setRefundCreateTime(String refundCreateTime) 
    {
        this.refundCreateTime = refundCreateTime;
    }

    public String getRefundCreateTime() 
    {
        return refundCreateTime;
    }
    public void setRefundStatus(String refundStatus) 
    {
        this.refundStatus = refundStatus;
    }

    public String getRefundStatus() 
    {
        return refundStatus;
    }
    public void setTotal(Long total) 
    {
        this.total = total;
    }

    public Long getTotal() 
    {
        return total;
    }
    public void setRefund(Long refund) 
    {
        this.refund = refund;
    }

    public Long getRefund() 
    {
        return refund;
    }
    public void setPayerTotal(Long payerTotal) 
    {
        this.payerTotal = payerTotal;
    }

    public Long getPayerTotal() 
    {
        return payerTotal;
    }
    public void setPayerRefund(Long payerRefund) 
    {
        this.payerRefund = payerRefund;
    }

    public Long getPayerRefund() 
    {
        return payerRefund;
    }
    public void setSettlementRefund(Long settlementRefund) 
    {
        this.settlementRefund = settlementRefund;
    }

    public Long getSettlementRefund() 
    {
        return settlementRefund;
    }
    public void setSettlementTotal(Long settlementTotal) 
    {
        this.settlementTotal = settlementTotal;
    }

    public Long getSettlementTotal() 
    {
        return settlementTotal;
    }
    public void setDiscountRefund(Long discountRefund) 
    {
        this.discountRefund = discountRefund;
    }

    public Long getDiscountRefund() 
    {
        return discountRefund;
    }
    public void setCurrency(String currency) 
    {
        this.currency = currency;
    }

    public String getCurrency() 
    {
        return currency;
    }
    public void setRefundFee(Long refundFee) 
    {
        this.refundFee = refundFee;
    }

    public Long getRefundFee() 
    {
        return refundFee;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderNo", getOrderNo())
            .append("refundOrderNo", getRefundOrderNo())
            .append("refundAmount", getRefundAmount())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("refundId", getRefundId())
            .append("outRefundNo", getOutRefundNo())
            .append("transactionId", getTransactionId())
            .append("outTradeNo", getOutTradeNo())
            .append("channel", getChannel())
            .append("userReceivedAccount", getUserReceivedAccount())
            .append("successTime", getSuccessTime())
            .append("refundCreateTime", getRefundCreateTime())
            .append("refundStatus", getRefundStatus())
            .append("total", getTotal())
            .append("refund", getRefund())
            .append("payerTotal", getPayerTotal())
            .append("payerRefund", getPayerRefund())
            .append("settlementRefund", getSettlementRefund())
            .append("settlementTotal", getSettlementTotal())
            .append("discountRefund", getDiscountRefund())
            .append("currency", getCurrency())
            .append("refundFee", getRefundFee())
            .toString();
    }
}
