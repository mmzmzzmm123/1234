package com.ruoyi.course.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户-课程-章节关系对象 cour_user_course_section
 * 
 * @author ruoyi
 * @date 2023-03-15
 */
public class CourUserCourseSection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 用户编号 */
    @Excel(name = "用户编号")
    private String userId;

    /** 课程编号 */
    @Excel(name = "课程编号")
    private String courseId;

    /** 章节编号 */
    @Excel(name = "章节编号")
    private String sectionId;

    /** 上次结束时间（单位：秒） */
    @Excel(name = "上次结束时间", readConverterExp = "单=位：秒")
    private Long endTime;

    /** 完成状态（0-未完成， 1-已完成） */
    @Excel(name = "完成状态", readConverterExp = "0=-未完成，,1=-已完成")
    private Integer finishStatus;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }
    public void setCourseId(String courseId) 
    {
        this.courseId = courseId;
    }

    public String getCourseId() 
    {
        return courseId;
    }
    public void setSectionId(String sectionId) 
    {
        this.sectionId = sectionId;
    }

    public String getSectionId() 
    {
        return sectionId;
    }
    public void setEndTime(Long endTime) 
    {
        this.endTime = endTime;
    }

    public Long getEndTime() 
    {
        return endTime;
    }
    public void setFinishStatus(Integer finishStatus) 
    {
        this.finishStatus = finishStatus;
    }

    public Integer getFinishStatus() 
    {
        return finishStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("courseId", getCourseId())
            .append("sectionId", getSectionId())
            .append("endTime", getEndTime())
            .append("finishStatus", getFinishStatus())
            .toString();
    }
}
