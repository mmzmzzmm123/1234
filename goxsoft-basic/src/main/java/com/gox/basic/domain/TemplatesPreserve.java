package com.gox.basic.domain;

import com.gox.common.annotation.Excel;
import com.gox.common.core.domain.BaseEntity;
import com.gox.common.plugin.AutoId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.type.Alias;

/**
 * 模板维护对象 templates_preserve
 * 
 * @author gox
 * @date 2021-03-06
 */
@Alias("TemplatesPreserve")
public class TemplatesPreserve extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @AutoId
    private Long id;


    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long nodeId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long deptId;

    /** 字段名字 */
    @Excel(name = "字段名字")
    private String tableFieldName;

    /** 对应的英文字段名 */
    @Excel(name = "对应的英文字段名")
    private String vModel;

    /** 是否为表格字段 */
    @Excel(name = "是否为表格字段")
    private Boolean tableFieldFlag;

    /** 表格字段排序 */
    @Excel(name = "表格字段排序")
    private Long sort;

    /** 是否为搜索字段 */
    @Excel(name = "是否为搜索字段")
    private Boolean tableSearchFlag;

    /** 搜索排序 */
    @Excel(name = "搜索排序")
    private Long searchSort;

    /** 列宽 */
    @Excel(name = "列宽")
    private Long width;

    public TemplatesPreserve() {
    }

    public TemplatesPreserve(Long nodeId, Long deptId) {
        this.nodeId = nodeId;
        this.deptId = deptId;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setNodeId(Long nodeId) 
    {
        this.nodeId = nodeId;
    }

    public Long getNodeId() 
    {
        return nodeId;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }
    public void setTableFieldName(String tableFieldName) 
    {
        this.tableFieldName = tableFieldName;
    }

    public String getTableFieldName() 
    {
        return tableFieldName;
    }
    public void setvModel(String vModel) 
    {
        this.vModel = vModel;
    }

    public String getvModel() 
    {
        return vModel;
    }
    public void setTableFieldFlag(Boolean tableFieldFlag)
    {
        this.tableFieldFlag = tableFieldFlag;
    }

    public Boolean getTableFieldFlag()
    {
        return tableFieldFlag;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }
    public void setTableSearchFlag(Boolean tableSearchFlag)
    {
        this.tableSearchFlag = tableSearchFlag;
    }

    public Boolean getTableSearchFlag()
    {
        return tableSearchFlag;
    }
    public void setSearchSort(Long searchSort) 
    {
        this.searchSort = searchSort;
    }

    public Long getSearchSort() 
    {
        return searchSort;
    }
    public void setWidth(Long width) 
    {
        this.width = width;
    }

    public Long getWidth() 
    {
        return width;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("nodeId", getNodeId())
            .append("deptId", getDeptId())
            .append("tableFieldName", getTableFieldName())
            .append("vModel", getvModel())
            .append("tableFieldFlag", getTableFieldFlag())
            .append("sort", getSort())
            .append("tableSearchFlag", getTableSearchFlag())
            .append("searchSort", getSearchSort())
            .append("width", getWidth())
            .toString();
    }
}
