package com.ruoyi.jyykhgl.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 调研指导对象 tsbz_jyydyzd
 *
 * @author tsbz
 * @date 2021-01-15
 */
public class TsbzJyydyzd extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 所属任务
     */
    @Excel(name = "所属任务")
    private Long rwid;

    /**
     * 任务内容
     */
    @Excel(name = "任务内容")
    private String rwnrlx;

    /**
     * 调研名称
     */
    @Excel(name = "调研名称")
    private String dymc;

    /**
     * 调研主题
     */
    @Excel(name = "调研主题")
    private String dyzt;

    /**
     * 调研时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "调研时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dysj;

    /**
     * 调研学校
     */
    @Excel(name = "调研学校")
    private String dyxx;

    /**
     * 调研对象
     */
    @Excel(name = "调研对象")
    private String dydx;

    /**
     * 部门编号
     */
    @Excel(name = "部门编号")
    private Long deptId;

    /**
     * 填报人
     */
    @Excel(name = "填报人")
    private Long createUserid;

    private TsbzJyykhrw tsbzJyykhrw;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setRwid(Long rwid) {
        this.rwid = rwid;
    }

    public Long getRwid() {
        return rwid;
    }

    public void setRwnrlx(String rwnrlx) {
        this.rwnrlx = rwnrlx;
    }

    public String getRwnrlx() {
        return rwnrlx;
    }

    public void setDymc(String dymc) {
        this.dymc = dymc;
    }

    public String getDymc() {
        return dymc;
    }

    public void setDyzt(String dyzt) {
        this.dyzt = dyzt;
    }

    public String getDyzt() {
        return dyzt;
    }

    public void setDysj(Date dysj) {
        this.dysj = dysj;
    }

    public Date getDysj() {
        return dysj;
    }

    public void setDyxx(String dyxx) {
        this.dyxx = dyxx;
    }

    public String getDyxx() {
        return dyxx;
    }

    public void setDydx(String dydx) {
        this.dydx = dydx;
    }

    public String getDydx() {
        return dydx;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setCreateUserid(Long createUserid) {
        this.createUserid = createUserid;
    }

    public Long getCreateUserid() {
        return createUserid;
    }

    public void setTsbzJyykhrw(TsbzJyykhrw tsbzJyykhrw) {
        this.tsbzJyykhrw = tsbzJyykhrw;
    }

    public TsbzJyykhrw getTsbzJyykhrw() {
        return tsbzJyykhrw;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("rwid", getRwid())
                .append("rwnrlx", getRwnrlx())
                .append("dymc", getDymc())
                .append("dyzt", getDyzt())
                .append("dysj", getDysj())
                .append("dyxx", getDyxx())
                .append("dydx", getDydx())
                .append("deptId", getDeptId())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .append("tsbzJyykhrw", getTsbzJyykhrw())
                .toString();
    }
}
