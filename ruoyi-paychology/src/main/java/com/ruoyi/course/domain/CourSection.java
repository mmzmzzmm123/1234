package com.ruoyi.course.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 章节对象 cour_section
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourSection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 章节编号 */
    @Excel(name = "章节编号")
    private String sectionId;

    /** 章节题目 */
    @Excel(name = "章节题目")
    private String topic;

    /** 章节时长(单位：秒) */
    @Excel(name = "章节时长(单位：秒)")
    private Long duration;

    /** 章节类型（0-普通，1-试听） */
    @Excel(name = "章节类型", readConverterExp = "0=-普通，1-试听")
    private Long type;

    /** 章节内容，富文本 */
    @Excel(name = "章节内容，富文本")
    private String content;

    /** 章节内容链接 */
    @Excel(name = "章节内容链接")
    private String contentUrl;

    /** 课程编号 */
    @Excel(name = "课程编号")
    private String courseId;

    /** 内容类型（0-视频，1-音频） */
    @Excel(name = "内容类型", readConverterExp = "0=-视频，1-音频")
    private Long contentType;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSectionId(String sectionId) 
    {
        this.sectionId = sectionId;
    }

    public String getSectionId() 
    {
        return sectionId;
    }
    public void setTopic(String topic) 
    {
        this.topic = topic;
    }

    public String getTopic() 
    {
        return topic;
    }
    public void setDuration(Long duration) 
    {
        this.duration = duration;
    }

    public Long getDuration() 
    {
        return duration;
    }
    public void setType(Long type) 
    {
        this.type = type;
    }

    public Long getType() 
    {
        return type;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setContentUrl(String contentUrl) 
    {
        this.contentUrl = contentUrl;
    }

    public String getContentUrl() 
    {
        return contentUrl;
    }
    public void setCourseId(String courseId)
    {
        this.courseId = courseId;
    }

    public String getCourseId()
    {
        return courseId;
    }
    public void setContentType(Long contentType) 
    {
        this.contentType = contentType;
    }

    public Long getContentType() 
    {
        return contentType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sectionId", getSectionId())
            .append("topic", getTopic())
            .append("duration", getDuration())
            .append("type", getType())
            .append("content", getContent())
            .append("contentUrl", getContentUrl())
            .append("courseId", getCourseId())
            .append("contentType", getContentType())
            .toString();
    }
}
