package com.ruoyi.project.benyi.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 主题整合学期计划明细对象 by_theme_termplanitem
 *
 * @author tsbz
 * @date 2020-08-24
 */
public class ByThemeTermplanitem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 所属计划
     */
    @Excel(name = "所属计划")
    private String tpid;

    /**
     * 月份
     */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "月份", width = 30, dateFormat = "yyyy-MM")
    private Date month;

    /**
     * 主题内容
     */
    @Excel(name = "主题内容")
    private String themeconent;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createuserid;

    /**
     * 修改人
     */
    @Excel(name = "修改人")
    private Long updateuserid;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTpid(String tpid) {
        this.tpid = tpid;
    }

    public String getTpid() {
        return tpid;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Date getMonth() {
        return month;
    }

    public void setThemeconent(String themeconent) {
        this.themeconent = themeconent;
    }

    public String getThemeconent() {
        return themeconent;
    }

    public void setCreateuserid(Long createuserid) {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() {
        return createuserid;
    }

    public void setUpdateuserid(Long updateuserid) {
        this.updateuserid = updateuserid;
    }

    public Long getUpdateuserid() {
        return updateuserid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("tpid", getTpid())
                .append("month", getMonth())
                .append("themeconent", getThemeconent())
                .append("remark", getRemark())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .append("updateuserid", getUpdateuserid())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}