package com.stdiet.custom.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    /** 客户ID */
    @Excel(name = "客户ID")
    private Long customerId;

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

    /** 食用日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "食用日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date edibleDate;

    /** 类型，0早 1中 2晚 */
    @Excel(name = "类型，0早 1中 2晚")
    private Integer edibleType;

    /** 热量数值 */
    @Excel(name = "热量数值")
    private Integer heatValue;

    /** 热量缺口 */
    @Excel(name = "热量缺口")
    private Integer heatGap;

    /** 删除标识 0未删除 1已删除 */
    private Integer delFlag;

}