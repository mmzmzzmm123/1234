package com.ruoyi.classschedule.domain;

import com.ruoyi.common.annotation.Excel;

public class ClazzCourseTeacherVo {
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


    private String clazzName;
    private String gradeName;
    private String courseName;
    private String teacherName;

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

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
}
