package com.ruoyi.gbxxgl.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 荣誉奖励(干部管理)对象 tsbz_gbryjl
 *
 * @author tsbz
 * @date 2020-12-21
 */
public class TsbzGbryjl extends BaseEntity {
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
     * 获奖日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "获奖日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date hjrq;

    /**
     * 荣誉名称
     */
    @Excel(name = "荣誉名称")
    private String rymc;

    /**
     * 荣誉级别
     */
    @Excel(name = "荣誉级别")
    private String ryjb;

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

    public void setHjrq(Date hjrq) {
        this.hjrq = hjrq;
    }

    public Date getHjrq() {
        return hjrq;
    }

    public void setRymc(String rymc) {
        this.rymc = rymc;
    }

    public String getRymc() {
        return rymc;
    }

    public void setRyjb(String ryjb) {
        this.ryjb = ryjb;
    }

    public String getRyjb() {
        return ryjb;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("gbid", getGbid())
                .append("hjrq", getHjrq())
                .append("rymc", getRymc())
                .append("ryjb", getRyjb())
                .append("isdel", getIsdel())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .append("tsbzGbjbqk", getTsbzGbjbqk())
                .toString();
    }
}
