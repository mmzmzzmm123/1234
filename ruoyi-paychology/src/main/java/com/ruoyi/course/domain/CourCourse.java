package com.ruoyi.course.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourCourse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 课程ID */
    private Integer id;

    /** 课程编号 */
    @Excel(name = "课程编号")
    private String courseId;

    /** 课程名称 */
    @Excel(name = "课程名称")
    private String name;

    /** 课程类型，取值根据课程类型表 */
    @Excel(name = "课程类型")
    private Integer type;

    /** 课程付费方式（0-付费，1-免费） */
    @Excel(name = "课程付费方式")
    private Integer payType;

    /** 课程作者 */
    @Excel(name = "课程作者")
    private String author;

    /** 课程图片 */
    @Excel(name = "课程图片")
    private String url;

    /** 课程列表图标、购买图标 */
    @Excel(name = "课程列表图标")
    private String iconUrl;

    /** 课程价格 */
    @Excel(name = "课程价格")
    private BigDecimal price;

    /** 课程详情，富文本内容 */
    @Excel(name = "课程详情，富文本内容")
    private String detail;

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
