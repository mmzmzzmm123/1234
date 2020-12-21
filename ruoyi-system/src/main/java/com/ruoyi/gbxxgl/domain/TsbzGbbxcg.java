package com.ruoyi.gbxxgl.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.jxjs.domain.TsbzJxjsjbxx;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 办学成果(干部管理-评价与考核)对象 tsbz_gbbxcg
 *
 * @author tsbz
 * @date 2020-12-21
 */
public class TsbzGbbxcg extends BaseEntity {
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
     * 成果名称
     */
    @Excel(name = "成果名称")
    private String cgmc;

    /**
     * 成果年度
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "成果年度", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cgnd;

    /**
     * 成果认定单位
     */
    @Excel(name = "成果认定单位")
    private String cgrddw;

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

    public void setCgmc(String cgmc) {
        this.cgmc = cgmc;
    }

    public String getCgmc() {
        return cgmc;
    }

    public void setCgnd(Date cgnd) {
        this.cgnd = cgnd;
    }

    public Date getCgnd() {
        return cgnd;
    }

    public void setCgrddw(String cgrddw) {
        this.cgrddw = cgrddw;
    }

    public String getCgrddw() {
        return cgrddw;
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
                .append("cgmc", getCgmc())
                .append("cgnd", getCgnd())
                .append("cgrddw", getCgrddw())
                .append("isdel", getIsdel())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .append("tsbzGbjbqk",getTsbzGbjbqk())
                .toString();
    }
}
