package com.ruoyi.office.domain;

import java.math.BigDecimal;
import java.util.Date;

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
public class TWxUserCoupon extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setStoreId(Long storeId)
    {
        this.storeId = storeId;
    }

    public Long getStoreId()
    {
        return storeId;
    }
    public void setCouponId(Long couponId) 
    {
        this.couponId = couponId;
    }

    public Long getCouponId() 
    {
        return couponId;
    }
    public void setCouponName(String couponName) 
    {
        this.couponName = couponName;
    }

    public String getCouponName() 
    {
        return couponName;
    }
    public void setOriginalPrice(BigDecimal originalPrice) 
    {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getOriginalPrice() 
    {
        return originalPrice;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setCardType(String cardType) 
    {
        this.cardType = cardType;
    }

    public String getCardType() 
    {
        return cardType;
    }
    public void setNumCanUse(Long numCanUse) 
    {
        this.numCanUse = numCanUse;
    }

    public Long getNumCanUse() 
    {
        return numCanUse;
    }
    public void setOneTimeOneDay(String oneTimeOneDay) 
    {
        this.oneTimeOneDay = oneTimeOneDay;
    }

    public String getOneTimeOneDay() 
    {
        return oneTimeOneDay;
    }
    public void setMaxMinuteOnce(Long maxMinuteOnce) 
    {
        this.maxMinuteOnce = maxMinuteOnce;
    }

    public Long getMaxMinuteOnce() 
    {
        return maxMinuteOnce;
    }
    public void setMinuteLeft(Long minuteLeft) 
    {
        this.minuteLeft = minuteLeft;
    }

    public Long getMinuteLeft() 
    {
        return minuteLeft;
    }
    public void setStartDate(Date startDate) 
    {
        this.startDate = startDate;
    }

    public Date getStartDate() 
    {
        return startDate;
    }
    public void setEndDate(Date endDate) 
    {
        this.endDate = endDate;
    }

    public Date getEndDate() 
    {
        return endDate;
    }
    public void setWeekDays(String weekDays) 
    {
        this.weekDays = weekDays;
    }

    public String getWeekDays() 
    {
        return weekDays;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("storeId", getStoreId())
            .append("couponId", getCouponId())
            .append("couponName", getCouponName())
            .append("originalPrice", getOriginalPrice())
            .append("price", getPrice())
            .append("cardType", getCardType())
            .append("numCanUse", getNumCanUse())
            .append("oneTimeOneDay", getOneTimeOneDay())
            .append("maxMinuteOnce", getMaxMinuteOnce())
            .append("minuteLeft", getMinuteLeft())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("weekDays", getWeekDays())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
