package com.stdiet.custom.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 菜品对象 sys_dishes
 * 
 * @author wonder
 * @date 2020-12-28
 */
public class SysDishes extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 菜品名称 */
    @Excel(name = "菜品名称")
    private String name;

    /** 菜品类型 */
    @Excel(name = "菜品类型")
    private String type;

    /** 做法 */
    @Excel(name = "做法")
    private String methods;

    private List<SysDishesIngredient> igdList;

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
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setMethods(String methods) 
    {
        this.methods = methods;
    }

    public String getMethods() 
    {
        return methods;
    }

    public void setIgdList(List<SysDishesIngredient> ingredientList) {
        this.igdList = ingredientList;
    }

    public List<SysDishesIngredient> getIgdList() {
        return igdList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("type", getType())
            .append("methods", getMethods())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}