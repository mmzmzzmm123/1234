package com.ruoyi.office.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）对象 t_room_order
 *
 * @author ruoyi
 * @date 2023-05-29
 */
@Data
public class TRoomOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 订单号
     */
    @Excel(name = "订单号")
    private Long orderNo;

    /**
     * 房间id
     */
    @Excel(name = "房间id")
    private Long roomId;

    /**
     * 使用人id
     */
    @Excel(name = "使用人id")
    private Long userId;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "开始时间", width = 40, dateFormat = "yyyy-MM-dd HH:mm")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "结束时间", width = 40, dateFormat = "yyyy-MM-dd HH:mm")
    private Date endTime;

    /**
     * 支付方式
     */
    @Excel(name = "支付方式", width = 40)
    private Integer payType;

    /**
     * 金额
     */
    @Excel(name = "金额")
    private BigDecimal totalAmount;

    /**
     * 支付金额
     */
    @Excel(name = "支付金额")
    private BigDecimal payAmount;

    /**
     * 卡券金额
     */
    @Excel(name = "卡券金额")
    private BigDecimal couponAmount;

    /**
     * 使用卡券
     */
    @Excel(name = "使用卡券")
    private Long couponId;

    private String prepayId;

    /**
     * 状态(空闲/已预约/使用中/预约超时未使用)
     */
    @Excel(name = "状态(0空闲/1待支付/2已预约/3使用中/4预约超时未使用/5已完成/9取消)")
    private Integer status;

}
