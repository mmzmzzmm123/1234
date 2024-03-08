package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 车源&动态关联表对象 source_dynamic_relative
 * 
 * @author carol
 * @date 2024-03-06
 */
public class SourceDynamicRelative extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long dynamicId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long sourceId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDynamicId(Long dynamicId) 
    {
        this.dynamicId = dynamicId;
    }

    public Long getDynamicId() 
    {
        return dynamicId;
    }
    public void setSourceId(Long sourceId) 
    {
        this.sourceId = sourceId;
    }

    public Long getSourceId() 
    {
        return sourceId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("dynamicId", getDynamicId())
            .append("sourceId", getSourceId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
