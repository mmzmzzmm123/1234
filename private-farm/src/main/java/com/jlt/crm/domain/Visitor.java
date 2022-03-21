package com.jlt.crm.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 访客信息对象 pr_visitor
 * 
 * @author ruoyi
 * @date 2022-02-08
 */
public class Visitor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 访客Id */
    private Long visitorId;

    /** 访客姓名 */
    @Excel(name = "访客姓名")
    private String visitorName;

    /** 访客电话 */
    @Excel(name = "访客电话")
    private String phone;

    /** 访客事由 */
    @Excel(name = "访客事由")
    private String matter;

    /** 待办事项 */
    @Excel(name = "待办事项")
    private String backlog;

    /** 访问时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date visitTime;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setVisitorId(Long visitorId) 
    {
        this.visitorId = visitorId;
    }

    public Long getVisitorId() 
    {
        return visitorId;
    }
    public void setVisitorName(String visitorName) 
    {
        this.visitorName = visitorName;
    }

    public String getVisitorName() 
    {
        return visitorName;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setMatter(String matter) 
    {
        this.matter = matter;
    }

    public String getMatter() 
    {
        return matter;
    }
    public void setBacklog(String backlog) 
    {
        this.backlog = backlog;
    }

    public String getBacklog() 
    {
        return backlog;
    }
    public void setVisitTime(Date visitTime) 
    {
        this.visitTime = visitTime;
    }

    public Date getVisitTime() 
    {
        return visitTime;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("visitorId", getVisitorId())
            .append("visitorName", getVisitorName())
            .append("phone", getPhone())
            .append("matter", getMatter())
            .append("backlog", getBacklog())
            .append("visitTime", getVisitTime())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
