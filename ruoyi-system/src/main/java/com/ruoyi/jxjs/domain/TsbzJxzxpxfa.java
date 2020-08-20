package com.ruoyi.jxjs.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 见习之星评选方案对象 tsbz_jxzxpxfa
 *
 * @author ruoyi
 * @date 2020-08-20
 */
public class TsbzJxzxpxfa extends BaseEntity {
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
     * 方案文件
     */
    @Excel(name = "方案文件")
    private String fawj;

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
     * 评选学段
     */
    @Excel(name = "评选学段")
    private String pxxd;

    /**
     * 评选学科
     */
    @Excel(name = "评选学科")
    private String pxxk;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createuserid;

    /**
     * 文件名称
     */
    @Excel(name = "文件名称")
    private String wjmc;

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

    public void setFawj(String fawj) {
        this.fawj = fawj;
    }

    public String getFawj() {
        return fawj;
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

    public void setPxxd(String pxxd) {
        this.pxxd = pxxd;
    }

    public String getPxxd() {
        return pxxd;
    }

    public void setPxxk(String pxxk) {
        this.pxxk = pxxk;
    }

    public String getPxxk() {
        return pxxk;
    }

    public void setCreateuserid(Long createuserid) {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() {
        return createuserid;
    }

    public void setWjmc(String wjmc) {
        this.wjmc = wjmc;
    }

    public String getWjmc() {
        return wjmc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("fawj", getFawj())
                .append("fazt", getFazt())
                .append("fayxkssj", getFayxkssj())
                .append("fayxjssj", getFayxjssj())
                .append("nf", getNf())
                .append("pxxd", getPxxd())
                .append("pxxk", getPxxk())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .append("wjmc", getWjmc())
                .toString();
    }
}