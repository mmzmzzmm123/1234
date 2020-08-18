package com.ruoyi.project.benyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 主题整合月计划对象 by_theme_monthplan
 *
 * @author tsbz
 * @date 2020-08-17
 */
public class ByThemeMonthplan extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 计划名称
     */
    @Excel(name = "计划名称")
    private String name;

    /**
     * 所属班级
     */
    @Excel(name = "所属班级")
    private String classid;

    /**
     * 学年学期
     */
    @Excel(name = "学年学期")
    private String xnxq;

    /**
     * 计划月份
     */
    @Excel(name = "计划月份")
    private String month;

    /**
     * 本月主题
     */
    @Excel(name = "本月主题")
    private String themes;

    /**
     * 本月自定义主题
     */
    @Excel(name = "本月自定义主题")
    private String selfthemes;

    /**
     * 家长支持
     */
    @Excel(name = "家长支持")
    private String support;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remarks;

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

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassid() {
        return classid;
    }

    public void setXnxq(String xnxq) {
        this.xnxq = xnxq;
    }

    public String getXnxq() {
        return xnxq;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonth() {
        return month;
    }

    public void setThemes(String themes) {
        this.themes = themes;
    }

    public String getThemes() {
        return themes;
    }

    public void setSelfthemes(String selfthemes) {
        this.selfthemes = selfthemes;
    }

    public String getSelfthemes() {
        return selfthemes;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getSupport() {
        return support;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
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
                .append("classid", getClassid())
                .append("xnxq", getXnxq())
                .append("month", getMonth())
                .append("themes", getThemes())
                .append("selfthemes", getSelfthemes())
                .append("support", getSupport())
                .append("remarks", getRemarks())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .toString();
    }
}
