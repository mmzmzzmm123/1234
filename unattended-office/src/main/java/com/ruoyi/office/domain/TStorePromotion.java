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
 * 优惠券 t_store_promotion对象 t_store_promotion
 * 
 * @author ruoyi
 * @date 2023-06-14
 */
@Data
public class TStorePromotion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 优惠券类型(满减、免费体验券) */
    @Excel(name = "优惠券类型(满减、免费体验券)")
    private String couponType;

    /** 适用商户 0 所有商户适用 */
    @Excel(name = "适用商户 0 所有商户适用")
    private Long merchantId;

    /** 适用门店 0 所有门店适用 */
    @Excel(name = "适用门店 0 所有门店适用")
    private Long storeId;

    /** 标准金额 */
    @Excel(name = "标准金额")
    private BigDecimal standardPrice;

    /** 优惠金额 */
    @Excel(name = "优惠金额")
    private BigDecimal discountPrice;

    /** 抵扣时长 */
    @Excel(name = "抵扣时长")
    private Long maxMinute;

    /** 可领取次数 */
    @Excel(name = "可领取次数")
    private Long maxHold;

    /** 有效期类型(1:自购买日起2:开始结束日期） */
    @Excel(name = "有效期类型(1:自购买日起2:开始结束日期）")
    private Integer validType;

    /** 自购买日期有效天数 */
    @Excel(name = "自购买日期有效天数")
    private Long validDays;

    /** 开始日期（维护则为有效期开始） */
    @Excel(name = "开始日期", readConverterExp = "维护则为有效期开始")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /** 结束日期（维护则为有效期结束） */
    @Excel(name = "结束日期", readConverterExp = "维护则为有效期结束")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /** 周内可用日期(1,3,4,7) */
    @Excel(name = "周内可用日期(1,3,4,7)")
    private String weekDays;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** logo */
    @Excel(name = "logo")
    private String logo;
}
