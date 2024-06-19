package com.renxin.course.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.renxin.common.annotation.Excel;
import com.renxin.common.core.domain.BaseEntity;

/**
 * label对象 cour_course_label
 * 
 * @author renxin
 * @date 2023-03-16
 */
public class CourCourseLabel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;

    /** 课程编号 */
    @Excel(name = "课程ID")
    private Integer courseId;

    /** 课程标签 */
    @Excel(name = "课程标签")
    private Integer courseLabel;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setCourseId(Integer courseId)
    {
        this.courseId = courseId;
    }

    public Integer getCourseId()
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
