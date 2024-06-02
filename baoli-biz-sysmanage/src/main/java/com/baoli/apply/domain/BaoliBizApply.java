package com.baoli.apply.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 申请管理对象 baoli_biz_apply
 * 
 * @author niujs
 * @date 2024-04-21
 */
public class BaoliBizApply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 申请类型 */
    @Excel(name = "申请类型")
    private String applyType;

    /** 申请内容 */
    @Excel(name = "申请内容")
    private String applyContent;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 申请人 */
    @Excel(name = "申请人")
    private Long applicantId;
    private String applicantName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setApplyType(String applyType) 
    {
        this.applyType = applyType;
    }

    public String getApplyType() 
    {
        return applyType;
    }
    public void setApplyContent(String applyContent) 
    {
        this.applyContent = applyContent;
    }

    public String getApplyContent() 
    {
        return applyContent;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setApplicantId(Long applicantId) 
    {
        this.applicantId = applicantId;
    }

    public Long getApplicantId() 
    {
        return applicantId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("applyType", getApplyType())
            .append("applyContent", getApplyContent())
            .append("status", getStatus())
            .append("applicantId", getApplicantId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
