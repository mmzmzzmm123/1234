package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 记录用户的提现信息对象 bus_withdrawals
 * 
 * @author ruoyi
 * @date 2024-06-18
 */
public class BusWithdrawals extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 提现记录ID */
    private Long withdrawalId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 提现金额 */
    @Excel(name = "提现金额")
    private BigDecimal amount;

    /** 提现日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "提现日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date withdrawalDate;

    /** 提现状态 (e.g., 审核、审核异常、处理中, 成功, 失败) */
    @Excel(name = "提现状态 (e.g., 审核、审核异常、处理中, 成功, 失败)")
    private String status;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date processedAt;

    /** 提现描述 */
    @Excel(name = "提现描述")
    private String description;

    public void setWithdrawalId(Long withdrawalId) 
    {
        this.withdrawalId = withdrawalId;
    }

    public Long getWithdrawalId() 
    {
        return withdrawalId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setWithdrawalDate(Date withdrawalDate) 
    {
        this.withdrawalDate = withdrawalDate;
    }

    public Date getWithdrawalDate() 
    {
        return withdrawalDate;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setProcessedAt(Date processedAt) 
    {
        this.processedAt = processedAt;
    }

    public Date getProcessedAt() 
    {
        return processedAt;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("withdrawalId", getWithdrawalId())
            .append("userId", getUserId())
            .append("amount", getAmount())
            .append("withdrawalDate", getWithdrawalDate())
            .append("status", getStatus())
            .append("processedAt", getProcessedAt())
            .append("description", getDescription())
            .toString();
    }
}
