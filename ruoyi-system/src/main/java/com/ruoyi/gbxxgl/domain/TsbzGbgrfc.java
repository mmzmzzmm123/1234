package com.ruoyi.gbxxgl.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 个人风采(干部管理-个人状况)对象 tsbz_gbgrfc
 *
 * @author tsbz
 * @date 2020-12-21
 */
public class TsbzGbgrfc extends BaseEntity {
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
     * 证件照
     */
    @Excel(name = "证件照")
    private String zjzpath;

    /**
     * 证件照名称
     */
    @Excel(name = "证件照名称")
    private String zjzmc;

    /**
     * 生活照
     */
    @Excel(name = "生活照")
    private String shzpath;

    /**
     * 生活照名称
     */
    @Excel(name = "生活照名称")
    private String shzmc;

    /**
     * 工作照
     */
    @Excel(name = "工作照")
    private String gzzpath;

    /**
     * 工作照名称
     */
    @Excel(name = "工作照名称")
    private String zgzmc;

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

    public void setZjzpath(String zjzpath) {
        this.zjzpath = zjzpath;
    }

    public String getZjzpath() {
        return zjzpath;
    }

    public void setZjzmc(String zjzmc) {
        this.zjzmc = zjzmc;
    }

    public String getZjzmc() {
        return zjzmc;
    }

    public void setShzpath(String shzpath) {
        this.shzpath = shzpath;
    }

    public String getShzpath() {
        return shzpath;
    }

    public void setShzmc(String shzmc) {
        this.shzmc = shzmc;
    }

    public String getShzmc() {
        return shzmc;
    }

    public void setGzzpath(String gzzpath) {
        this.gzzpath = gzzpath;
    }

    public String getGzzpath() {
        return gzzpath;
    }

    public void setZgzmc(String zgzmc) {
        this.zgzmc = zgzmc;
    }

    public String getZgzmc() {
        return zgzmc;
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
                .append("zjzpath", getZjzpath())
                .append("zjzmc", getZjzmc())
                .append("shzpath", getShzpath())
                .append("shzmc", getShzmc())
                .append("gzzpath", getGzzpath())
                .append("zgzmc", getZgzmc())
                .append("isdel", getIsdel())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .append("tsbzGbjbqk", getTsbzGbjbqk())
                .append("dqzt", getDqzt())
                .toString();
    }
}
