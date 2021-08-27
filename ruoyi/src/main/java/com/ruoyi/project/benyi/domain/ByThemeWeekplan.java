package com.ruoyi.project.benyi.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.project.system.domain.ByClass;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 主题整合周计划（根据月计划明细）对象 by_theme_weekplan
 *
 * @author tsbz
 * @date 2020-08-26
 */
public class ByThemeWeekplan extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;

    /**
     * 学校id
     */
    @Excel(name = "学校id")
    private Long schoolid;

    /**
     * 班级编号
     */
    @Excel(name = "班级编号")
    private String classid;

    /**
     * 所属月份
     */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "所属月份", width = 30, dateFormat = "yyyy-MM")
    private Date month;

    /**
     * 所属周
     */
    @Excel(name = "所属周")
    private Long zc;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String status;

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
     * 审核意见
     */
    @Excel(name = "审核意见")
    private String shyj;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createuserid;

    /**
     * 学年学期
     */
    @Excel(name = "学年学期")
    private String xnxq;

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

    public void setMonth(Date month) {
        this.month = month;
    }

    public Date getMonth() {
        return month;
    }

    public void setZc(Long zc) {
        this.zc = zc;
    }

    public Long getZc() {
        return zc;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
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

    public void setShyj(String shyj) {
        this.shyj = shyj;
    }

    public String getShyj() {
        return shyj;
    }

    public void setCreateuserid(Long createuserid) {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() {
        return createuserid;
    }

    public void setXnxq(String xnxq) {
        this.xnxq = xnxq;
    }

    public String getXnxq() {
        return xnxq;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("schoolid", getSchoolid())
                .append("classid", getClassid())
                .append("month", getMonth())
                .append("zc", getZc())
                .append("remark", getRemark())
                .append("status", getStatus())
                .append("spr", getSpr())
                .append("sptime", getSptime())
                .append("shyj", getShyj())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .append("xnxq", getXnxq())
                .append("byClass", getByClass())
                .toString();
    }
}