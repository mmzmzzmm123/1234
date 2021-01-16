package com.ruoyi.jyykhgl.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 其他考核（教研员）对象 tsbz_jyyqtkh
 *
 * @author tsbz
 * @date 2021-01-16
 */
public class TsbzJyyqtkh extends BaseEntity {
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
     * 名称
     */
    @Excel(name = "名称")
    private String mc;

    /**
     * 主题
     */
    @Excel(name = "主题")
    private String zt;

    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sj;

    /**
     * 地点
     */
    @Excel(name = "地点")
    private String dd;

    /**
     * 说明
     */
    @Excel(name = "说明")
    private String sm;

    /**
     * 填报人
     */
    @Excel(name = "填报人")
    private Long createUserid;

    /**
     * 部门编号
     */
    @Excel(name = "部门编号")
    private Long deptId;

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

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getMc() {
        return mc;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getZt() {
        return zt;
    }

    public void setSj(Date sj) {
        this.sj = sj;
    }

    public Date getSj() {
        return sj;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getDd() {
        return dd;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }

    public String getSm() {
        return sm;
    }

    public void setCreateUserid(Long createUserid) {
        this.createUserid = createUserid;
    }

    public Long getCreateUserid() {
        return createUserid;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("rwid", getRwid())
                .append("rwnrlx", getRwnrlx())
                .append("mc", getMc())
                .append("zt", getZt())
                .append("sj", getSj())
                .append("dd", getDd())
                .append("sm", getSm())
                .append("createUserid", getCreateUserid())
                .append("deptId", getDeptId())
                .append("createTime", getCreateTime())
                .toString();
    }
}
