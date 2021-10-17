package com.ruoyi.project.benyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 入学准备课程对象 by_admissioncourse
 *
 * @author tsbz
 * @date 2021-10-17
 */
public class ByAdmissioncourse extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 父节点
     */
    @Excel(name = "父节点")
    private Long parentId;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String titlename;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setTitlename(String titlename) {
        this.titlename = titlename;
    }

    public String getTitlename() {
        return titlename;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("parentId", getParentId())
                .append("titlename", getTitlename())
                .append("content", getContent())
                .append("sort", getSort())
                .append("createTime", getCreateTime())
                .toString();
    }
}
