package com.ruoyi.project.benyi.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 游戏数学学期计划对象 by_math_termplan
 *
 * @author tsbz
 * @date 2020-10-29
 */
public class ByMathTermplan extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 学校ID
     */
    @Excel(name = "学校ID")
    private Long schoolid;

    /**
     * 班级id
     */
    @Excel(name = "班级id")
    private String classid;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;

    /**
     * 开始月份
     */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "开始月份", width = 30, dateFormat = "yyyy-MM")
    private Date startmonth;

    /**
     * 结束月份
     */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "结束月份", width = 30, dateFormat = "yyyy-MM")
    private Date endmonth;

    /**
     * 学年学期
     */
    @Excel(name = "学年学期")
    private String xnxq;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String status;

    /**
     * 审批人
     */
    @Excel(name = "审批人")
    private Integer spr;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStartmonth(Date startmonth) {
        this.startmonth = startmonth;
    }

    public Date getStartmonth() {
        return startmonth;
    }

    public void setEndmonth(Date endmonth) {
        this.endmonth = endmonth;
    }

    public Date getEndmonth() {
        return endmonth;
    }

    public void setXnxq(String xnxq) {
        this.xnxq = xnxq;
    }

    public String getXnxq() {
        return xnxq;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setSpr(Integer spr) {
        this.spr = spr;
    }

    public Integer getSpr() {
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
                .append("schoolid", getSchoolid())
                .append("classid", getClassid())
                .append("name", getName())
                .append("startmonth", getStartmonth())
                .append("endmonth", getEndmonth())
                .append("xnxq", getXnxq())
                .append("status", getStatus())
                .append("spr", getSpr())
                .append("sptime", getSptime())
                .append("spyj", getSpyj())
                .append("remark", getRemark())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .toString();
    }
}
