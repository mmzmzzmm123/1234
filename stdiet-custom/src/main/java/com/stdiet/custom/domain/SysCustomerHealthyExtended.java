package com.stdiet.custom.domain;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSON;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 客户健康信息补充对象 sys_customer_healthy_extended
 *
 * @author wonder
 * @date 2021-06-21
 */
@Data
public class SysCustomerHealthyExtended extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** healthy表id */
    @Excel(name = "healthy表id")
    private Long healthyId;

    /** 腰围，厘米 */
    @Excel(name = "腰围，厘米")
    private BigDecimal waist;

    /** 臀围，厘米 */
    @Excel(name = "臀围，厘米")
    private BigDecimal hipline;

    /** 常吃水果信息：水果名称、水果数量 */
    @Excel(name = "常吃水果信息：水果名称、水果数量")
    private String eatFruitsMessage;

    /** 长时间服用药物信息：药物类型、药物名称、用药量、用药时间 */
    @Excel(name = "长时间服用药物信息：药物类型、药物名称、用药量、用药时间")
    private String longEatDrugMessage;

    /** 血糖相关信息 */
    @Excel(name = "血糖相关信息")
    private String bloodSugarMessage;

    /** 郁抑状态评估 */
    @Excel(name = "郁抑状态评估")
    private String depressedStateMessage;

    /** 血压相关信息 */
    @Excel(name = "血压相关信息")
    private String bloodPressureMessage;

    /** 焦虑状态评估 */
    @Excel(name = "焦虑状态评估")
    private String anxietyStateMessage;

    /** 月经不调、多囊信息 */
    @Excel(name = "月经不调、多囊信息")
    private String menstruationMessage;

    /** 删除标识 0未删除 1已删除 */
    private Long delFlag;
}