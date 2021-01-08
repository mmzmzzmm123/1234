package com.ruoyi.gbxxgl.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 科研课题(干部管理)对象 tsbz_gbkykt
 *
 * @author tsbz
 * @date 2020-12-21
 */
public class TsbzGbkykt extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 干部ID
     */
    @Excel(name = "干部ID")
    private Long gbid;

    /**
     * 研究领域
     */
    @Excel(name = "研究领域")
    private String yjly;

    /**
     * 立项课题名称
     */
    @Excel(name = "立项课题名称")
    private String lxktmc;

    /**
     * 立项课题级别
     */
    @Excel(name = "立项课题级别")
    private String lxktjb;

    /**
     * 立项课题类别
     */
    @Excel(name = "立项课题类别")
    private String lxktlb;

    /**
     * 立项日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "立项日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lxrq;

    /**
     * 结题日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结题日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date jtrq;

    /**
     * 鉴定结果
     */
    @Excel(name = "鉴定结果")
    private String jdjg;

    /**
     * 承担角色
     */
    @Excel(name = "承担角色")
    private String cdjs;

    /**
     * 是否删除
     */
    @Excel(name = "是否删除")
    private String isdel;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createUserid;

    private TsbzGbjbqk tsbzGbjbqk;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setGbid(Long gbid) {
        this.gbid = gbid;
    }

    public Long getGbid() {
        return gbid;
    }

    public void setYjly(String yjly) {
        this.yjly = yjly;
    }

    public String getYjly() {
        return yjly;
    }

    public void setLxktmc(String lxktmc) {
        this.lxktmc = lxktmc;
    }

    public String getLxktmc() {
        return lxktmc;
    }

    public void setLxktjb(String lxktjb) {
        this.lxktjb = lxktjb;
    }

    public String getLxktjb() {
        return lxktjb;
    }

    public void setLxktlb(String lxktlb) {
        this.lxktlb = lxktlb;
    }

    public String getLxktlb() {
        return lxktlb;
    }

    public void setLxrq(Date lxrq) {
        this.lxrq = lxrq;
    }

    public Date getLxrq() {
        return lxrq;
    }

    public void setJtrq(Date jtrq) {
        this.jtrq = jtrq;
    }

    public Date getJtrq() {
        return jtrq;
    }

    public void setJdjg(String jdjg) {
        this.jdjg = jdjg;
    }

    public String getJdjg() {
        return jdjg;
    }

    public void setCdjs(String cdjs) {
        this.cdjs = cdjs;
    }

    public String getCdjs() {
        return cdjs;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public String getIsdel() {
        return isdel;
    }

    public void setCreateUserid(Long createUserid) {
        this.createUserid = createUserid;
    }

    public Long getCreateUserid() {
        return createUserid;
    }

    public TsbzGbjbqk getTsbzGbjbqk() {
        return tsbzGbjbqk;
    }

    public void setTsbzGbjbqk(TsbzGbjbqk tsbzGbjbqk) {
        this.tsbzGbjbqk = tsbzGbjbqk;
    }

    private String dqzt;

    public void setDqzt(String dqzt) {
        this.dqzt = dqzt;
    }

    public String getDqzt() {
        return dqzt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("gbid", getGbid())
                .append("yjly", getYjly())
                .append("lxktmc", getLxktmc())
                .append("lxktjb", getLxktjb())
                .append("lxktlb", getLxktlb())
                .append("lxrq", getLxrq())
                .append("jtrq", getJtrq())
                .append("jdjg", getJdjg())
                .append("cdjs", getCdjs())
                .append("isdel", getIsdel())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .append("tsbzGbjbqk", getTsbzGbjbqk())
                .append("dqzt", getDqzt())
                .toString();
    }
}
