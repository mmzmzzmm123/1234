package com.ruoyi.project.benyi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 园历管理(本一)对象 by_calendar
 * 
 * @author tsbz
 * @date 2020-05-10
 */
public class ByCalendar extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 标识 */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 活动类型 */
    @Excel(name = "活动类型")
    private String type;

    /** 活动时间 */
    @Excel(name = "活动时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date activitytime;

    /** 活动结束时间 */
    @Excel(name = "活动结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date activityendtime;

    /** 活动样式颜色 */
    @Excel(name = "活动样式颜色")
    private String stylecolor;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createuserid;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setActivitytime(Date activitytime) 
    {
        this.activitytime = activitytime;
    }

    public Date getActivitytime() 
    {
        return activitytime;
    }
    public void setActivityendtime(Date activityendtime) 
    {
        this.activityendtime = activityendtime;
    }

    public Date getActivityendtime() 
    {
        return activityendtime;
    }
    public void setStylecolor(String stylecolor) 
    {
        this.stylecolor = stylecolor;
    }

    public String getStylecolor() 
    {
        return stylecolor;
    }
    public void setCreateuserid(Long createuserid) 
    {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() 
    {
        return createuserid;
    }
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("type", getType())
            .append("activitytime", getActivitytime())
            .append("activityendtime", getActivityendtime())
            .append("stylecolor", getStylecolor())
            .append("createuserid", getCreateuserid())
            .append("createtime", getCreatetime())
            .toString();
    }
}
