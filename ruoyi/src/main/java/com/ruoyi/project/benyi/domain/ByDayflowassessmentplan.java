package com.ruoyi.project.benyi.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 幼儿园一日流程评估计划对象 by_dayflowassessmentplan
 *
 * @author tsbz
 * @date 2021-02-01
 */
public class ByDayflowassessmentplan extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 计划名称
     */
    private String name;

    /**
     * 评估学校
     */
    @Excel(name = "评估学校")
    private Long deptId;

    /**
     * 评估月份
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "评估月份", width = 30, dateFormat = "yyyy-MM-dd")
    private Date month;

    /**
     * 评估学年学期
     */
    @Excel(name = "评估学年学期")
    private String xnxq;

    /**
     * 评估班级
     */
    @Excel(name = "评估班级")
    private String classid;

    /**
     * 评估内容
     */
    @Excel(name = "评估内容")
    private String connent;

    /**
     * 内容分数
     */
    @Excel(name = "内容分数")
    private Double score;

    /**
     * 班级平均分
     */
    @Excel(name = "班级平均分数")
    private Double classavg;

    /**
     * 评估时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "评估时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date starttime;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createUserid;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Date getMonth() {
        return month;
    }

    public void setXnxq(String xnxq) {
        this.xnxq = xnxq;
    }

    public String getXnxq() {
        return xnxq;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassid() {
        return classid;
    }

    public void setConnent(String connent) {
        this.connent = connent;
    }

    public String getConnent() {
        return connent;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setCreateUserid(Long createUserid) {
        this.createUserid = createUserid;
    }

    public Long getCreateUserid() {
        return createUserid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("deptId", getDeptId())
                .append("month", getMonth())
                .append("xnxq", getXnxq())
                .append("classid", getClassid())
                .append("connent", getConnent())
                .append("score", getScore())
                .append("classavg", getClassavg())
                .append("starttime", getStarttime())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Double getClassavg() {
        return classavg;
    }

    public void setClassavg(Double classavg) {
        this.classavg = classavg;
    }
}
