package com.ruoyi.gauge.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 心理测评普通设置对象 psy_gauge_score_setting
 * 
 * @author ruoyi
 * @date 2022-09-10
 */
@Data
public class PsyGaugeScoreSetting extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Integer id;

    /** 测评表单ID */
    @Excel(name = "测评表单ID")
    private Integer gaugeId;

    /** 得分范围start */
    @Excel(name = "得分范围start")
    private Integer start;

    /** 得分范围end */
    @Excel(name = "得分范围end")
    private Integer end;

    /** 建议 */
    @Excel(name = "建议")
    private String proposal;

    @Excel(name = "结论")
    private String result;


}
