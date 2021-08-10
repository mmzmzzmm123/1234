package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 快乐+1对象 qz_happy
 * 
 * @author zlx
 * @date 2021-08-10
 */
public class QzHappy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 圈子ID */
    private Long happyId;

    /** 快乐+1 */
    @Excel(name = "快乐+1")
    private String happyContent;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date releaseTime;

    /** 计划发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planReleaseTime;

    /** 是否投稿（0表示投稿，1为系统指定） */
    @Excel(name = "是否投稿", readConverterExp = "0=表示投稿，1为系统指定")
    private String distributeFlag;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 审批人 */
    @Excel(name = "审批人")
    private String auditBy;

    /** 审批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditTime;

    /** 备注 */
    private String signature;

    /** 扩展字段1 */
    private String ext1;

    /** 扩展字段2 */
    private String ext2;

    /** 扩展字段3 */
    private String ext3;

    public void setHappyId(Long happyId) 
    {
        this.happyId = happyId;
    }

    public Long getHappyId() 
    {
        return happyId;
    }
    public void setHappyContent(String happyContent) 
    {
        this.happyContent = happyContent;
    }

    public String getHappyContent() 
    {
        return happyContent;
    }
    public void setReleaseTime(Date releaseTime) 
    {
        this.releaseTime = releaseTime;
    }

    public Date getReleaseTime() 
    {
        return releaseTime;
    }
    public void setPlanReleaseTime(Date planReleaseTime) 
    {
        this.planReleaseTime = planReleaseTime;
    }

    public Date getPlanReleaseTime() 
    {
        return planReleaseTime;
    }
    public void setDistributeFlag(String distributeFlag) 
    {
        this.distributeFlag = distributeFlag;
    }

    public String getDistributeFlag() 
    {
        return distributeFlag;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setAuditBy(String auditBy) 
    {
        this.auditBy = auditBy;
    }

    public String getAuditBy() 
    {
        return auditBy;
    }
    public void setAuditTime(Date auditTime) 
    {
        this.auditTime = auditTime;
    }

    public Date getAuditTime() 
    {
        return auditTime;
    }
    public void setSignature(String signature) 
    {
        this.signature = signature;
    }

    public String getSignature() 
    {
        return signature;
    }
    public void setExt1(String ext1) 
    {
        this.ext1 = ext1;
    }

    public String getExt1() 
    {
        return ext1;
    }
    public void setExt2(String ext2) 
    {
        this.ext2 = ext2;
    }

    public String getExt2() 
    {
        return ext2;
    }
    public void setExt3(String ext3) 
    {
        this.ext3 = ext3;
    }

    public String getExt3() 
    {
        return ext3;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("happyId", getHappyId())
            .append("happyContent", getHappyContent())
            .append("releaseTime", getReleaseTime())
            .append("planReleaseTime", getPlanReleaseTime())
            .append("distributeFlag", getDistributeFlag())
            .append("status", getStatus())
            .append("auditBy", getAuditBy())
            .append("auditTime", getAuditTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("signature", getSignature())
            .append("remark", getRemark())
            .append("ext1", getExt1())
            .append("ext2", getExt2())
            .append("ext3", getExt3())
            .toString();
    }
}
