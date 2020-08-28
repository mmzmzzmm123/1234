package com.ruoyi.project.benyi.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 周计划(家长和教育部门细化)对象 by_planweekitem
 *
 * @author tsbz
 * @date 2020-08-27
 */
public class ByPlanweekitem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 所属计划
     */
    @Excel(name = "所属计划")
    private String wid;

    /**
     * 活动类型
     */
    @Excel(name = "活动类型")
    private String activitytype;

    /**
     * 活动内容
     */
    @Excel(name = "活动内容")
    private String content;

    /**
     * 活动时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "活动时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date activitytime;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createuserid;

    /**
     * 修改人
     */
    @Excel(name = "修改人")
    private Long updateuserid;

    /**
     * 星期几
     */
    @Excel(name = "星期几")
    private String day;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getWid() {
        return wid;
    }

    public void setActivitytype(String activitytype) {
        this.activitytype = activitytype;
    }

    public String getActivitytype() {
        return activitytype;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setActivitytime(Date activitytime) {
        this.activitytime = activitytime;
    }

    public Date getActivitytime() {
        return activitytime;
    }

    public void setCreateuserid(Long createuserid) {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() {
        return createuserid;
    }

    public void setUpdateuserid(Long updateuserid) {
        this.updateuserid = updateuserid;
    }

    public Long getUpdateuserid() {
        return updateuserid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("wid", getWid())
                .append("activitytype", getActivitytype())
                .append("content", getContent())
                .append("activitytime", getActivitytime())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .append("updateuserid", getUpdateuserid())
                .append("updateTime", getUpdateTime())
                .append("day", getDay())
                .toString();
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
