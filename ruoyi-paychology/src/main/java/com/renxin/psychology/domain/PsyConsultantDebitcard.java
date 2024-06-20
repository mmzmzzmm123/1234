package com.renxin.psychology.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.renxin.common.annotation.Excel;
import com.renxin.common.core.domain.BaseEntity;

/**
 * 客户银行卡对象 psy_consultant_debitcard
 * 
 * @author renxin
 * @date 2024-06-20
 */
public class PsyConsultantDebitcard extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 借记卡ID */
    private String cardNumber;

    /** 咨询师ID */
    @Excel(name = "咨询师ID")
    private Long consultantId;

    /** 借记卡所属银行 */
    @Excel(name = "借记卡所属银行")
    private String cardBanck;

    /** 1 正常 0 未激活 */
    @Excel(name = "1 正常 0 未激活")
    private String status;

    /** 0 未删除 1 删除 */
    private String delFlag;

    public void setCardNumber(String cardNumber) 
    {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() 
    {
        return cardNumber;
    }
    public void setConsultantId(Long consultantId) 
    {
        this.consultantId = consultantId;
    }

    public Long getConsultantId() 
    {
        return consultantId;
    }
    public void setCardBanck(String cardBanck) 
    {
        this.cardBanck = cardBanck;
    }

    public String getCardBanck() 
    {
        return cardBanck;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
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
            .append("cardNumber", getCardNumber())
            .append("consultantId", getConsultantId())
            .append("cardBanck", getCardBanck())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
