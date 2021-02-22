package com.stdiet.custom.domain;

import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 外食热量统计对象 sys_food_heat_statistics
 *
 * @author xzj
 * @date 2021-02-19
 */
@Data
public class SysFoodHeatStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 客户热量统计ID */
    @Excel(name = "客户热量统计ID")
    private Long customerHeatId;

    /** 食材 */
    @Excel(name = "食材")
    private String ingredient;

    /** 通俗单位ID */
    @Excel(name = "通俗单位ID")
    private Long unit;

    private String unitName;

    /** 通俗单位数量 */
    @Excel(name = "通俗单位数量")
    private Integer number;

    /** 具体质量，单位：克 */
    @Excel(name = "具体质量，单位：克")
    private Integer quantity;

    /** 类型，0早 1中 2晚 */
    @Excel(name = "类型，0早 1中 2晚")
    private Integer edibleType;

    /** 热量数值 */
    @Excel(name = "热量数值")
    private Integer heatValue;

    /** 删除标识 0未删除 1已删除 */
    private Integer delFlag;



}