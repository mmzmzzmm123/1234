package com.ruoyi.gbxxgl.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 业务考核(干部管理-评价与考核)对象 tsbz_gbywkh
 *
 * @author tsbz
 * @date 2020-12-21
 */
public class TsbzGbywkh extends BaseEntity {
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
     * 学年
     */
    @Excel(name = "学年")
    private String xn;

    /**
     * 技术职务
     */
    @Excel(name = "技术职务")
    private String jszw;

    /**
     * 任职时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任职时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rzsj;

    /**
     * 周课时
     */
    @Excel(name = "周课时")
    private String zks;

    /**
     * 档次
     */
    @Excel(name = "档次")
    private String dc;

    /**
     * 同级比
     */
    @Excel(name = "同级比")
    private String tjb;

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

    public void setXn(String xn) {
        this.xn = xn;
    }

    public String getXn() {
        return xn;
    }

    public void setJszw(String jszw) {
        this.jszw = jszw;
    }

    public String getJszw() {
        return jszw;
    }

    public void setRzsj(Date rzsj) {
        this.rzsj = rzsj;
    }

    public Date getRzsj() {
        return rzsj;
    }

    public void setZks(String zks) {
        this.zks = zks;
    }

    public String getZks() {
        return zks;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getDc() {
        return dc;
    }

    public void setTjb(String tjb) {
        this.tjb = tjb;
    }

    public String getTjb() {
        return tjb;
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
                .append("xn", getXn())
                .append("jszw", getJszw())
                .append("rzsj", getRzsj())
                .append("zks", getZks())
                .append("dc", getDc())
                .append("tjb", getTjb())
                .append("isdel", getIsdel())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .append("tsbzGbjbqk", getTsbzGbjbqk())
                .append("dqzt", getDqzt())
                .toString();
    }
}
