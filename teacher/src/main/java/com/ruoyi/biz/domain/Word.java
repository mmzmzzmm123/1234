package com.ruoyi.biz.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 作业安排对象 word
 * 
 * @author ruoyi
 * @date 2022-08-05
 */
public class Word extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 作业标题 */
    @Excel(name = "作业标题")
    private String title;

    /** 作业内容 */
    @Excel(name = "作业内容")
    private String content;

    /** 发布班级 */
    @Excel(name = "发布班级")
    private Long clazzId;

    /** 发布老师 */
    @Excel(name = "发布老师")
    private Long teacherId;

    private String clazzName; //班级名称

    private String  gradeName; //发布班级

    private String teacherName; //发布老师

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
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setClazzId(Long clazzId) 
    {
        this.clazzId = clazzId;
    }

    public Long getClazzId() 
    {
        return clazzId;
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
            .append("title", getTitle())
            .append("content", getContent())
            .append("clazzId", getClazzId())
            .append("teacherId", getTeacherId())
            .toString();
    }
}
