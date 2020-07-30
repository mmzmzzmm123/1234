package com.ruoyi.project.benyi.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 入班体验申请对象 by_experience
 *
 * @author tsbz
 * @date 2020-07-26
 */
public class ByExperience extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 家长姓名
     */
    @Excel(name = "家长姓名")
    private String jzxm;

    /**
     * 幼儿姓名
     */
    @Excel(name = "幼儿姓名")
    private String yexm;

    /**
     * 幼儿出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "幼儿出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date csrq;

    /**
     * 联系方式
     */
    @Excel(name = "联系方式")
    private String lxfs;

    /**
     * 拟入园时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "拟入园时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date nrysj;

    /**
     * 申请体验时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请体验时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sqtysj;

    /**
     * 上午或下午
     */
    @Excel(name = "上午或下午")
    private String swxw;

    /**
     * 体验学校
     */
    @Excel(name = "体验学校")
    private Long schoolid;

    /**
     * 是否回复
     */
    @Excel(name = "是否回复")
    private String sfhf;

    /**
     * 回复内容
     */
    @Excel(name = "回复内容")
    private String hfrn;

    /**
     * 回复人
     */
    @Excel(name = "回复人")
    private Long hfuserid;

    /**
     * 回复时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "回复时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date fhsj;

    /**
     * 园长指示
     */
    @Excel(name = "园长指示")
    private String yzzs;

    /**
     * 体验结果
     * 0不入园
     * 1入园
     */
    @Excel(name = "体验结果 0不入园 1入园")
    private String tyjg;

    /**
     * 入园时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入园时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rysj;

    /**
     * 体验内容id
     */
    @Excel(name = "体验内容id")
    private String tynrid;

    private String tynrcontent;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setJzxm(String jzxm) {
        this.jzxm = jzxm;
    }

    public String getJzxm() {
        return jzxm;
    }

    public void setYexm(String yexm) {
        this.yexm = yexm;
    }

    public String getYexm() {
        return yexm;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setLxfs(String lxfs) {
        this.lxfs = lxfs;
    }

    public String getLxfs() {
        return lxfs;
    }

    public void setNrysj(Date nrysj) {
        this.nrysj = nrysj;
    }

    public Date getNrysj() {
        return nrysj;
    }

    public void setSqtysj(Date sqtysj) {
        this.sqtysj = sqtysj;
    }

    public Date getSqtysj() {
        return sqtysj;
    }

    public void setSwxw(String swxw) {
        this.swxw = swxw;
    }

    public String getSwxw() {
        return swxw;
    }

    public void setSchoolid(Long schoolid) {
        this.schoolid = schoolid;
    }

    public Long getSchoolid() {
        return schoolid;
    }

    public void setSfhf(String sfhf) {
        this.sfhf = sfhf;
    }

    public String getSfhf() {
        return sfhf;
    }

    public void setHfrn(String hfrn) {
        this.hfrn = hfrn;
    }

    public String getHfrn() {
        return hfrn;
    }

    public void setHfuserid(Long hfuserid) {
        this.hfuserid = hfuserid;
    }

    public Long getHfuserid() {
        return hfuserid;
    }

    public void setFhsj(Date fhsj) {
        this.fhsj = fhsj;
    }

    public Date getFhsj() {
        return fhsj;
    }

    public void setYzzs(String yzzs) {
        this.yzzs = yzzs;
    }

    public String getYzzs() {
        return yzzs;
    }

    public void setTyjg(String tyjg) {
        this.tyjg = tyjg;
    }

    public String getTyjg() {
        return tyjg;
    }

    public void setRysj(Date rysj) {
        this.rysj = rysj;
    }

    public Date getRysj() {
        return rysj;
    }

    public void setTynrid(String tynrid) {
        this.tynrid = tynrid;
    }

    public String getTynrid() {
        return tynrid;
    }

    public void setTynrcontent(String tynrcontent) {
        this.tynrcontent = tynrcontent;
    }

    public String getTynrcontent() {
        return tynrcontent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("jzxm", getJzxm())
                .append("yexm", getYexm())
                .append("csrq", getCsrq())
                .append("lxfs", getLxfs())
                .append("nrysj", getNrysj())
                .append("sqtysj", getSqtysj())
                .append("swxw", getSwxw())
                .append("schoolid", getSchoolid())
                .append("sfhf", getSfhf())
                .append("hfrn", getHfrn())
                .append("hfuserid", getHfuserid())
                .append("fhsj", getFhsj())
                .append("yzzs", getYzzs())
                .append("tyjg", getTyjg())
                .append("rysj", getRysj())
                .append("createTime", getCreateTime())
                .append("tynrid", getTynrid())
                .append("tynrcontent", getTynrcontent())
                .toString();
    }
}