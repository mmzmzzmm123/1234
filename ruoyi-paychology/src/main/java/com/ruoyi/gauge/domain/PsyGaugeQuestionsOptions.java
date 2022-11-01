package com.ruoyi.gauge.domain;

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
public class PsyGaugeQuestionsOptions extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 测评表单题目ID */
    @Excel(name = "测评表单题目ID")
    private Long gaugeQuestionsId;

    /** 选项名 */
    @Excel(name = "选项名")
    private String name;

    /** 分数 */
    @Excel(name = "分数")
    private Long value;

    /** 选项图片 */
    @Excel(name = "选项图片")
    private String imgUrl;

    /** 类型 1、文字 2、图片 */
    @Excel(name = "类型 1、文字 2、图片")
    private Integer type;

    @Excel(name = "排序")
    private Integer sort;

    private String selectedStr;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSelectedStr() {
        return selectedStr;
    }

    public void setSelectedStr(String selectedStr) {
        this.selectedStr = selectedStr;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGaugeQuestionsId(Long gaugeQuestionsId) 
    {
        this.gaugeQuestionsId = gaugeQuestionsId;
    }

    public Long getGaugeQuestionsId() 
    {
        return gaugeQuestionsId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setValue(Long value) 
    {
        this.value = value;
    }

    public Long getValue() 
    {
        return value;
    }
    public void setImgUrl(String imgUrl) 
    {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() 
    {
        return imgUrl;
    }
    public void setType(Integer type) 
    {
        this.type = type;
    }

    public Integer getType() 
    {
        return type;
    }

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
