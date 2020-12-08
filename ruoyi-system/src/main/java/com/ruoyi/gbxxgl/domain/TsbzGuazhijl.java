package com.ruoyi.gbxxgl.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 干部挂职经历对象 tsbz_guazhijl
 *
 * @author ruoyi
 * @date 2020-12-08
 */
public class TsbzGuazhijl extends BaseEntity {
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
     * 挂职单位名称
     */
    @Excel(name = "挂职单位名称")
    private String gzdw;

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
     * 带教导师
     */
    @Excel(name = "带教导师")
    private String djds;

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

    public void setGzdw(String gzdw) {
        this.gzdw = gzdw;
    }

    public String getGzdw() {
        return gzdw;
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

    public void setDjds(String djds) {
        this.djds = djds;
    }

    public String getDjds() {
        return djds;
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
                .append("gzdw", getGzdw())
                .append("qsny", getQsny())
                .append("zzny", getZzny())
                .append("djds", getDjds())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .toString();
    }
}
