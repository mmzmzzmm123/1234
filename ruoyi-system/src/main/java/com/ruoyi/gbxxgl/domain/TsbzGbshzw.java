package com.ruoyi.gbxxgl.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 干部任职情况-社会职务对象 tsbz_gbshzw
 *
 * @author ruoyi
 * @date 2020-12-08
 */
public class TsbzGbshzw extends BaseEntity {
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
     * 任职起始年月
     */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "任职起始年月", width = 30, dateFormat = "yyyy-MM")
    private Date rzqsny;

    /**
     * 任职终止年月
     */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "任职终止年月", width = 30, dateFormat = "yyyy-MM")
    private Date rzzzny;

    /**
     * 届别
     */
    @Excel(name = "届别")
    private String jb;

    /**
     * 职务级别
     */
    @Excel(name = "职务级别")
    private String jibie;

    /**
     * 职务名称
     */
    @Excel(name = "职务名称")
    private String zwmc;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createuser;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;

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

    public void setRzqsny(Date rzqsny) {
        this.rzqsny = rzqsny;
    }

    public Date getRzqsny() {
        return rzqsny;
    }

    public void setRzzzny(Date rzzzny) {
        this.rzzzny = rzzzny;
    }

    public Date getRzzzny() {
        return rzzzny;
    }

    public void setJb(String jb) {
        this.jb = jb;
    }

    public String getJb() {
        return jb;
    }

    public void setJibie(String jibie) {
        this.jibie = jibie;
    }

    public String getJibie() {
        return jibie;
    }

    public void setZwmc(String zwmc) {
        this.zwmc = zwmc;
    }

    public String getZwmc() {
        return zwmc;
    }

    public void setCreateuser(Long createuser) {
        this.createuser = createuser;
    }

    public Long getCreateuser() {
        return createuser;
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
                .append("rzqsny", getRzqsny())
                .append("rzzzny", getRzzzny())
                .append("jb", getJb())
                .append("jibie", getJibie())
                .append("zwmc", getZwmc())
                .append("createuser", getCreateuser())
                .append("createtime", getCreatetime())
                .append("isdel", getIsdel())
                .append("tsbzGbjbqk", getTsbzGbjbqk())
                .append("dqzt", getDqzt())
                .toString();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

}
