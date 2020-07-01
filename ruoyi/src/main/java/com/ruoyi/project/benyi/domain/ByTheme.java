package com.ruoyi.project.benyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 主题整合对象 by_theme
 *
 * @author tsbz
 * @date 2020-07-01
 */
public class ByTheme extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 主题名称
     */
    @Excel(name = "主题名称")
    private String name;

    /**
     * 主题内容
     */
    @Excel(name = "主题内容")
    private String content;

    /**
     * 家园沟通
     */
    @Excel(name = "家园沟通")
    private String communicate;

    /**
     * 适用班级
     */
    @Excel(name = "适用班级")
    private String classid;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setCommunicate(String communicate) {
        this.communicate = communicate;
    }

    public String getCommunicate() {
        return communicate;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassid() {
        return classid;
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
                .append("name", getName())
                .append("content", getContent())
                .append("communicate", getCommunicate())
                .append("classid", getClassid())
                .append("sort", getSort())
                .append("createTime", getCreateTime())
                .toString();
    }
}
