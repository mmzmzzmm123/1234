package com.ruoyi.course.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * label对象 cour_course_label
 * 
 * @author ruoyi
 * @date 2023-03-16
 */
public class CourCourseLabel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 课程编号 */
    @Excel(name = "课程编号")
    private String courseId;

    /** 课程标签 */
    @Excel(name = "课程标签")
    private Integer courseLabel;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCourseId(String courseId) 
    {
        this.courseId = courseId;
    }

    public String getCourseId() 
    {
        return courseId;
    }
    public void setCourseLabel(Integer courseLabel) 
    {
        this.courseLabel = courseLabel;
    }

    public Integer getCourseLabel() 
    {
        return courseLabel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("courseId", getCourseId())
            .append("courseLabel", getCourseLabel())
            .toString();
    }
}
