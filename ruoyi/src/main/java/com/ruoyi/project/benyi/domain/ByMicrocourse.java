package com.ruoyi.project.benyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 微型课程对象 by_microcourse
 *
 * @author tsbz
 * @date 2021-05-13
 */
public class ByMicrocourse extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 作者
     */
    @Excel(name = "作者")
    private String author;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String contents;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private String type;

    /**
     * 适用范围
     */
    @Excel(name = "适用范围")
    private String scpoe;

    @Excel(name = "上下册")
    private String upanddown;

    @Excel(name = "序号")
    private Long sort;

    /**
     * 树状父类ID
     */
    private Long parentId;

    /**
     * 树状子类
     */
    private List<ByMicrocourse> children = new ArrayList<ByMicrocourse>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setScpoe(String scpoe) {
        this.scpoe = scpoe;
    }

    public String getScpoe() {
        return scpoe;
    }

    public void setUpanddown(String upanddown) {
        this.upanddown = upanddown;
    }

    public String getUpanddown() {
        return upanddown;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Long getSort() {
        return sort;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<ByMicrocourse> getChildren() {
        return children;
    }

    public void setChildren(List<ByMicrocourse> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("title", getTitle())
                .append("author", getAuthor())
                .append("contents", getContents())
                .append("type", getType())
                .append("scpoe", getScpoe())
                .append("upanddown", getUpanddown())
                .append("sort", getSort())
                .append("createTime", getCreateTime())
                .append("parentid", getParentId())
                .toString();
    }
}
