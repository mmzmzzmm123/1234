package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 work_heating
 * 
 * @author ruoyi
 * @date 2022-08-05
 */
public class WorkHeating extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long heatingId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String reactionCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String mode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String interval;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long temperature;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long temperatureDuration;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long stirDuration;

    public void setHeatingId(Long heatingId) 
    {
        this.heatingId = heatingId;
    }

    public Long getHeatingId() 
    {
        return heatingId;
    }
    public void setReactionCode(String reactionCode) 
    {
        this.reactionCode = reactionCode;
    }

    public String getReactionCode() 
    {
        return reactionCode;
    }
    public void setMode(String mode) 
    {
        this.mode = mode;
    }

    public String getMode() 
    {
        return mode;
    }
    public void setInterval(String interval) 
    {
        this.interval = interval;
    }

    public String getInterval() 
    {
        return interval;
    }
    public void setTemperature(Long temperature) 
    {
        this.temperature = temperature;
    }

    public Long getTemperature() 
    {
        return temperature;
    }
    public void setTemperatureDuration(Long temperatureDuration) 
    {
        this.temperatureDuration = temperatureDuration;
    }

    public Long getTemperatureDuration() 
    {
        return temperatureDuration;
    }
    public void setStirDuration(Long stirDuration) 
    {
        this.stirDuration = stirDuration;
    }

    public Long getStirDuration() 
    {
        return stirDuration;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("heatingId", getHeatingId())
            .append("reactionCode", getReactionCode())
            .append("mode", getMode())
            .append("interval", getInterval())
            .append("temperature", getTemperature())
            .append("temperatureDuration", getTemperatureDuration())
            .append("stirDuration", getStirDuration())
            .toString();
    }
}
