package com.ruoyi.gauge.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 测评标签对象 psy_gauge_label
 * 
 * @author ruoyi
 * @date 2022-10-18
 */
public class PsyGaugeLabel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 量表id */
//    @Excel(name = "量表id")
    private Long gaugeId;

    @Excel(name = "量表名称")
    private String gaugeName;

    /** 量表标签(0-精选测评，1-热门推荐) */
    @Excel(name = "量表标签(0-精选测评，1-热门推荐)")
    private Long label;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGaugeId(Long gaugeId) 
    {
        this.gaugeId = gaugeId;
    }

    public Long getGaugeId() 
    {
        return gaugeId;
    }
    public void setLabel(Long label) 
    {
        this.label = label;
    }

    public Long getLabel() 
    {
        return label;
    }

    public String getGaugeName() {
        return gaugeName;
    }

    public void setGaugeName(String gaugeName) {
        this.gaugeName = gaugeName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("gaugeId", getGaugeId())
            .append("label", getLabel())
            .toString();
    }
}
