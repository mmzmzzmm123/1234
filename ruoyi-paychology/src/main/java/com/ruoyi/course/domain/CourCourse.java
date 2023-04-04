package com.ruoyi.course.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.validation.constraints.Pattern;

/**
 * 课程对象 cour_course
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourCourse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 课程编号 */
    @Excel(name = "课程ID")
    private Integer courseId;

    /** 课程名称 */
    @Excel(name = "课程名称")
    private String name;

    /** 课程类型，取值根据课程类型表 */
    @Excel(name = "课程类型，取值根据课程类型表")
    private Long type;

    /** 课程作者 */
    @Excel(name = "课程作者")
    private String author;

    /** 课程图片 */
    @Excel(name = "课程图片")
    private String url;

    /** 课程价格 */
    @Excel(name = "课程价格")
    private BigDecimal price;

    /** 课程详情，富文本内容 */
    @Excel(name = "课程详情，富文本内容")
    private String detail;

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
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setType(Long type) 
    {
        this.type = type;
    }

    public Long getType() 
    {
        return type;
    }
    public void setAuthor(String author) 
    {
        this.author = author;
    }

    public String getAuthor() 
    {
        return author;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setDetail(String detail) 
    {
        this.detail = detail;
    }

    public String getDetail() 
    {
        return detail;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("courseId", getCourseId())
            .append("name", getName())
            .append("type", getType())
            .append("author", getAuthor())
            .append("url", getUrl())
            .append("price", getPrice())
            .append("detail", getDetail())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
