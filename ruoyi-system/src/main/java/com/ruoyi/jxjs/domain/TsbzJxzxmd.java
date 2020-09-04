package com.ruoyi.jxjs.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 见习之星名单对象 tsbz_jxzxmd
 *
 * @author ruoyi
 * @date 2020-08-23
 */
public class TsbzJxzxmd extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 教师id
     */
    @Excel(name = "教师id")
    private Long jsid;

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


    private BigDecimal bfb;
    private String faid;
    private TsbzJxjsjbxx tsbzJxjsjbxx;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrdwid() {
        return prdwid;
    }

    public void setPrdwid(String prdwid) {
        this.prdwid = prdwid;
    }

    public String getPrdwmc() {
        return prdwmc;
    }

    public void setPrdwmc(String prdwmc) {
        this.prdwmc = prdwmc;
    }

    public String getJdxid() {
        return jdxid;
    }

    public void setJdxid(String jdxid) {
        this.jdxid = jdxid;
    }

    private String name;
    private String prdwid;
    private String prdwmc;
    private String jdxid;

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

    public BigDecimal getBfb() {
        return bfb;
    }

    public void setBfb(BigDecimal bfb) {
        this.bfb = bfb;
    }

    public String getFaid() {
        return faid;
    }

    public void setFaid(String faid) {
        this.faid = faid;
    }

    public TsbzJxjsjbxx getTsbzJxjsjbxx() {
        return tsbzJxjsjbxx;
    }

    public void setTsbzJxjsjbxx(TsbzJxjsjbxx tsbzJxjsjbxx) {
        this.tsbzJxjsjbxx = tsbzJxjsjbxx;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("jsid", getJsid())
                .append("nf", getNf())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .append("bfb", getBfb())
                .append("faid", getFaid())
                .append("tsbzJxjsjbxx", getTsbzJxjsjbxx())
                .append("name", getName())
                .append("prdwid", getPrdwid())
                .append("prdwmc", getPrdwmc())
                .append("jdxid", getJdxid())
                .toString();
    }
}
