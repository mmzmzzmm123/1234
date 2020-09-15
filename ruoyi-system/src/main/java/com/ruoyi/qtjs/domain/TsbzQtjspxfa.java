package com.ruoyi.qtjs.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 群体教师评选方案对象 tsbz_qtjspxfa
 *
 * @author ruoyi
 * @date 2020-09-15
 */
public class TsbzQtjspxfa extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 方案名称
     */
    @Excel(name = "方案名称")
    private String name;

    /**
     * 方案内容
     */
    @Excel(name = "方案内容")
    private String fanr;

    /**
     * 方案文件
     */
    @Excel(name = "方案文件")
    private String fawj;

    /**
     * 文件名称
     */
    @Excel(name = "文件名称")
    private String wjmc;

    /**
     * 方案状态
     */
    @Excel(name = "方案状态")
    private String fazt;

    /**
     * 方案有效开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "方案有效开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date fayxkssj;

    /**
     * 方案有效结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "方案有效结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date fayxjssj;

    /**
     * 所属年份
     */
    @Excel(name = "所属年份")
    private String nf;

    /**
     * 评选类别
     */
    @Excel(name = "评选类别")
    private String pxlb;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createuserid;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFanr(String fanr) {
        this.fanr = fanr;
    }

    public String getFanr() {
        return fanr;
    }

    public void setFawj(String fawj) {
        this.fawj = fawj;
    }

    public String getFawj() {
        return fawj;
    }

    public void setWjmc(String wjmc) {
        this.wjmc = wjmc;
    }

    public String getWjmc() {
        return wjmc;
    }

    public void setFazt(String fazt) {
        this.fazt = fazt;
    }

    public String getFazt() {
        return fazt;
    }

    public void setFayxkssj(Date fayxkssj) {
        this.fayxkssj = fayxkssj;
    }

    public Date getFayxkssj() {
        return fayxkssj;
    }

    public void setFayxjssj(Date fayxjssj) {
        this.fayxjssj = fayxjssj;
    }

    public Date getFayxjssj() {
        return fayxjssj;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getNf() {
        return nf;
    }

    public void setPxlb(String pxlb) {
        this.pxlb = pxlb;
    }

    public String getPxlb() {
        return pxlb;
    }

    public void setCreateuserid(Long createuserid) {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() {
        return createuserid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("fanr", getFanr())
                .append("fawj", getFawj())
                .append("wjmc", getWjmc())
                .append("fazt", getFazt())
                .append("fayxkssj", getFayxkssj())
                .append("fayxjssj", getFayxjssj())
                .append("nf", getNf())
                .append("pxlb", getPxlb())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .toString();
    }
}
