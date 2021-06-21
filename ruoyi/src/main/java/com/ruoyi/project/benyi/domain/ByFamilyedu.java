package com.ruoyi.project.benyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 家庭教育对象 by_familyedu
 *
 * @author tsbz
 * @date 2021-06-20
 */
public class ByFamilyedu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 父节点
     */
    @Excel(name = "父节点")
    private Long parentid;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String content;

    /**
     * 序号
     */
    @Excel(name = "序号")
    private Long sort;

    /**
     * 树状子类
     */
    private List<ByFamilyedu> children = new ArrayList<ByFamilyedu>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Long getSort() {
        return sort;
    }

    public List<ByFamilyedu> getChildren() {
        return children;
    }

    public void setChildren(List<ByFamilyedu> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("parentid", getParentid())
                .append("title", getTitle())
                .append("content", getContent())
                .append("sort", getSort())
                .append("createTime", getCreateTime())
                .toString();
    }
}
