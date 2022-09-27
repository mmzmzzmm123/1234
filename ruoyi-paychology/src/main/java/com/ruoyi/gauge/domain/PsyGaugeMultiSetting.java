package com.ruoyi.gauge.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 心理测评多维设置对象 psy_gauge_multi_setting
 * 
 * @author ruoyi
 * @date 2022-09-10
 */
public class PsyGaugeMultiSetting extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 测评表单ID */
    @Excel(name = "测评表单ID")
    private Long gaugeId;

    /** 纬度名 */
    @Excel(name = "纬度名")
    private String name;


    /** 绑定问题ID */
    @Excel(name = "绑定问题ID")
    private String questionIds;

    @Excel(name = "得分设置ID")
    private String scoreSettingIds;

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
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setQuestionIds(String questionIds) 
    {
        this.questionIds = questionIds;
    }

    public String getQuestionIds() 
    {
        return questionIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("gaugeId", getGaugeId())
            .append("name", getName())
            .append("questionIds", getQuestionIds())
            .append("scoreSettingIds", getScoreSettingIds())
            .toString();
    }

    public String getScoreSettingIds() {
        return scoreSettingIds;
    }

    public void setScoreSettingIds(String scoreSettingIds) {
        this.scoreSettingIds = scoreSettingIds;
    }
}
