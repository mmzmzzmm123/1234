package com.stdiet.custom.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

/**
 * 销售订单对象 sys_order
 *
 * @author wonder
 * @date 2020-09-24
 */
@Data
public class SysOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    private Long orderId;

    private Long cusId;

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

    private BigDecimal weight;

    private String status;

    private Date startTime;

    private Date pauseTime;

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

    /**
     * 赠送时长
     */
    @Excel(name = "赠送时长", width = 30, suffix = "天")
    private Integer giveServeDay;

    private Long serveTimeId;

    @Excel(name = "审核状态", dictType = "cus_review_status")
    private String reviewStatus;

    /**
     * 成交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "成交时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    /**
     * 调理项目id
     */
    private Integer conditioningProjectId;

    /**
     * 调理项目
     */
    @Excel(name = "调理项目", width = 30)
    private String conditioningProject;

    /**
     * 服务结束时间（根据开始日期、服务月份、暂停记录计算而来）
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "服务结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date serverEndTime;

    /**
     * 进粉时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "进粉时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date becomeFanTime;

    /**
     * 服务天数
     */
    @Excel(name = "服务结束时间", width = 30)
    private Integer serverDay;

    /**
     * 订单暂停记录 非持久化字段
     * */
    private List<SysOrderPause> orderPauseList;
    private Integer amountFlag;

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getStartTime() {
        return startTime;
    }

}