package com.ruoyi.project.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 班级信息对象 by_class
 *
 * @author tsbz
 * @date 2020-04-14
 */
public class ByClass extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 班级编号
     */
    private String bjbh;

    /**
     * 学校代码
     */
    @Excel(name = "学校代码")
    private Long deptId;

    /**
     * 班级类型
     */
    @Excel(name = "班级类型")
    private String bjtype;

    /**
     * 班级序号
     */
    @Excel(name = "班级序号")
    private Long bhxh;

    /**
     * 学年
     */
    @Excel(name = "学年")
    private String xn;

    /**
     * 班级名称
     */
    @Excel(name = "班级名称")
    private String bjmc;

    /**
     * 班级荣誉称号
     */
    @Excel(name = "班级荣誉称号")
    private String bjrych;

    /**
     * 建班年月
     */
    @Excel(name = "建班年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date jbny;

    /**
     * 主班教师
     */
    @Excel(name = "主班教师")
    private Long zbjs;

    /**
     * 主班教师名称
     */
    private String zbjsxm;

    /**
     * 配班教师
     */
    @Excel(name = "配班教师")
    private Long pbjs;

    /**
     * 配班教师名称
     */
    private String pbjsxm;

    /**
     * 助理教师
     */
    @Excel(name = "助理教师")
    private Long zljs;

    /**
     * 助理教师名称
     */
    private String zljsxm;

    /**
     * 是否删除
     * 1:删除
     * 0:正常
     */
    @Excel(name = "是否删除 1:删除 0:正常")
    private String isdel;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    public void setBjbh(String bjbh) {
        this.bjbh = bjbh;
    }

    public String getBjbh() {
        return bjbh;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setBjtype(String bjtype) {
        this.bjtype = bjtype;
    }

    public String getBjtype() {
        return bjtype;
    }

    public void setBhxh(Long bhxh) {
        this.bhxh = bhxh;
    }

    public Long getBhxh() {
        return bhxh;
    }

    public void setXn(String xn) {
        this.xn = xn;
    }

    public String getXn() {
        return xn;
    }

    public void setBjmc(String bjmc) {
        this.bjmc = bjmc;
    }

    public String getBjmc() {
        return bjmc;
    }

    public void setBjrych(String bjrych) {
        this.bjrych = bjrych;
    }

    public String getBjrych() {
        return bjrych;
    }

    public void setJbny(Date jbny) {
        this.jbny = jbny;
    }

    public Date getJbny() {
        return jbny;
    }

    public void setZbjs(Long zbjs) {
        this.zbjs = zbjs;
    }

    public Long getZbjs() {
        return zbjs;
    }
    public void setZbjsxm(String zbjsxm) {
        this.zbjsxm = zbjsxm;
    }

    public String getZbjsxm() {
        return zbjsxm;
    }

    public void setPbjs(Long pbjs) {
        this.pbjs = pbjs;
    }

    public Long getPbjs() {
        return pbjs;
    }
    public void setPbjsxm(String pbjsxm) {
        this.pbjsxm = pbjsxm;
    }

    public String getPbjsxm() {
        return pbjsxm;
    }

    public void setZljs(Long zljs) {
        this.zljs = zljs;
    }

    public Long getZljs() {
        return zljs;
    }
    public void setZljsxm(String zljsxm) {
        this.zljsxm = zljsxm;
    }

    public String getZljsxm() {
        return zljsxm;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public String getIsdel() {
        return isdel;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("bjbh", getBjbh())
                .append("deptId", getDeptId())
                .append("bjtype", getBjtype())
                .append("bhxh", getBhxh())
                .append("xn", getXn())
                .append("bjmc", getBjmc())
                .append("bjrych", getBjrych())
                .append("jbny", getJbny())
                .append("zbjs", getZbjs())
                .append("zbjsxm", getZbjsxm())
                .append("pbjs", getPbjs())
                .append("pbjsxm", getPbjsxm())
                .append("zljs", getZljs())
                .append("zljsxm", getZljsxm())
                .append("isdel", getIsdel())
                .append("createtime", getCreatetime())
                .toString();
    }
}
