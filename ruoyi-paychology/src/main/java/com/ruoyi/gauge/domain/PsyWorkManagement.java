package com.ruoyi.gauge.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 排班管理对象 psy_work_management
 * 
 * @author ruoyi
 * @date 2023-01-30
 */
public class PsyWorkManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 咨询师id */
    @Excel(name = "咨询师id")
    private Long consultantId;

    /** 可选班次0全天1上午2下午 3晚上 */
    @Excel(name = "可选班次0全天1上午2下午 3晚上")
    private String shift;

    /** 咨询时段 */
    @Excel(name = "咨询时段")
    private Long timeInterval;

    /** 咨询时长(分钟) */
    @Excel(name = "咨询时长(分钟)")
    private Long time;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setConsultantId(Long consultantId) 
    {
        this.consultantId = consultantId;
    }

    public Long getConsultantId() 
    {
        return consultantId;
    }
    public void setShift(String shift) 
    {
        this.shift = shift;
    }

    public String getShift() 
    {
        return shift;
    }
    public void setTimeInterval(Long timeInterval) 
    {
        this.timeInterval = timeInterval;
    }

    public Long getTimeInterval() 
    {
        return timeInterval;
    }
    public void setTime(Long time) 
    {
        this.time = time;
    }

    public Long getTime() 
    {
        return time;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("consultantId", getConsultantId())
            .append("shift", getShift())
            .append("timeInterval", getTimeInterval())
            .append("time", getTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
