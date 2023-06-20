package com.ruoyi.gauge.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 心理测评订单信息对象 psy_order
 * 
 * @author ruoyi
 * @date 2022-10-12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PsyOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Integer id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderId;

    /** 测评编号 */
    @Excel(name = "测评编号")
    private Integer gaugeId;

    /** 订单状态(1-创建,2-完成,3-关闭) */
    @Excel(name = "订单状态(1-创建,2-完成,3-关闭)")
    private Integer orderStatus;

    /** 应付金额 */
    @Excel(name = "应付金额")
    private BigDecimal amount;

    /** 测评完成情况(1-已完成，2-未完成) */
    @Excel(name = "测评完成情况(1-已完成，2-未完成)")
    private Integer gaugeStatus;

    /** 用户id */
    @Excel(name = "用户id")
    private Integer userId;

    /** 用户id */
    @Excel(name = "用户名")
    private String userName;

    /**
     * 量表标题
     */
    @Excel(name = "量表标题")
    private String gaugeTitle;

    /**
     * 量表简介
     */
    private String gaugeDes;

    /**
     * 总得分
     */
    private String score;

    /**
     * 总得分对应图片
     */
    private String resultUrl;
}
