package com.stdiet.custom.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

/**
 * 食谱计划对象 sys_recipes_plan
 *
 * @author xzj
 * @date 2021-01-15
 */
@Data
public class SysRecipesPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 订单ID */
    //@Excel(name = "订单ID")
    private Long orderId;

    private Long cusId;

    //客户ID
    private Long customerId;

    //非持久化字段，客户姓名
    @Excel(name = "客户姓名")
    private String customer;

    //非持久化字段，客户手机号
    private String phone;

    @Excel(name = "客户手机号")
    //处理过的客户手机号
    private String hidePhone;

    /** 食谱开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "食谱开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 食谱结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "食谱结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    //非持久化字段，服务开始时间
    //@Excel(name = "服务开始时间", dateFormat = "yyyy-MM-dd")
    private Date orderStartDate;

    //非持久化字段，服务结束时间
    //@Excel(name = "服务结束时间", dateFormat = "yyyy-MM-dd")
    private Date orderEndDate;

    //非持久化字段，营养师ID
    private Long nutritionistId;

    //非持久化字段，营养师姓名
    @Excel(name = "营养师")
    private String nutritionist;

    //非持久化字段，营养师助理ID
    private Long nutritionistAssisId;

    //非持久化字段，营养师助理姓名
    @Excel(name = "营养师助理")
    private String nutritionistAssis;

    /** 食谱ID */
    //@Excel(name = "食谱ID")
    private Long recipesId;

    /** 食谱是否发送，0未发送 1已发送 */
    @Excel(name = "食谱是否发送", readConverterExp="0=未发送,1=已发送")
    private Integer sendFlag;

    /** 食谱发送时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "食谱发送时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sendTime;

    /** 删除标识 0未删除 1已删除 默认0 */
    private Integer delFlag;
}