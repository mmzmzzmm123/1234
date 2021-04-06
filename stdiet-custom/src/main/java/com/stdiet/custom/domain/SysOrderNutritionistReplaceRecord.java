package com.stdiet.custom.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 订单营养师、售后更换记录对象 sys_order_nutritionist_replace_record
 *
 * @author xzj
 * @date 2021-04-02
 */
@Data
public class SysOrderNutritionistReplaceRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 订单ID */
    @Excel(name = "订单ID")
    private Long orderId;

    /** 更换的营养师ID */
    @Excel(name = "更换的营养师ID")
    private Long nutritionistId;

    /** 更换的售后ID */
    @Excel(name = "更换的售后ID")
    private Long afterSaleId;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 删除标识，0未删除1已删除 */
    private Integer delFlag;

    // 售后
    private String afterSale;

    //营养师
    private String nutritionist;
}