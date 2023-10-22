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
 * 续费订单对象 t_room_order_charge
 *
 * @author ruoyi
 * @date 2023-10-22
 */
@Data
public class TRoomOrderCharge extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 订单号（roomId+yyyyMMdd+xx)
     */
    @Excel(name = "订单号", readConverterExp = "订单号（roomId+yyyyMMdd+xx)")
    private Long orderNo;

    /**
     * 原订单id
     */
    @Excel(name = "原订单id")
    private Long orgOrderId;

    /**
     * room_charge.id
     */
    @Excel(name = "room_charge.id")
    private Long chargePackId;

    /**
     * 房间id
     */
    @Excel(name = "房间id")
    private Long roomId;

    /**
     * 支付人
     */
    @Excel(name = "支付人")
    private Long wxUserId;

    /**
     * 续费时长
     */
    @Excel(name = "续费时长")
    private Long chargeMinute;

    /**
     * 原订单结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "原订单结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 支付方式
     */
    @Excel(name = "支付方式")
    private Integer payType;

    /**
     * 金额
     */
    @Excel(name = "金额")
    private BigDecimal totalAmount;

    /**
     * 现金支付金额
     */
    @Excel(name = "现金支付金额")
    private BigDecimal payAmount;

    /**
     * 福利支付金额
     */
    @Excel(name = "福利支付金额")
    private BigDecimal welfareAmount;

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

    /**
     * wx预支付id
     */
    @Excel(name = "wx预支付id")
    private String prepayId;

    /**
     * 状态(待支付/已支付/已取消)
     */
    @Excel(name = "状态(待支付/已支付/已取消)")
    private String status;

}
