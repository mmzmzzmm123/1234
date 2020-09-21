package com.ruoyi.qtjs.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 群体教师评选结果对象 tsbz_qtjspxjg
 *
 * @author ruoyi
 * @date 2020-09-16
 */
public class TsbzQtjspxjg extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 评选方案编号
     */
    @Excel(name = "评选方案编号")
    private Long faid;

    /**
     * 教师编号
     */
    @Excel(name = "教师编号")
    private Long jsid;

    /**
     * 评选类型
     */
    @Excel(name = "评选类型")
    private String pxlx;

    /**
     * 年份
     */
    @Excel(name = "年份")
    private String nf;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createuserid;

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    private  String deptname;

    private BigDecimal bfb;

    public BigDecimal getBfb() {
        return bfb;
    }

    public void setBfb(BigDecimal bfb) {
        this.bfb = bfb;
    }

    public TsbzJsjbxx getTsbzJsjbxx() {
        return tsbzJsjbxx;
    }

    public void setTsbzJsjbxx(TsbzJsjbxx tsbzJsjbxx) {
        this.tsbzJsjbxx = tsbzJsjbxx;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    private TsbzJsjbxx tsbzJsjbxx;
    private String deptid;

    public String getJsxm() {
        return jsxm;
    }

    public void setJsxm(String jsxm) {
        this.jsxm = jsxm;
    }

    private String jsxm;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setFaid(Long faid) {
        this.faid = faid;
    }

    public Long getFaid() {
        return faid;
    }

    public void setJsid(Long jsid) {
        this.jsid = jsid;
    }

    public Long getJsid() {
        return jsid;
    }

    public void setPxlx(String pxlx) {
        this.pxlx = pxlx;
    }

    public String getPxlx() {
        return pxlx;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getNf() {
        return nf;
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
                .append("faid", getFaid())
                .append("jsid", getJsid())
                .append("pxlx", getPxlx())
                .append("nf", getNf())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .append("deptid", getDeptid())
                .append("bfb", getBfb())
                .append("tsbzJsjbxx", getTsbzJsjbxx())
                .append("jsxm", getJsxm())
                .append("deptname", getDeptname())
                .toString();
    }
}
