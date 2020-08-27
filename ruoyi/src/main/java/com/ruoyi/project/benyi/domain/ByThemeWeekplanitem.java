package com.ruoyi.project.benyi.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 主题整合周计划明细对象 by_theme_weekplanitem
 *
 * @author tsbz
 * @date 2020-08-26
 */
public class ByThemeWeekplanitem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date daytime;

    /**
     * 周
     */
    @Excel(name = "周")
    private Long zhou;

    /**
     * 活动
     */
    @Excel(name = "活动")
    private String activityid;

    /**
     * 分组性质
     */
    @Excel(name = "分组性质")
    private String fzxz;

    /**
     * 家长支持
     */
    @Excel(name = "家长支持")
    private String jzzc;

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
     * 所属周计划
     */
    @Excel(name = "所属周计划")
    private String wpid;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDaytime(Date daytime) {
        this.daytime = daytime;
    }

    public Date getDaytime() {
        return daytime;
    }

    public void setZhou(Long zhou) {
        this.zhou = zhou;
    }

    public Long getZhou() {
        return zhou;
    }

    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

    public String getActivityid() {
        return activityid;
    }

    public void setFzxz(String fzxz) {
        this.fzxz = fzxz;
    }

    public String getFzxz() {
        return fzxz;
    }

    public void setJzzc(String jzzc) {
        this.jzzc = jzzc;
    }

    public String getJzzc() {
        return jzzc;
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

    public void setWpid(String wpid) {
        this.wpid = wpid;
    }

    public String getWpid() {
        return wpid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("daytime", getDaytime())
                .append("zhou", getZhou())
                .append("activityid", getActivityid())
                .append("fzxz", getFzxz())
                .append("jzzc", getJzzc())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .append("updateuserid", getUpdateuserid())
                .append("updateTime", getUpdateTime())
                .append("wpid", getWpid())
                .toString();
    }
}