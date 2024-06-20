package com.renxin.psychology.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.renxin.common.annotation.Excel;
import com.renxin.common.core.domain.BaseEntity;

/**
 * 账户对象 psy_consultant_account
 * 
 * @author renxin
 * @date 2024-06-20
 */
public class PsyConsultantAccount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 账户ID */
    private Long consultantId;

    /** 数字账号ID */
    @Excel(name = "数字账号ID")
    private String accountNumber;

    /** 提现密码 */
    @Excel(name = "提现密码")
    private String withdrawalPassword;

    /** 1 正常 0 未激活 */
    @Excel(name = "1 正常 0 未激活")
    private String status;

    /** 账户余额 */
    @Excel(name = "账户余额")
    private BigDecimal accountAmount;

    /** 0 未删除 1 删除 */
    private String delFlag;

    public void setConsultantId(Long consultantId) 
    {
        this.consultantId = consultantId;
    }

    public Long getConsultantId() 
    {
        return consultantId;
    }
    public void setAccountNumber(String accountNumber) 
    {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() 
    {
        return accountNumber;
    }
    public void setWithdrawalPassword(String withdrawalPassword) 
    {
        this.withdrawalPassword = withdrawalPassword;
    }

    public String getWithdrawalPassword() 
    {
        return withdrawalPassword;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setAccountAmount(BigDecimal accountAmount) 
    {
        this.accountAmount = accountAmount;
    }

    public BigDecimal getAccountAmount() 
    {
        return accountAmount;
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
            .append("consultantId", getConsultantId())
            .append("accountNumber", getAccountNumber())
            .append("withdrawalPassword", getWithdrawalPassword())
            .append("status", getStatus())
            .append("accountAmount", getAccountAmount())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
