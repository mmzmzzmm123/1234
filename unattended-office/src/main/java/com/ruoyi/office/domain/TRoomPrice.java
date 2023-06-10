package com.ruoyi.office.domain;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 房间价格对象 t_room_price
 *
 * @author ruoyi
 * @date 2023-05-29
 */
@Data
public class TRoomPrice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * room_id
     */
    @Excel(name = "room_id")
    private Long roomId;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "HH:mm")
    @Excel(name = "开始时间" , width = 30)
    private Integer startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "HH:mm")
    @Excel(name = "结束时间" , width = 30)
    private Integer stopTime;

    /**
     * 单价
     */
    @Excel(name = "单价")
    private BigDecimal price;

    /**
     * 会员单价
     */
    @Excel(name = "会员单价")
    private BigDecimal memberPrice;

    /**
     * 状态(空闲/已预约/使用中/预约超时未使用)
     */
    @Excel(name = "状态(空闲/已预约/使用中/预约超时未使用)")
    private Long status;

}
