package com.renxin.psychology.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.renxin.common.annotation.Excel;
import com.renxin.common.core.domain.BaseEntity;

/**
 * 咨询师团体对象 psy_consultant_team_supervision
 * 
 * @author renxin
 * @date 2024-06-26
 */
@NoArgsConstructor
@TableName("psy_consultant_team_supervision")
public class PsyConsultantTeamSupervision extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 团督主键 */
    private Long id;

    /** 团督标题 */
    @Excel(name = "团督标题")
    private String title;

    /** 团督logo */
    @Excel(name = "团督logo")
    private String logoPicUrl;

    /** 团督明细地址 */
    @Excel(name = "团督明细地址")
    private String detailPicUrl;

    /** 周期 */
    @Excel(name = "周期")
    private String cycle;

    /** 周期次数 */
    @Excel(name = "周期次数")
    private Long cycleNumber;

    /** 带领者咨询师 */
    @Excel(name = "带领者咨询师")
    private String consultantId;

    /** 0:招募中 1:已开始 2:已结束 3:暂停 */
    @Excel(name = "0:招募中 1:已开始 2:已结束 3:暂停")
    private String status;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setLogoPicUrl(String logoPicUrl) 
    {
        this.logoPicUrl = logoPicUrl;
    }

    public String getLogoPicUrl() 
    {
        return logoPicUrl;
    }
    public void setDetailPicUrl(String detailPicUrl) 
    {
        this.detailPicUrl = detailPicUrl;
    }

    public String getDetailPicUrl() 
    {
        return detailPicUrl;
    }
    public void setCycle(String cycle) 
    {
        this.cycle = cycle;
    }

    public String getCycle() 
    {
        return cycle;
    }
    public void setCycleNumber(Long cycleNumber) 
    {
        this.cycleNumber = cycleNumber;
    }

    public Long getCycleNumber() 
    {
        return cycleNumber;
    }
    public void setConsultantId(String consultantId) 
    {
        this.consultantId = consultantId;
    }

    public String getConsultantId() 
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
            .append("id", getId())
            .append("title", getTitle())
            .append("logoPicUrl", getLogoPicUrl())
            .append("detailPicUrl", getDetailPicUrl())
            .append("cycle", getCycle())
            .append("cycleNumber", getCycleNumber())
            .append("consultantId", getConsultantId())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
