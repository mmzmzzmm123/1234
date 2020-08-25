package com.ruoyi.project.benyi.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 主题整合周计划对象 by_theme_weekplan
 *
 * @author tsbz
 * @date 2020-08-25
 */
public class ByThemeWeekplan extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 所属月计划
     */
    @Excel(name = "所属月计划")
    private String mpid;

    /**
     * 周次
     */
    @Excel(name = "周次")
    private Long zc;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date starttime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endtime;

    /**
     * 活动id
     */
    @Excel(name = "活动id")
    private String activityid;

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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setMpid(String mpid) {
        this.mpid = mpid;
    }

    public String getMpid() {
        return mpid;
    }

    public void setZc(Long zc) {
        this.zc = zc;
    }

    public Long getZc() {
        return zc;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

    public String getActivityid() {
        return activityid;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("mpid", getMpid())
                .append("zc", getZc())
                .append("starttime", getStarttime())
                .append("endtime", getEndtime())
                .append("activityid", getActivityid())
                .append("jzzc", getJzzc())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .toString();
    }
}
