package com.ruoyi.gbxxgl.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 干部出入境情况对象 tsbz_gbcrjqk
 *
 * @author ruoyi
 * @date 2020-12-08
 */
public class TsbzGbcrjqk extends BaseEntity {
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
     * 起始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "起始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date qsrq;

    /**
     * 终止日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "终止日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date zzrq;

    /**
     * 性质
     */
    @Excel(name = "性质")
    private String xz;

    /**
     * 目的地
     */
    @Excel(name = "目的地")
    private String mdd;

    /**
     * 事由
     */
    @Excel(name = "事由")
    private String sy;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createUser;

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

    public void setQsrq(Date qsrq) {
        this.qsrq = qsrq;
    }

    public Date getQsrq() {
        return qsrq;
    }

    public void setZzrq(Date zzrq) {
        this.zzrq = zzrq;
    }

    public Date getZzrq() {
        return zzrq;
    }

    public void setXz(String xz) {
        this.xz = xz;
    }

    public String getXz() {
        return xz;
    }

    public void setMdd(String mdd) {
        this.mdd = mdd;
    }

    public String getMdd() {
        return mdd;
    }

    public void setSy(String sy) {
        this.sy = sy;
    }

    public String getSy() {
        return sy;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public String getIsdel() {
        return isdel;
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
                .append("qsrq", getQsrq())
                .append("zzrq", getZzrq())
                .append("xz", getXz())
                .append("mdd", getMdd())
                .append("sy", getSy())
                .append("createUser", getCreateUser())
                .append("createTime", getCreateTime())
                .append("tsbzGbjbqk", getTsbzGbjbqk())
                .append("isdel", getIsdel())
                .append("dqzt", getDqzt())
                .toString();
    }
}
