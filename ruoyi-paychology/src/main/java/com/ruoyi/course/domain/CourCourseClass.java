package com.ruoyi.course.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 类型对象 cour_course_class
 * 
 * @author ruoyi
 * @date 2023-04-07
 */
public class CourCourseClass extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 类型ID */
    private Integer id;

    /** 类型名称 */
    @Excel(name = "类型名称")
    private String name;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
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
    public void setSort(Integer sort) 
    {
        this.sort = sort;
    }

    public Integer getSort() 
    {
        return sort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("sort", getSort())
            .toString();
    }
}
