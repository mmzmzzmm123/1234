package com.ruoyi.gauge.domain;

import java.math.BigDecimal;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 心理测评对象 psy_gauge
 * 
 * @author ruoyi
 * @date 2022-08-30
 */
@Data
public class PsyGauge extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 测评标题 */
    @Excel(name = "测评标题")
    private String title;

    /** 子标题 */
    @Excel(name = "子标题")
    private String subtitle;

    /** 头部图片 */
    @Excel(name = "头部图片")
    private String headPicture;

    /** 测评描述 */
    @Excel(name = "测评描述")
    private String introduce;

    /** 测评分类 */
    @Excel(name = "测评分类")
    private Integer gaugeClass;

    /** 测评题数 */
    @Excel(name = "测评题数")
    private Integer gaugeNum;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 测评次数 */
    @Excel(name = "测评次数")
    private Integer num;

    /** 测评类型 */
    @Excel(name = "测评类型")
    private Integer type;

    @Excel(name = "测评简介")
    private String gaugeDes;

    @Excel(name = "列表展示图片")
    private String listShowPicture;

}
