package com.ruoyi.gbxxgl.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 干部学习经历对象 tsbz_gbxxjl
 *
 * @author ruoyi
 * @date 2020-12-08
 */
public class TsbzGbxxjl extends BaseEntity {
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
     * 教育类型
     */
    @Excel(name = "教育类型")
    private String jylx;

    /**
     * 学历
     */
    @Excel(name = "学历")
    private String xl;

    /**
     * 学位
     */
    @Excel(name = "学位")
    private String xw;

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
     * 毕业院校
     */
    @Excel(name = "毕业院校")
    private String byyx;

    /**
     * 专业
     */
    @Excel(name = "专业")
    private String zy;

    /**
     * 职务
     */
    @Excel(name = "职务")
    private String zw;

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

    public void setJylx(String jylx) {
        this.jylx = jylx;
    }

    public String getJylx() {
        return jylx;
    }

    public void setXl(String xl) {
        this.xl = xl;
    }

    public String getXl() {
        return xl;
    }

    public void setXw(String xw) {
        this.xw = xw;
    }

    public String getXw() {
        return xw;
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

    public void setByyx(String byyx) {
        this.byyx = byyx;
    }

    public String getByyx() {
        return byyx;
    }

    public void setZy(String zy) {
        this.zy = zy;
    }

    public String getZy() {
        return zy;
    }

    public void setZw(String zw) {
        this.zw = zw;
    }

    public String getZw() {
        return zw;
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
                .append("jylx", getJylx())
                .append("xl", getXl())
                .append("xw", getXw())
                .append("qsny", getQsny())
                .append("zzny", getZzny())
                .append("byyx", getByyx())
                .append("zy", getZy())
                .append("zw", getZw())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .append("isdel", getIsdel())
                .append("tsbzGbjbqk", getTsbzGbjbqk())
                .append("dqzt", getDqzt())
                .toString();
    }

}
