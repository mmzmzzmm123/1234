package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 work_feeding
 * 
 * @author ruoyi
 * @date 2022-08-05
 */
public class WorkFeeding extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long feedingId;

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
    private String material;

    public void setFeedingId(Long feedingId) 
    {
        this.feedingId = feedingId;
    }

    public Long getFeedingId() 
    {
        return feedingId;
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
    public void setMaterial(String material) 
    {
        this.material = material;
    }

    public String getMaterial() 
    {
        return material;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("feedingId", getFeedingId())
            .append("reactionCode", getReactionCode())
            .append("mode", getMode())
            .append("interval", getInterval())
            .append("material", getMaterial())
            .toString();
    }
}
