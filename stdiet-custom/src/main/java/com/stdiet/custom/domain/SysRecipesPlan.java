package com.stdiet.custom.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class SysRecipesPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 订单ID */
    //@Excel(name = "订单ID")
    private Long orderId;

    //非持久化字段，客户姓名
    @Excel(name = "客户姓名")
    private String customer;

    //非持久化字段，客户手机号
    @Excel(name = "客户手机号")
    private String phone;

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

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public Long getOrderId()
    {
        return orderId;
    }
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }
    public void setRecipesId(Long recipesId)
    {
        this.recipesId = recipesId;
    }

    public Long getRecipesId()
    {
        return recipesId;
    }
    public void setSendFlag(Integer sendFlag)
    {
        this.sendFlag = sendFlag;
    }

    public Integer getSendFlag()
    {
        return sendFlag;
    }
    public void setSendTime(Date sendTime)
    {
        this.sendTime = sendTime;
    }

    public Date getSendTime()
    {
        return sendTime;
    }
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag()
    {
        return delFlag;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Long getNutritionistId() {
        return nutritionistId;
    }

    public void setNutritionistId(Long nutritionistId) {
        this.nutritionistId = nutritionistId;
    }

    public String getNutritionist() {
        return nutritionist;
    }

    public void setNutritionist(String nutritionist) {
        this.nutritionist = nutritionist;
    }

    public Long getNutritionistAssisId() {
        return nutritionistAssisId;
    }

    public void setNutritionistAssisId(Long nutritionistAssisId) {
        this.nutritionistAssisId = nutritionistAssisId;
    }

    public String getNutritionistAssis() {
        return nutritionistAssis;
    }

    public void setNutritionistAssis(String nutritionistAssis) {
        this.nutritionistAssis = nutritionistAssis;
    }

    public Date getOrderStartDate() {
        return orderStartDate;
    }

    public void setOrderStartDate(Date orderStartDate) {
        this.orderStartDate = orderStartDate;
    }

    public Date getOrderEndDate() {
        return orderEndDate;
    }

    public void setOrderEndDate(Date orderEndDate) {
        this.orderEndDate = orderEndDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderId", getOrderId())
                .append("startDate", getStartDate())
                .append("endDate", getEndDate())
                .append("recipesId", getRecipesId())
                .append("sendFlag", getSendFlag())
                .append("sendTime", getSendTime())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .append("delFlag", getDelFlag())
                .toString();
    }
}