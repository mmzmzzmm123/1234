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
     * $column.columnComment
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建人", width = 30, dateFormat = "yyyy-MM-dd")
    private Date 创建时间;

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

    public void set创建时间(Date 创建时间) {
        this.创建时间 = 创建时间;
    }

    public Date get创建时间() {
        return 创建时间;
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
                .append("themeconent", getThemeconent())
                .append("remark", getRemark())
                .append("createuserid", getCreateuserid())
                .append("创建时间", get创建时间())
                .append("updateuserid", getUpdateuserid())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
