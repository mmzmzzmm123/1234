package com.ruoyi.gbxxgl.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 家庭成员(干部管理-个人状况)对象 tsbz_gbjtcy
 *
 * @author tsbz
 * @date 2020-12-21
 */
public class TsbzGbjtcy extends BaseEntity {
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
     * 关系
     */
    @Excel(name = "关系")
    private String gx;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String xm;

    /**
     * 出生年月
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date csny;

    /**
     * 工作单位
     */
    @Excel(name = "工作单位")
    private String gzdw;

    /**
     * 职务
     */
    @Excel(name = "职务")
    private String zw;

    /**
     * 政治面貌
     */
    @Excel(name = "政治面貌")
    private String zzmm;

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

    public void setGx(String gx) {
        this.gx = gx;
    }

    public String getGx() {
        return gx;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getXm() {
        return xm;
    }

    public void setCsny(Date csny) {
        this.csny = csny;
    }

    public Date getCsny() {
        return csny;
    }

    public void setGzdw(String gzdw) {
        this.gzdw = gzdw;
    }

    public String getGzdw() {
        return gzdw;
    }

    public void setZw(String zw) {
        this.zw = zw;
    }

    public String getZw() {
        return zw;
    }

    public void setZzmm(String zzmm) {
        this.zzmm = zzmm;
    }

    public String getZzmm() {
        return zzmm;
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
                .append("gx", getGx())
                .append("xm", getXm())
                .append("csny", getCsny())
                .append("gzdw", getGzdw())
                .append("zw", getZw())
                .append("zzmm", getZzmm())
                .append("isdel", getIsdel())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .append("tsbzGbjbqk", getTsbzGbjbqk())
                .append("dqzt", getDqzt())
                .toString();
    }
}
