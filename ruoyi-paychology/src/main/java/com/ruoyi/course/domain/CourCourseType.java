package com.ruoyi.course.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 课程类型对象 cour_course_type
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
public class CourCourseType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 类型编号 */
    @Excel(name = "类型编号")
    private Long typeId;

    /** 类型名称 */
    @Excel(name = "类型名称")
    private String name;

    public void setTypeId(Long typeId) 
    {
        this.typeId = typeId;
    }

    public Long getTypeId() 
    {
        return typeId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("typeId", getTypeId())
            .append("name", getName())
            .toString();
    }
}
