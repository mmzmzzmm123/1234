package com.ruoyi.project.benyi.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.project.system.domain.ByClass;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 主题整合月计划对象 by_theme_monthplan
 *
 * @author tsbz
 * @date 2020-08-25
 */
public class ByThemeMonthplan extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 计划名称
     */
    @Excel(name = "计划名称")
    private String name;

    /**
     * 所属学校
     */
    @Excel(name = "所属学校")
    private Long schoolid;

    /**
     * 所属班级
     */
    @Excel(name = "所属班级")
    private String classid;

    /**
     * 学年学期
     */
    @Excel(name = "学年学期")
    private String xnxq;

    /**
     * 计划月份
     */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "计划月份", width = 30, dateFormat = "yyyy-MM")
    private Date month;

    /**
     * 本月主题
     */
    @Excel(name = "本月主题")
    private String themes;

    /**
     * 本月自定义主题
     */
    @Excel(name = "本月自定义主题")
    private String selfthemes;

    /**
     * 微型课程
     */
    @Excel(name = "微型课程")
    private String wxkc;

    /**
     * 家长支持
     */
    @Excel(name = "家长支持")
    private String support;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remarks;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createuserid;

    /**
     * 审批人
     */
    @Excel(name = "审批人")
    private Long spr;

    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sptime;

    /**
     * 审批意见
     */
    @Excel(name = "审批意见")
    private String spyj;

    /**
     * 当前状态
     */
    @Excel(name = "当前状态")
    private String status;

    public ByClass getByClass() {
        return byClass;
    }

    public void setByClass(ByClass byClass) {
        this.byClass = byClass;
    }

    private ByClass byClass;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSchoolid(Long schoolid) {
        this.schoolid = schoolid;
    }

    public Long getSchoolid() {
        return schoolid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassid() {
        return classid;
    }

    public void setXnxq(String xnxq) {
        this.xnxq = xnxq;
    }

    public String getXnxq() {
        return xnxq;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Date getMonth() {
        return month;
    }

    public void setThemes(String themes) {
        this.themes = themes;
    }

    public String getThemes() {
        return themes;
    }

    public void setSelfthemes(String selfthemes) {
        this.selfthemes = selfthemes;
    }

    public String getSelfthemes() {
        return selfthemes;
    }

    public void setWxkc(String wxkc) {
        this.wxkc = wxkc;
    }

    public String getWxkc() {
        return wxkc;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getSupport() {
        return support;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setCreateuserid(Long createuserid) {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() {
        return createuserid;
    }

    public void setSpr(Long spr) {
        this.spr = spr;
    }

    public Long getSpr() {
        return spr;
    }

    public void setSptime(Date sptime) {
        this.sptime = sptime;
    }

    public Date getSptime() {
        return sptime;
    }

    public void setSpyj(String spyj) {
        this.spyj = spyj;
    }

    public String getSpyj() {
        return spyj;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("schoolid", getSchoolid())
                .append("classid", getClassid())
                .append("xnxq", getXnxq())
                .append("month", getMonth())
                .append("themes", getThemes())
                .append("selfthemes", getSelfthemes())
                .append("wxkc", getWxkc())
                .append("support", getSupport())
                .append("remarks", getRemarks())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .append("spr", getSpr())
                .append("sptime", getSptime())
                .append("spyj", getSpyj())
                .append("status", getStatus())
                .append("byClass", getByClass())
                .toString();
    }
}