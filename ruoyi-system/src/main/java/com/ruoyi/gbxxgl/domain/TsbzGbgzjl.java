package com.ruoyi.gbxxgl.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 干部工作经历对象 tsbz_gbgzjl
 *
 * @author ruoyi
 * @date 2020-12-08
 */
public class TsbzGbgzjl extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 干部编号
     */
    @Excel(name = "干部编号")
    private Long gbid;

    /**
     * 工作单位编号
     */
    @Excel(name = "工作单位编号")
    private String gzdwid;

    /**
     * 工作单位名称
     */
    @Excel(name = "工作单位名称")
    private String gzdwmc;

    /**
     * 起始年月
     */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "起始年月", width = 30, dateFormat = "yyyy-MM")
    private Date qsny;

    /**
     * 终止年月
     */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "终止年月", width = 30, dateFormat = "yyyy-MM")
    private Date zzny;

    /**
     * 执行职务
     */
    @Excel(name = "执行职务")
    private String zzzw;

    /**
     * 党内职务
     */
    @Excel(name = "党内职务")
    private String dnzw;

    /**
     * 群团职务
     */
    @Excel(name = "群团职务")
    private String qtzw;

    /**
     * 任教学科
     */
    @Excel(name = "任教学科")
    private String rjxk;

    /**
     * 工作岗位
     */
    @Excel(name = "工作岗位")
    private String gzgw;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createUserid;

    /**
     * 是否删除
     */
    @Excel(name = "是否删除")
    private String isdel;

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

    public void setGzdwid(String gzdwid) {
        this.gzdwid = gzdwid;
    }

    public String getGzdwid() {
        return gzdwid;
    }

    public void setGzdwmc(String gzdwmc) {
        this.gzdwmc = gzdwmc;
    }

    public String getGzdwmc() {
        return gzdwmc;
    }

    public void setQsny(Date qsny) {
        this.qsny = qsny;
    }

    public Date getQsny() {
        return qsny;
    }

    public void setZzny(Date zzny) {
        this.zzny = zzny;
    }

    public Date getZzny() {
        return zzny;
    }

    public void setZzzw(String zzzw) {
        this.zzzw = zzzw;
    }

    public String getZzzw() {
        return zzzw;
    }

    public void setDnzw(String dnzw) {
        this.dnzw = dnzw;
    }

    public String getDnzw() {
        return dnzw;
    }

    public void setQtzw(String qtzw) {
        this.qtzw = qtzw;
    }

    public String getQtzw() {
        return qtzw;
    }

    public void setRjxk(String rjxk) {
        this.rjxk = rjxk;
    }

    public String getRjxk() {
        return rjxk;
    }

    public void setGzgw(String gzgw) {
        this.gzgw = gzgw;
    }

    public String getGzgw() {
        return gzgw;
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

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public String getIsdel() {
        return isdel;
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
                .append("gzdwid", getGzdwid())
                .append("gzdwmc", getGzdwmc())
                .append("qsny", getQsny())
                .append("zzny", getZzny())
                .append("zzzw", getZzzw())
                .append("dnzw", getDnzw())
                .append("qtzw", getQtzw())
                .append("rjxk", getRjxk())
                .append("gzgw", getGzgw())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .append("tsbzGbjbqk", getTsbzGbjbqk())
                .append("isdel", getIsdel())
                .append("dqzt", getDqzt())
                .toString();
    }
}
