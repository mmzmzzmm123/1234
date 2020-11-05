package com.ruoyi.project.benyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 幼儿评估记录对象 by_assessmentrecordchild
 *
 * @author tsbz
 * @date 2020-10-27
 */
public class ByAssessmentrecordchild extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 幼儿编号
     */
    @Excel(name = "幼儿编号")
    private Long childid;

    /**
     * 班级编号
     */
    @Excel(name = "班级编号")
    private String classid;

    /**
     * 学年
     */
    @Excel(name = "学年")
    private String xn;

    /**
     * 是否提交
     */
    @Excel(name = "是否提交")
    private String state;

    /**
     * 所属年龄段
     */
    @Excel(name = "所属年龄段")
    private String scope;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long userid;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setChildid(Long childid) {
        this.childid = childid;
    }

    public Long getChildid() {
        return childid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassid() {
        return classid;
    }

    public void setXn(String xn) {
        this.xn = xn;
    }

    public String getXn() {
        return xn;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getUserid() {
        return userid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("childid", getChildid())
                .append("classid", getClassid())
                .append("xn", getXn())
                .append("state", getState())
                .append("scope", getScope())
                .append("userid", getUserid())
                .append("createTime", getCreateTime())
                .toString();
    }
}