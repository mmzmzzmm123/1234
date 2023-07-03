package com.ruoyi.gauge.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 心理测评问题选项对象 psy_gauge_questions_options
 * 
 * @author ruoyi
 * @date 2022-09-06
 */
@Data
public class PsyGaugeQuestionsOptions extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 测评表单题目ID */
    @Excel(name = "测评表单题目ID")
    private Integer gaugeQuestionsId;

    /** 选项名 */
    @Excel(name = "选项名")
    private String name;

    /** 分数 */
    @Excel(name = "分数")
    private Integer value;

    /** 选项图片 */
    @Excel(name = "选项图片")
    private String imgUrl;

    /** 类型 1、文字 2、图片 */
    @Excel(name = "类型 1、文字 2、图片")
    private Integer type;

    @Excel(name = "排序")
    private Integer sort;

    private boolean selectedFlag;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("gaugeQuestionsId", getGaugeQuestionsId())
            .append("name", getName())
            .append("value", getValue())
            .append("imgUrl", getImgUrl())
            .append("type", getType())
            .append("sort", getSort())
            .toString();
    }
}