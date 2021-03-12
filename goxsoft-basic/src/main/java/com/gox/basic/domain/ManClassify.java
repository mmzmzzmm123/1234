package com.gox.basic.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.gox.common.annotation.Excel;
import com.gox.common.core.domain.BaseEntity;
import org.apache.ibatis.type.Alias;

/**
 * 分类管理对象 man_classify
 * 
 * @author gox
 * @date 2021-03-09
 */
@Alias("ManClassify")
public class ManClassify extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 分类名 */
    @Excel(name = "分类名")
    private String name;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 父节点id */
    @Excel(name = "父节点id")
    private Long parentId;

    /** 父节点名字 */
    @Excel(name = "父节点名字")
    private String parentName;

    /** 类型 M/C */
    @Excel(name = "类型 M/C")
    private String type;
    /**
     * 已归/未归
     */
    private Boolean auditType;
    /**
     * 文件/案卷
     */
    private Boolean archivalType;
    /**
     * 权限
     */
    private String perms;
    /**
     * 图标
     */
    private String icon;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getAuditType() {
        return auditType;
    }

    public void setAuditType(Boolean auditType) {
        this.auditType = auditType;
    }

    public Boolean getArchivalType() {
        return archivalType;
    }

    public void setArchivalType(Boolean archivalType) {
        this.archivalType = archivalType;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort()
    {
        return sort;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setParentName(String parentName) 
    {
        this.parentName = parentName;
    }

    public String getParentName() 
    {
        return parentName;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("sort", sort)
                .append("parentId", parentId)
                .append("parentName", parentName)
                .append("type", type)
                .append("auditType", auditType)
                .append("archivalType", archivalType)
                .append("perms", perms)
                .append("icon", icon)
                .toString();
    }
}
