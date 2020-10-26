package com.ruoyi.project.benyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 评估内容对象 by_assessmentcontent
 *
 * @author tsbz
 * @date 2020-10-09
 */
public class ByAssessmentcontent extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 父id
     */
    @Excel(name = "父id")
    private Long parentId;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;

    /**
     * 是否元素
     */
    @Excel(name = "是否元素")
    private String iselement;

    /**
     * 序号
     */
    @Excel(name = "序号")
    private Long sort;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<ByAssessmentcontent> getChildren() {
        return children;
    }

    public void setChildren(List<ByAssessmentcontent> children) {
        this.children = children;
    }

    /**
     * 父部门名称
     */
    private String parentName;

    /**
     * 子部门
     */
    private List<ByAssessmentcontent> children = new ArrayList<ByAssessmentcontent>();

    /**
     * 元素才有适用范围;应该以幼儿的岁数为准
     */
    @Excel(name = "元素才有适用范围;应该以幼儿的岁数为准")
    private String scope;

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

    public void setIselement(String iselement) {
        this.iselement = iselement;
    }

    public String getIselement() {
        return iselement;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Long getSort() {
        return sort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("parentId", getParentId())
                .append("name", getName())
                .append("iselement", getIselement())
                .append("scope", getScope())
                .append("createTime", getCreateTime())
                .append("sort", getSort())
                .toString();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
