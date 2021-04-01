package com.stdiet.custom.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
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
    //@Excel(name = "编号")
    private Long orderId;

    /**
     * 成交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "成交时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

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

    @JsonFormat(pattern = "yyyy-MM-dd")
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

    /** 售中ID */
    private Long onSaleId;

    @Excel(name = "售中")
    private String onSale;

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
    //@Excel(name = "推荐人")
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
    //@Excel(name = "服务天数", width = 30)
    private Integer serverDay;

    /**
     * 订单暂停记录 非持久化字段
     * */
    //private List<SysOrderPause> orderPauseList;

    //查询参数
    private Integer amountFlag;

    /** 订单类型 0普通单 1比例拆分单 2体验单 */
    private String orderType;

    @Excel(name = "订单类型")
    private String orderTypeName;

    /** 订单次数类型 0一开单  1二开单 */
    private String orderCountType;

    /** 订单金额类型 0全款单  1定金单  2尾款单 */
    private String orderMoneyType;

    /** 拆分订单中的主订单id，非拆分订单时，该id都为0 */
    private Long mainOrderId;

    /** 售后二开提成单, 0非提成单 1提成单 */
    private Integer afterSaleCommissOrder;

    /** 提成计算开始时间，与食谱计划开始时间区分开 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    //@Excel(name = "提成计算开始时间，与食谱计划开始时间区分开", width = 30, dateFormat = "yyyy-MM-dd")
    private Date commissStartTime;

    /** 删除标记 */
    private Integer delFlag;

    //订单类型数组，用于接收订单类型、订单次数类型、订单金额类型，非持久化字段
    private Integer[] orderTypeList;

    //是否自动创建售后二开提成单，0否 1是，非持久化字段
    private Integer secondAfterSaleFlag;

    //营养师数组，比例拆分单时需要两个营养师，非持久化字段
    private Long[] nutritionistIdList;

    //拆分比例，如：1,9就是按照比例10%、90%拆分，非持久化字段
    private String nutritionistRate;

    //订单查询时的订单类型二维数组
    private JSONArray searchOrderTypeArray;



}