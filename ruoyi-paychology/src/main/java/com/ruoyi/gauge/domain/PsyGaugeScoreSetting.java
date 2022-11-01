package com.ruoyi.gauge.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 心理测评普通设置对象 psy_gauge_Score_setting
 * 
 * @author ruoyi
 * @date 2022-09-10
 */
public class PsyGaugeScoreSetting extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 测评表单ID */
    @Excel(name = "测评表单ID")
    private Long gaugeId;

    /** 得分范围start */
    @Excel(name = "得分范围start")
    private Long start;

    /** 得分范围end */
    @Excel(name = "得分范围end")
    private Long end;

    /** 建议 */
    @Excel(name = "建议")
    private String proposal;

    @Excel(name = "结论")
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setStart(Long start) 
    {
        this.start = start;
    }

    public Long getStart() 
    {
        return start;
    }
    public void setEnd(Long end) 
    {
        this.end = end;
    }

    public Long getEnd() 
    {
        return end;
    }
    public void setProposal(String proposal) 
    {
        this.proposal = proposal;
    }

    public String getProposal() 
    {
        return proposal;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("gaugeId", getGaugeId())
            .append("start", getStart())
            .append("end", getEnd())
            .append("proposal", getProposal())
            .toString();
    }

    public Long getGaugeId() {
        return gaugeId;
    }

    public void setGaugeId(Long gaugeId) {
        this.gaugeId = gaugeId;
    }
}
