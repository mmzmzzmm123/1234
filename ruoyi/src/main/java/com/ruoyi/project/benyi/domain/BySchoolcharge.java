package com.ruoyi.project.benyi.domain;

import com.ruoyi.project.system.domain.SysDept;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 园所收费标准对象 by_schoolcharge
 *
 * @author tsbz
 * @date 2020-12-10
 */
public class BySchoolcharge extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 园所id
     */
    @Excel(name = "园所id")
    private Long deptId;

    /**
     * 保育费
     */
    @Excel(name = "保育费")
    private Double byf;

    /**
     * 伙食费
     */
    @Excel(name = "伙食费")
    private Double hsf;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createUserid;

    /**
     * 保育费小班
     */
    @Excel(name = "保育费小班")
    private Double byfX;

    /**
     * 伙食费小班
     */
    @Excel(name = "伙食费小班")
    private Double hsfX;

    /**
     * 保育费托班
     */
    @Excel(name = "保育费托班")
    private Double byfT;

    /**
     * 伙食费托班
     */
    @Excel(name = "伙食费托班")
    private Double hsfT;

    /**
     * 保育费中班
     */
    @Excel(name = "保育费中班")
    private Double byfZ;

    /**
     * 伙食费中班
     */
    @Excel(name = "伙食费中班")
    private Double hsfZ;

    private SysDept dept;

    // 幼儿考勤系统属性
    private String month;
    private String name;
    private String classid;
    private Long days;
    private String bjtype;

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

    public void setByf(Double byf) {
        this.byf = byf;
    }

    public Double getByf() {
        return byf;
    }

    public void setHsf(Double hsf) {
        this.hsf = hsf;
    }

    public Double getHsf() {
        return hsf;
    }

    public void setCreateUserid(Long createUserid) {
        this.createUserid = createUserid;
    }

    public Long getCreateUserid() {
        return createUserid;
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public void setByfX(Double byfX) {
        this.byfX = byfX;
    }

    public Double getByfX() {
        return byfX;
    }

    public void setHsfX(Double hsfX) {
        this.hsfX = hsfX;
    }

    public Double getHsfX() {
        return hsfX;
    }

    public void setByfT(Double byfT) {
        this.byfT = byfT;
    }

    public Double getByfT() {
        return byfT;
    }

    public void setHsfT(Double hsfT) {
        this.hsfT = hsfT;
    }

    public Double getHsfT() {
        return hsfT;
    }

    public void setByfZ(Double byfZ) {
        this.byfZ = byfZ;
    }

    public Double getByfZ() {
        return byfZ;
    }

    public void setHsfZ(Double hsfZ) {
        this.hsfZ = hsfZ;
    }

    public Double getHsfZ() {
        return hsfZ;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public Long getDays() {
        return days;
    }

    public void setDays(Long days) {
        this.days = days;
    }

    public String getBjtype() {
        return bjtype;
    }

    public void setBjtype(String bjtype) {
        this.bjtype = bjtype;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("deptId", getDeptId())
                .append("byf", getByf())
                .append("hsf", getHsf())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .append("dept", getDept())
                .append("byfX", getByfX())
                .append("hsfX", getHsfX())
                .append("byfT", getByfT())
                .append("hsfT", getHsfT())
                .append("byfZ", getByfZ())
                .append("hsfZ", getHsfZ())
                .append("month", getMonth())
                .append("name", getName())
                .append("classid", getClassid())
                .append("bjtype", getBjtype())
                .append("days", getDays())
                .toString();
    }
}
