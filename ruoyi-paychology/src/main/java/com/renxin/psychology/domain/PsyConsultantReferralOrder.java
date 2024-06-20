package com.renxin.psychology.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.renxin.common.annotation.Excel;
import com.renxin.common.core.domain.BaseEntity;

/**
 * 转介订单对象 psy_consultant_referral_order
 * 
 * @author renxin
 * @date 2024-06-20
 */
public class PsyConsultantReferralOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 转介ID */
    private Long referralId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 转入咨询师ID */
    @Excel(name = "转入咨询师ID")
    private Long consultantId;

    /** 来着咨询师ID */
    @Excel(name = "来着咨询师ID")
    private Long fromConsultId;

    /** 备注 */
    @Excel(name = "备注")
    private String referralRemark;

    /** 1 成功  0 处理中 4失败 */
    @Excel(name = "1 成功  0 处理中 4失败")
    private String status;

    /** 0 未删除 1 删除 */
    private String delFlag;

    public void setReferralId(Long referralId) 
    {
        this.referralId = referralId;
    }

    public Long getReferralId() 
    {
        return referralId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setConsultantId(Long consultantId) 
    {
        this.consultantId = consultantId;
    }

    public Long getConsultantId() 
    {
        return consultantId;
    }
    public void setFromConsultId(Long fromConsultId) 
    {
        this.fromConsultId = fromConsultId;
    }

    public Long getFromConsultId() 
    {
        return fromConsultId;
    }
    public void setReferralRemark(String referralRemark) 
    {
        this.referralRemark = referralRemark;
    }

    public String getReferralRemark() 
    {
        return referralRemark;
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
            .append("referralId", getReferralId())
            .append("userId", getUserId())
            .append("consultantId", getConsultantId())
            .append("fromConsultId", getFromConsultId())
            .append("referralRemark", getReferralRemark())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
