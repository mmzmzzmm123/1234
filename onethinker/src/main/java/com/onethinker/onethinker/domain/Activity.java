package com.onethinker.onethinker.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 活动对象 t_activity
 *
 * @author yangyouqi
 * @date 2023-10-31
 */
@Data
@TableName("t_activity")
public class Activity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 表主键
     */
    private Long id;

    /**
     * 是否可见，0为不可见，1为可见
     */
    @Excel(name = "是否可见，0为不可见，1为可见")
    private Integer enabled;

    /**
     * 权重
     */
    @Excel(name = "权重")
    private Long weight;

    /**
     * 活动开始时间
     */
    @Excel(name = "活动开始时间")
    private Long startTime;

    /**
     * 活动结束时间
     */
    @Excel(name = "活动结束时间")
    private Long endTime;

    /**
     * 活动标题
     */
    @Excel(name = "活动标题")
    private String title;

    /**
     * 活动背景图
     */
    @Excel(name = "活动背景图")
    private String bgUrl;

    /**
     * 批次号
     */
    @Excel(name = "批次号")
    private String batchNo;

    /**
     * 活动类型id
     */
    @Excel(name = "活动类型id")
    private Integer activityType;


}
