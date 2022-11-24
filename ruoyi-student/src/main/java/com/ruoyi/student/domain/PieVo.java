package com.ruoyi.student.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 【请填写功能名称】对象 pie_vo
 * 
 * @author ruoyi
 * @date 2022-11-23
 */
public class PieVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    private BigDecimal valueNumber;


    private String name;

    public void setValueNumber(BigDecimal value)
    {
        this.valueNumber = value;
    }

    public BigDecimal getValueNumber()
    {
        return valueNumber;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("value", getValueNumber())
            .append("name", getName())
            .toString();
    }
}
