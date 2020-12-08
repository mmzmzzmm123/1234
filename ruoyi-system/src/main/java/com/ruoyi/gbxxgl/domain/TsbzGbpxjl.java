package com.ruoyi.gbxxgl.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 干部培训经历对象 tsbz_gbpxjl
 *
 * @author ruoyi
 * @date 2020-12-08
 */
public class TsbzGbpxjl extends BaseEntity {
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
     * 培训班名称
     */
    @Excel(name = "培训班名称")
    private String pxbmc;

    /**
     * 培训地点
     */
    @Excel(name = "培训地点")
    private String pxdd;

    /**
     * 培训级别
     */
    @Excel(name = "培训级别")
    private String pxjb;

    /**
     * 职务
     */
    @Excel(name = "职务")
    private String zw;

    /**
     * 起始年月
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "起始年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date qsny;

    /**
     * 终止年月
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "终止年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date zzny;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createUserid;

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

    public void setPxbmc(String pxbmc) {
        this.pxbmc = pxbmc;
    }

    public String getPxbmc() {
        return pxbmc;
    }

    public void setPxdd(String pxdd) {
        this.pxdd = pxdd;
    }

    public String getPxdd() {
        return pxdd;
    }

    public void setPxjb(String pxjb) {
        this.pxjb = pxjb;
    }

    public String getPxjb() {
        return pxjb;
    }

    public void setZw(String zw) {
        this.zw = zw;
    }

    public String getZw() {
        return zw;
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

    public void setCreateUserid(Long createUserid) {
        this.createUserid = createUserid;
    }

    public Long getCreateUserid() {
        return createUserid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("gbid", getGbid())
                .append("pxbmc", getPxbmc())
                .append("pxdd", getPxdd())
                .append("pxjb", getPxjb())
                .append("zw", getZw())
                .append("qsny", getQsny())
                .append("zzny", getZzny())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .toString();
    }
}
