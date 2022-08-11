package com.ruoyi.classschedule.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 老师_班级_课程对象 clazz_course_teacher
 * 
 * @author ruoyi
 * @date 2022-08-03
 */
public class ClazzCourseTeacher extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 班级id */
    @Excel(name = "班级id")
    private Long clazzId;

    /** 年级ID */
    @Excel(name = "年级ID")
    private Long gradeId;

    /** 课程ID */
    @Excel(name = "课程ID")
    private Long courseId;

    /** 教师id */
    @Excel(name = "教师id")
    private Long teacherId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setClazzId(Long clazzId) 
    {
        this.clazzId = clazzId;
    }

    public Long getClazzId() 
    {
        return clazzId;
    }
    public void setGradeId(Long gradeId) 
    {
        this.gradeId = gradeId;
    }

    public Long getGradeId() 
    {
        return gradeId;
    }
    public void setCourseId(Long courseId) 
    {
        this.courseId = courseId;
    }

    public Long getCourseId() 
    {
        return courseId;
    }
    public void setTeacherId(Long teacherId) 
    {
        this.teacherId = teacherId;
    }

    public Long getTeacherId() 
    {
        return teacherId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("clazzId", getClazzId())
            .append("gradeId", getGradeId())
            .append("courseId", getCourseId())
            .append("teacherId", getTeacherId())
            .toString();
    }
}
