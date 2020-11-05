package com.ruoyi.project.benyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 幼儿评估对象 by_assessmentchild
 *
 * @author tsbz
 * @date 2020-10-27
 */
public class ByAssessmentchild extends BaseEntity {
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
     * 测评内容编号
     */
    @Excel(name = "测评内容编号")
    private Long contentid;

    /**
     * 是否符合标准
     */
    @Excel(name = "是否符合标准")
    private String type;

    /**
     * 测评学年
     */
    @Excel(name = "测评学年")
    private String xn;

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

    public void setContentid(Long contentid) {
        this.contentid = contentid;
    }

    public Long getContentid() {
        return contentid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setXn(String xn) {
        this.xn = xn;
    }

    public String getXn() {
        return xn;
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
                .append("contentid", getContentid())
                .append("type", getType())
                .append("xn", getXn())
                .append("userid", getUserid())
                .append("createTime", getCreateTime())
                .toString();
    }
}