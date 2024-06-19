package com.renxin.course.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.renxin.common.annotation.Excel;
import com.renxin.common.core.domain.BaseEntity;

/**
 * 用户-课程-章节关系对象 cour_user_course_section
 * 
 * @author renxin
 * @date 2023-03-15
 */
public class CourUserCourseSection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;

    /** 用户编号 */
    @Excel(name = "用户ID")
    private Integer userId;

    /** 课程编号 */
    @Excel(name = "课程编号")
    private Integer courseId;

    /** 章节编号 */
    @Excel(name = "章节编号")
    private Integer sectionId;

    /** 上次结束时间（单位：秒） */
    @Excel(name = "上次结束时间", readConverterExp = "单=位：秒")
    private Integer endTime;

    /** 完成状态（0-未完成， 1-已完成） */
    @Excel(name = "完成状态", readConverterExp = "0=-未完成，,1=-已完成")
    private Integer finishStatus;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public Integer getUserId()
    {
        return userId;
    }
    public void setCourseId(Integer courseId)
    {
        this.courseId = courseId;
    }

    public Integer getCourseId()
    {
        return courseId;
    }
    public void setSectionId(Integer sectionId)
    {
        this.sectionId = sectionId;
    }

    public Integer getSectionId()
    {
        return sectionId;
    }
    public void setEndTime(Integer endTime)
    {
        this.endTime = endTime;
    }

    public Integer getEndTime()
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
