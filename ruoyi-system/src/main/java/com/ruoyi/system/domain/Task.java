package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 任务表
 * @TableName t_task
 */
@TableName(value ="t_task")
@Data
public class Task implements Serializable {
    /**
     * 任务ID
     */
    @TableId(value = "task_id")
    private String taskId;

    /**
     * 订单编号
     */
    @TableField(value = "order_id")
    private String orderId;

    /**
     * 任务名称
     */
    @TableField(value = "task_name")
    private String taskName;

    /**
     * 任务类型 TaskTypeEnum
     */
    @TableField(value = "task_type")
    private String taskType;

    /**
     * 平台类型 0-Telegram;1-Whatsapp
     */
    @TableField(value = "platform")
    private Integer platform;

    /**
     * 任务状态
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 冻结余额
     */
    @TableField(value = "freeze_balance")
    private Integer freezeBalance;

    /**
     * 消耗余额
     */
    @TableField(value = "spent_balance")
    private Integer spentBalance;

    /**
     * 任务启动时间
     */
    @TableField(value = "start_time")
    private LocalDateTime startTime;

    /**
     * 任务结束时间
     */
    @TableField(value = "stop_time")
    private LocalDateTime stopTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 预期执行时间
     */
    @TableField(value = "expected_time")
    private LocalDateTime expectedTime;

    /**
     * 归属商家ID
     */
    @TableField(value = "merchant_id")
    private String merchantId;

    /**
     * 创建人ID
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 任务最近暂停时间
     */
    @TableField(value = "pause_time")
    private LocalDateTime pauseTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}