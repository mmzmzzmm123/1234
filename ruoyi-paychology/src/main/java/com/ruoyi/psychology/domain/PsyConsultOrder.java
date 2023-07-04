package com.ruoyi.psychology.domain;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BasePlusEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 咨询订单对象 psy_consult_order
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("psy_consult_order")
public class PsyConsultOrder extends BasePlusEntity implements Serializable
{

    private static final long serialVersionUID = 6679722822685766673L;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderNo;

    /** 咨询师 */
//    @Excel(name = "咨询师")
    private Long consultId;

    /** 咨询师 */
    @Excel(name = "咨询师")
    private String consultName;

    /** 服务 */
    @Excel(name = "服务")
    private Long serveId;

    /** 服务 */
    @Excel(name = "服务")
    private String serveName;

    /** 客户id */
//    @Excel(name = "客户id")
    private Long userId;

    @Excel(name = "客户")
    private String userName;

    /** 排班 */
//    @Excel(name = "排班")
    private Long workId;

    /** 可选班次0全天 1上午 2下午 3晚上 */
    @Excel(name = "可选班次")
    @TableField(value = "type", updateStrategy = FieldStrategy.IGNORED)
    private String type;

    /** 天 */
    @Excel(name = "日期")
    @TableField(value = "day", updateStrategy = FieldStrategy.IGNORED)
    private String day;

    /** 周 */
    @Excel(name = "星期")
    @TableField(value = "week", updateStrategy = FieldStrategy.IGNORED)
    private String week;

    /** 咨询时段开始 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "咨询时段开始", width = 30, dateFormat = "yyyy-MM-dd")
    @TableField(value = "time_start", updateStrategy = FieldStrategy.IGNORED)
    private Date timeStart;

    /** 咨询时段结束 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "咨询时段结束", width = 30, dateFormat = "yyyy-MM-dd")
    @TableField(value = "time_end", updateStrategy = FieldStrategy.IGNORED)
    private Date timeEnd;

    /** 咨询时长(分钟) */
    @Excel(name = "咨询时长(分钟)")
    @TableField(value = "time", updateStrategy = FieldStrategy.IGNORED)
    private Integer time;

    /** 可预约数量 */
    @Excel(name = "可预约数量")
    private Integer num;

    /** 已预约数量 */
    @Excel(name = "已预约数量")
    private Integer buyNum;

    /** 应付费用 */
    @Excel(name = "应付费用")
    private BigDecimal amount;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 订单状态0-待付款 1-待预约 2-待咨询 3-已完成 4-已取消 */
    @Excel(name = "订单状态0-待付款 1-待预约 2-待咨询 3-已完成 4-已取消")
    private Integer status;
}
