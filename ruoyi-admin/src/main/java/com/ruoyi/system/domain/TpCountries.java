package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 全球国家管理对象 tp_countries
 * 
 * @author carol
 * @date 2024-02-29
 */
public class TpCountries extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 中文名字 */
    @Excel(name = "中文名字")
    private String name;

    /** 简称 */
    @Excel(name = "简称")
    private String code;

    /** 英文名字 */
    @Excel(name = "英文名字")
    private String enName;

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
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setEnName(String enName) 
    {
        this.enName = enName;
    }

    public String getEnName() 
    {
        return enName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("code", getCode())
            .append("enName", getEnName())
            .toString();
    }
}
