package com.ruoyi.project.mr.base.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 考勤日历明细对象 base_calendar_item
 *
 * @author mr
 * @date 2020-01-07
 */
public class BaseCalendarItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** UUID */
    private String id;

    /** 日历id */
    @Excel(name = "日历id")
    private String parentId;

    /** 状态（Y正常 N休息） */
    @Excel(name = "状态", readConverterExp = "Y=正常,N=休息")
    private String work;

    /** 上班时间 */
    @Excel(name = "上班时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date workDay;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getParentId()
    {
        return parentId;
    }
    public void setWork(String work)
    {
        this.work = work;
    }

    public String getWork()
    {
        return work;
    }
    public void setWorkDay(Date workDay)
    {
        this.workDay = workDay;
    }

    public Date getWorkDay()
    {
        return workDay;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("parentId", getParentId())
            .append("work", getWork())
            .append("workDay", getWorkDay())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
