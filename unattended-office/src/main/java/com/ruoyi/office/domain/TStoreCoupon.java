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
 * 卡券对象 t_store_coupon
 *
 * @author ruoyi
 * @date 2023-05-31
 */
@Data
public class TStoreCoupon extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String couponName;

    /**
     * 适用门店
     */
    @Excel(name = "适用门店")
    private String storeId;

    /**
     * 原价
     */
    @Excel(name = "原价")
    private BigDecimal originalPrice;

    /**
     * 当前价格
     */
    @Excel(name = "当前价格")
    private BigDecimal price;

    /**
     * 卡券类型(时长卡、次数卡)
     */
    @Excel(name = "卡券类型(时长卡、次数卡)")
    private String cardType;

    /**
     * 可使用次数(针对次数卡)
     */
    @Excel(name = "可使用次数(针对次数卡)")
    private Long numCanUse;

    /**
     * 单日只扣一次次数(针对次数卡)
     */
    @Excel(name = "单日只扣一次次数(针对次数卡)")
    private String oneTimeOneDay;

    /**
     * 每次最多抵扣时长(针对时长卡)
     */
    @Excel(name = "每次最多抵扣时长(针对时长卡)")
    private Long maxMinuteOnce;

    /**
     * 购买限制(余额/积分)
     */
    @Excel(name = "购买限制(余额/积分)")
    private String payType;

    /**
     * 可购买次数
     */
    @Excel(name = "可购买次数")
    private Long maxHold;

    /**
     * 有效期类型(1:自购买日起2:开始结束日期）
     */
    @Excel(name = "有效期类型")
    private Integer validType;

    /**
     * 自购买日期有效天数
     */
    @Excel(name = "自购买日期有效天数")
    private Integer validDays;

    /**
     * 开始日期（维护则为有效期开始）
     */
    @Excel(name = "开始日期", readConverterExp = "维=护则为有效期开始")
    private Date startDate;

    /**
     * 结束日期（维护则为有效期结束）
     */
    @Excel(name = "结束日期", readConverterExp = "维=护则为有效期结束")
    private Date endDate;

    /**
     * 周内可用日期(1,3,4,7)
     */
    @Excel(name = "周内可用日期(1,3,4,7)")
    private String weekDays;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Long status;

}
