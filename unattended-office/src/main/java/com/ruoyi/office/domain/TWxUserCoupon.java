package com.ruoyi.office.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户优惠券对象 t_wx_user_coupon
 * 
 * @author ruoyi
 * @date 2023-06-05
 */
@Data
public class TWxUserCoupon extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    private Long merchantId;

    /** 适用门店 */
    @Excel(name = "适用门店")
    private Long storeId;

    /** 卡券id */
    @Excel(name = "卡券id")
    private Long couponId;

    /** 名称 */
    @Excel(name = "名称")
    private String couponName;

    /** 原价 */
    @Excel(name = "原价")
    private BigDecimal originalPrice;

    /** 当前价格 */
    @Excel(name = "当前价格")
    private BigDecimal price;

    /** 卡券类型(时长卡、次数卡) */
    @Excel(name = "卡券类型(时长卡、次数卡)")
    private String cardType;

    /** 可使用次数(针对次数卡) */
    @Excel(name = "可使用次数(针对次数卡)")
    private Long numCanUse;

    /** 单日只扣一次次数(针对次数卡) */
    @Excel(name = "单日只扣一次次数(针对次数卡)")
    private String oneTimeOneDay;

    /** 每次最多抵扣时长(针对时长卡) */
    @Excel(name = "每次最多抵扣时长(针对时长卡)")
    private Long maxMinuteOnce;

    /** 剩余时长 */
    @Excel(name = "剩余时长")
    private Long minuteLeft;

    /** 开始日期（维护则为有效期开始） */
    @Excel(name = "开始日期", readConverterExp = "维=护则为有效期开始")
    private Date startDate;

    /** 结束日期（维护则为有效期结束） */
    @Excel(name = "结束日期", readConverterExp = "维=护则为有效期结束")
    private Date endDate;

    /** 周内可用日期(1,3,4,7) */
    @Excel(name = "周内可用日期(1,3,4,7)")
    private String weekDays;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

}
