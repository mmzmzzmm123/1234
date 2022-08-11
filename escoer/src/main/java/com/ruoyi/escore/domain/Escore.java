package com.ruoyi.escore.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 分数对象 escore
 * 
 * @author ruoyi
 * @date 2022-08-02
 */
public class Escore extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 考试id */
    @Excel(name = "考试id")
    private Long examId;

    /** 班级id */
    @Excel(name = "班级id")
    private Long clazzId;

    /** 学生id */
    @Excel(name = "学生id")
    private Long studentId;

    /** 年级id */
    @Excel(name = "年级id")
    private Long gradeId;

    /** 课程id */
    @Excel(name = "课程id")
    private Long courseId;

    /** 分数 */
    @Excel(name = "分数")
    private Integer score;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setExamId(Long examId) 
    {
        this.examId = examId;
    }

    public Long getExamId() 
    {
        return examId;
    }
    public void setClazzId(Long clazzId) 
    {
        this.clazzId = clazzId;
    }

    public Long getClazzId() 
    {
        return clazzId;
    }
    public void setStudentId(Long studentId) 
    {
        this.studentId = studentId;
    }

    public Long getStudentId() 
    {
        return studentId;
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
    public void setScore(Integer score) 
    {
        this.score = score;
    }

    public Integer getScore() 
    {
        return score;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("examId", getExamId())
            .append("clazzId", getClazzId())
            .append("studentId", getStudentId())
            .append("gradeId", getGradeId())
            .append("courseId", getCourseId())
            .append("score", getScore())
            .toString();
    }
}
