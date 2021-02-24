package com.stdiet.custom.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 外食热量统计对象 sys_customer_heat_statistics
 *
 * @author xzj
 * @date 2021-02-20
 */
@Data
public class SysCustomerHeatStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 客户ID */
    @Excel(name = "客户ID")
    private Long customerId;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date edibleDate;

    /** 最大可摄入量 */
    @Excel(name = "最大可摄入量")
    private Integer maxHeatValue;

    /** 当天食材总热量 */
    @Excel(name = "当天食材总热量")
    private Integer heatValue;

    /** 当天摄入蛋白质热量 */
    @Excel(name = "当天摄入蛋白质热量")
    private Integer proteinHeat;

    /** 当天摄入脂肪热量 */
    @Excel(name = "当天摄入脂肪热量")
    private Integer fatHeat;

    /** 当天摄入碳水热量 */
    @Excel(name = "当天摄入碳水热量")
    private Integer carbonWaterHeat;

    /** 当天热量缺口 */
    @Excel(name = "当天热量缺口")
    private Integer heatGap;

    /** 删除标识 0未删除 1已删除 */
    private Integer delFlag;

    //食材热量ID
    private Long[] foodHeatIdList;

    //食材热量
    private Integer[] foodHeatList;

    //食材蛋白质质量
    private Integer[] proteinQualityList;

    //食材脂肪质量
    private Integer[] fatQualityList;

    //食材碳水质量
    private Integer[] carbonWaterQualityList;

    //具体食材集合
    private List<SysFoodHeatStatistics> foodHeatStatisticsList;
}