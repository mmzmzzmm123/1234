package com.ruoyi.qtjs.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 教师基本信息对象 tsbz_jsjbxx
 *
 * @author ruoyi
 * @date 2020-09-14
 */
public class TsbzJsjbxx extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 其他系统教师编号
     */
    @Excel(name = "其他系统教师编号")
    private String jsid;

    /**
     * 教师姓名
     */
    @Excel(name = "教师姓名")
    private String jsxm;

    /**
     * 性别
     */
    @Excel(name = "性别")
    private String xb;

    /**
     * 部门id
     */
    @Excel(name = "部门id")
    private String deptid;

    /**
     * 教师档案号
     */
    @Excel(name = "教师档案号")
    private String dabh;

    /**
     * 证件类型
     */
    @Excel(name = "证件类型")
    private String zjlx;

    /**
     * 证件号码
     */
    @Excel(name = "证件号码")
    private String zjhm;

    /**
     * 编制状态
     */
    @Excel(name = "编制状态")
    private String bzzt;

    /**
     * 岗位状态
     */
    @Excel(name = "岗位状态")
    private String gwzt;

    /**
     * 岗位类型
     */
    @Excel(name = "岗位类型")
    private String gwlx;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date csrq;

    /**
     * 任教年限
     */
    @Excel(name = "任教年限")
    private Long rjnx;

    /**
     * 职称
     */
    @Excel(name = "职称")
    private String zc;

    /**
     * 专业荣誉
     */
    @Excel(name = "专业荣誉")
    private String zyry;

    /**
     * 同职比
     */
    @Excel(name = "同职比")
    private String tzb;

    /**
     * 周课时
     */
    @Excel(name = "周课时")
    private String zks;

    public TsbzXxjbxx getTsbzXxjbxx() {
        return tsbzXxjbxx;
    }

    public void setTsbzXxjbxx(TsbzXxjbxx tsbzXxjbxx) {
        this.tsbzXxjbxx = tsbzXxjbxx;
    }

    private TsbzXxjbxx tsbzXxjbxx;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setJsid(String jsid) {
        this.jsid = jsid;
    }

    public String getJsid() {
        return jsid;
    }

    public void setJsxm(String jsxm) {
        this.jsxm = jsxm;
    }

    public String getJsxm() {
        return jsxm;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getXb() {
        return xb;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDabh(String dabh) {
        this.dabh = dabh;
    }

    public String getDabh() {
        return dabh;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
    }

    public String getZjlx() {
        return zjlx;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setBzzt(String bzzt) {
        this.bzzt = bzzt;
    }

    public String getBzzt() {
        return bzzt;
    }

    public void setGwzt(String gwzt) {
        this.gwzt = gwzt;
    }

    public String getGwzt() {
        return gwzt;
    }

    public void setGwlx(String gwlx) {
        this.gwlx = gwlx;
    }

    public String getGwlx() {
        return gwlx;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setRjnx(Long rjnx) {
        this.rjnx = rjnx;
    }

    public Long getRjnx() {
        return rjnx;
    }

    public void setZc(String zc) {
        this.zc = zc;
    }

    public String getZc() {
        return zc;
    }

    public void setZyry(String zyry) {
        this.zyry = zyry;
    }

    public String getZyry() {
        return zyry;
    }

    public void setTzb(String tzb) {
        this.tzb = tzb;
    }

    public String getTzb() {
        return tzb;
    }

    public void setZks(String zks) {
        this.zks = zks;
    }

    public String getZks() {
        return zks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("jsid", getJsid())
                .append("jsxm", getJsxm())
                .append("xb", getXb())
                .append("deptid", getDeptid())
                .append("dabh", getDabh())
                .append("zjlx", getZjlx())
                .append("zjhm", getZjhm())
                .append("bzzt", getBzzt())
                .append("gwzt", getGwzt())
                .append("gwlx", getGwlx())
                .append("csrq", getCsrq())
                .append("rjnx", getRjnx())
                .append("zc", getZc())
                .append("zyry", getZyry())
                .append("tzb", getTzb())
                .append("zks", getZks())
                .append("createTime", getCreateTime())
                .append("tsbzXxjbxx",getTsbzXxjbxx())
                .toString();
    }
}
