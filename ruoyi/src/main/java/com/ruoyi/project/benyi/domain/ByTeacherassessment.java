package com.ruoyi.project.benyi.domain;

import com.ruoyi.project.system.domain.SysDept;
import com.ruoyi.project.system.domain.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 教师月绩效考核对象 by_teacherassessment
 *
 * @author tsbz
 * @date 2021-07-01
 */
public class ByTeacherassessment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 教师编号
     */
    @Excel(name = "教师编号")
    private Long jsid;

    /**
     * 班级编号
     */
    @Excel(name = "班级编号")
    private String classid;

    /**
     * 所在部门
     */
    @Excel(name = "所在部门")
    private Long deptId;

    /**
     * 考核月份
     */
    @Excel(name = "考核月份")
    private String month;

    /**
     * 一日流程比例
     */
    @Excel(name = "一日流程比例")
    private Double yrlcbl;

    /**
     * 教师考勤比例
     */
    @Excel(name = "教师考勤比例")
    private Double jskqbl;

    /**
     * 幼儿考勤比例
     */
    @Excel(name = "幼儿考勤比例")
    private Double yekqbl;

    /**
     * 事故比例
     */
    @Excel(name = "事故比例")
    private Double sgbl;

    /**
     * 卫生比例
     */
    @Excel(name = "卫生比例")
    private Double wsbl;

    /**
     * 总分
     */
    @Excel(name = "总分")
    private Double zfbl;

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    private SysUser sysUser;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setJsid(Long jsid) {
        this.jsid = jsid;
    }

    public Long getJsid() {
        return jsid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassid() {
        return classid;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonth() {
        return month;
    }

    public void setYrlcbl(Double yrlcbl) {
        this.yrlcbl = yrlcbl;
    }

    public Double getYrlcbl() {
        return yrlcbl;
    }

    public void setJskqbl(Double jskqbl) {
        this.jskqbl = jskqbl;
    }

    public Double getJskqbl() {
        return jskqbl;
    }

    public void setYekqbl(Double yekqbl) {
        this.yekqbl = yekqbl;
    }

    public Double getYekqbl() {
        return yekqbl;
    }

    public void setSgbl(Double sgbl) {
        this.sgbl = sgbl;
    }

    public Double getSgbl() {
        return sgbl;
    }

    public void setWsbl(Double wsbl) {
        this.wsbl = wsbl;
    }

    public Double getWsbl() {
        return wsbl;
    }

    public void setZfbl(Double zfbl) {
        this.zfbl = zfbl;
    }

    public Double getZfbl() {
        return zfbl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("jsid", getJsid())
                .append("classid", getClassid())
                .append("deptId", getDeptId())
                .append("month", getMonth())
                .append("yrlcbl", getYrlcbl())
                .append("jskqbl", getJskqbl())
                .append("yekqbl", getYekqbl())
                .append("sgbl", getSgbl())
                .append("wsbl", getWsbl())
                .append("zfbl", getZfbl())
                .append("createTime", getCreateTime())
                .append("sysUser",getSysUser())
                .toString();
    }
}
