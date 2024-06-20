package com.renxin.psychology.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.renxin.common.annotation.Excel;
import com.renxin.common.core.domain.BaseEntity;

/**
 * 提现订单对象 psy_consultant_withdrawal_order
 * 
 * @author renxin
 * @date 2024-06-20
 */
public class PsyConsultantWithdrawalOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 提现ID */
    private Long withdrawalNo;

    /** 咨询师ID */
    @Excel(name = "咨询师ID")
    private Long consultantId;

    /** 1 成功  0 处理中 4失败 */
    @Excel(name = "1 成功  0 处理中 4失败")
    private String status;

    /** 提现金额 */
    @Excel(name = "提现金额")
    private BigDecimal withdrawalAmount;

    /** 0 未删除 1 删除 */
    private String delFlag;

    public void setWithdrawalNo(Long withdrawalNo) 
    {
        this.withdrawalNo = withdrawalNo;
    }

    public Long getWithdrawalNo() 
    {
        return withdrawalNo;
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
    public void setWithdrawalAmount(BigDecimal withdrawalAmount) 
    {
        this.withdrawalAmount = withdrawalAmount;
    }

    public BigDecimal getWithdrawalAmount() 
    {
        return withdrawalAmount;
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
            .append("withdrawalNo", getWithdrawalNo())
            .append("consultantId", getConsultantId())
            .append("status", getStatus())
            .append("withdrawalAmount", getWithdrawalAmount())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
