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
 * 用户优惠券 t_wx_user_promotion对象 t_wx_user_promotion
 * 
 * @author ruoyi
 * @date 2023-06-14
 */
@Data
public class TWxUserPromotion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** wx用户 */
    @Excel(name = "wx用户")
    private Long wxId;

    /** 优惠券id */
    @Excel(name = "优惠券id")
    private Long couponId;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 优惠券类型(满减、免费体验券) */
    @Excel(name = "优惠券类型(满减、免费体验券)")
    private Integer couponType;

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
