package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 work_concentrate
 * 
 * @author ruoyi
 * @date 2022-08-05
 */
public class WorkConcentrate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long concentrateId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String reactionCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String standing;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String arg1;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String arg2;

    public void setConcentrateId(Long concentrateId) 
    {
        this.concentrateId = concentrateId;
    }

    public Long getConcentrateId() 
    {
        return concentrateId;
    }
    public void setReactionCode(String reactionCode) 
    {
        this.reactionCode = reactionCode;
    }

    public String getReactionCode() 
    {
        return reactionCode;
    }
    public void setStanding(String standing) 
    {
        this.standing = standing;
    }

    public String getStanding() 
    {
        return standing;
    }
    public void setArg1(String arg1) 
    {
        this.arg1 = arg1;
    }

    public String getArg1() 
    {
        return arg1;
    }
    public void setArg2(String arg2) 
    {
        this.arg2 = arg2;
    }

    public String getArg2() 
    {
        return arg2;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("concentrateId", getConcentrateId())
            .append("reactionCode", getReactionCode())
            .append("standing", getStanding())
            .append("arg1", getArg1())
            .append("arg2", getArg2())
            .toString();
    }
}
