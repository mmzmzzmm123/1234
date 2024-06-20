package com.renxin.psychology.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.renxin.common.annotation.Excel;
import com.renxin.common.core.domain.BaseEntity;

/**
 * 账户明细流水对象 psy_consultant_account_record
 * 
 * @author renxin
 * @date 2024-06-20
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("psy_consultant_account_record")
public class PsyConsultantAccountRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long recordId;

    /** 账户ID */
    @Excel(name = "账户ID")
    private Long consultantId;

    /** 1 成功 0 失败 */
    @Excel(name = "1 成功 0 失败")
    private String status;

    /** 0 分成 1 提现 */
    @Excel(name = "0 分成 1 提现")
    private String payType;

    /** 订单ID ：提现订单ID */
    @Excel(name = "订单ID ：提现订单ID")
    private String orderId;

    /** 支付前账户余额 */
    @Excel(name = "支付前账户余额")
    private String accountAmount;

    /** 支付金额 */
    @Excel(name = "支付金额")
    private BigDecimal payAmount;

    /** 支付消息结果 */
    @Excel(name = "支付消息结果")
    private String payMessage;

    /** 0 未删除 1 删除 */
    private String delFlag;

    public void setRecordId(Long recordId) 
    {
        this.recordId = recordId;
    }

    public Long getRecordId() 
    {
        return recordId;
    }
    public void setConsultantId(Long consultantId) 
    {
        this.consultantId = consultantId;
    }

    public Long getConsultantId() 
    {
        return consultantId;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setPayType(String payType) 
    {
        this.payType = payType;
    }

    public String getPayType() 
    {
        return payType;
    }
    public void setOrderId(String orderId) 
    {
        this.orderId = orderId;
    }

    public String getOrderId() 
    {
        return orderId;
    }
    public void setAccountAmount(String accountAmount) 
    {
        this.accountAmount = accountAmount;
    }

    public String getAccountAmount() 
    {
        return accountAmount;
    }
    public void setPayAmount(BigDecimal payAmount) 
    {
        this.payAmount = payAmount;
    }

    public BigDecimal getPayAmount() 
    {
        return payAmount;
    }
    public void setPayMessage(String payMessage) 
    {
        this.payMessage = payMessage;
    }

    public String getPayMessage() 
    {
        return payMessage;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("consultantId", getConsultantId())
            .append("status", getStatus())
            .append("payType", getPayType())
            .append("orderId", getOrderId())
            .append("accountAmount", getAccountAmount())
            .append("payAmount", getPayAmount())
            .append("payMessage", getPayMessage())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
