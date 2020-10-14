package com.ruoyi.custom.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 销售订单对象 sys_order
 *
 * @author wonder
 * @date 2020-09-24
 */
public class SysOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    private Long orderId;

    /**
     * 客户姓名
     */
    @Excel(name = "客户姓名")
    private String customer;

    /**
     * 电话
     */
    @Excel(name = "电话")
    private String phone;

    /**
     * 金额
     */
    @Excel(name = "金额")
    private BigDecimal amount;

    /**
     * 收款方式
     */
    private Long payTypeId;

    /**
     * 收款方式
     */
    @Excel(name = "收款方式")
    private String payType;

    /**
     * 售前
     */
    private Long preSaleId;

    /**
     * 售前
     */
    @Excel(name = "售前")
    private String preSale;

    /**
     * 售后
     */
    private Long afterSaleId;

    /**
     * 售后
     */
    @Excel(name = "售后")
    private String afterSale;

    /**
     * 营养师
     */
    private Long nutritionistId;

    /**
     * 营养师
     */
    @Excel(name = "营养师")
    private String nutritionist;

    /**
     * 助理营养师
     */
    private Long nutriAssisId;

    /**
     * 助理营养师
     */
    @Excel(name = "助理营养师")
    private String nutriAssis;

    /**
     * 账号
     */
    private Long accountId;

    /**
     * 账号
     */
    @Excel(name = "账号")
    private String account;

    /**
     * 策划
     */
    private Long plannerId;

    /**
     * 策划
     */
    @Excel(name = "策划")
    private String planner;

    /**
     * 策划助理
     */
    private Long plannerAssisId;

    /**
     * 策划助理
     */
    @Excel(name = "策划助理")
    private String plannerAssis;

    /**
     * 运营
     */
    private Long operatorId;

    /**
     * 运营
     */
    @Excel(name = "运营")
    private String operator;

    /**
     * 运营助理
     */
    private Long operatorAssisId;

    @Excel(name = "运营助理")
    private String operatorAssis;

    /**
     * 推荐人
     */
    @Excel(name = "推荐人")
    private String recommender;

    /**
     * 服务月数
     */
    @Excel(name = "服务时长")
    private String serveTime;

    private Long serveTimeId;

    @Excel(name = "审核状态", dictType = "cus_review_status")
    private String reviewStatus;

    /**
     * 成交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "成交时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    public String getServeTime() {
        return serveTime;
    }

    public void setServeTimeId(Long serveTimeId) {
        this.serveTimeId = serveTimeId;
    }

    public Long getServeTimeId() {
        return serveTimeId;
    }

    public void setServeTime(String serveTime) {
        this.serveTime = serveTime;
    }

    public void setOperatorAssis(String operatorAssis) {
        this.operatorAssis = operatorAssis;
    }

    public void setOperatorAssisId(Long operatorAssisId) {
        this.operatorAssisId = operatorAssisId;
    }

    public Long getOperatorAssisId() {
        return operatorAssisId;
    }

    public String getOperatorAssis() {
        return operatorAssis;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomer() {
        return customer;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setPayTypeId(Long payTypeId) {
        this.payTypeId = payTypeId;
    }

    public Long getPayTypeId() {
        return payTypeId;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPreSaleId(Long preSaleId) {
        this.preSaleId = preSaleId;
    }

    public Long getPreSaleId() {
        return preSaleId;
    }

    public void setPreSale(String preSale) {
        this.preSale = preSale;
    }

    public String getPreSale() {
        return preSale;
    }

    public void setAfterSaleId(Long afterSaleId) {
        this.afterSaleId = afterSaleId;
    }

    public Long getAfterSaleId() {
        return afterSaleId;
    }

    public void setAfterSale(String afterSale) {
        this.afterSale = afterSale;
    }

    public String getAfterSale() {
        return afterSale;
    }

    public void setNutritionistId(Long nutritionistId) {
        this.nutritionistId = nutritionistId;
    }

    public Long getNutritionistId() {
        return nutritionistId;
    }

    public void setNutritionist(String nutritionist) {
        this.nutritionist = nutritionist;
    }

    public String getNutritionist() {
        return nutritionist;
    }

    public void setNutriAssisId(Long nutriAssisId) {
        this.nutriAssisId = nutriAssisId;
    }

    public Long getNutriAssisId() {
        return nutriAssisId;
    }

    public void setNutriAssis(String nutriAssis) {
        this.nutriAssis = nutriAssis;
    }

    public String getNutriAssis() {
        return nutriAssis;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setPlannerId(Long plannerId) {
        this.plannerId = plannerId;
    }

    public Long getPlannerId() {
        return plannerId;
    }

    public void setPlanner(String planner) {
        this.planner = planner;
    }

    public String getPlanner() {
        return planner;
    }

    public void setPlannerAssisId(Long plannerAssisId) {
        this.plannerAssisId = plannerAssisId;
    }

    public Long getPlannerAssisId() {
        return plannerAssisId;
    }

    public void setPlannerAssis(String plannerAssis) {
        this.plannerAssis = plannerAssis;
    }

    public String getPlannerAssis() {
        return plannerAssis;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public void setRecommender(String recommender) {
        this.recommender = recommender;
    }

    public String getRecommender() {
        return recommender;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("orderId", getOrderId())
                .append("customer", getCustomer())
                .append("phone", getPhone())
                .append("amount", getAmount())
                .append("payTypeId", getPayTypeId())
                .append("payType", getPayType())
                .append("preSaleId", getPreSaleId())
                .append("createBy", getCreateBy())
                .append("preSale", getPreSale())
                .append("createTime", getCreateTime())
                .append("afterSaleId", getAfterSaleId())
                .append("updateBy", getUpdateBy())
                .append("afterSale", getAfterSale())
                .append("updateTime", getUpdateTime())
                .append("nutritionistId", getNutritionistId())
                .append("remark", getRemark())
                .append("nutritionist", getNutritionist())
                .append("nutriAssisId", getNutriAssisId())
                .append("nutriAssis", getNutriAssis())
                .append("accountId", getAccountId())
                .append("account", getAccount())
                .append("plannerId", getPlannerId())
                .append("planner", getPlanner())
                .append("plannerAssisId", getPlannerAssisId())
                .append("plannerAssis", getPlannerAssis())
                .append("operatorId", getOperatorId())
                .append("operator", getOperator())
                .append("operatorAssisId", getOperatorAssisId())
                .append("operatorAssis", getOperatorAssis())
                .append("recommender", getRecommender())
                .append("orderTime", getOrderTime())
                .append("serveTimeId", getServeTimeId())
                .append("serveTime", getServeTime())
                .append("reviewStatus", getReviewStatus())
                .toString();
    }
}