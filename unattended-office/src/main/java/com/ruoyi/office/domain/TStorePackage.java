package com.ruoyi.office.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 店铺充值套餐对象 t_store_package
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
public class TStorePackage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 套餐名称 */
    @Excel(name = "套餐名称")
    private String packageName;

    /** 实付金额 */
    @Excel(name = "实付金额")
    private BigDecimal payAmount;

    /** 赠送金额 */
    @Excel(name = "赠送金额")
    private BigDecimal giftAmont;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 门店id */
    @Excel(name = "门店id")
    private Long storeId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPackageName(String packageName) 
    {
        this.packageName = packageName;
    }

    public String getPackageName() 
    {
        return packageName;
    }
    public void setPayAmount(BigDecimal payAmount) 
    {
        this.payAmount = payAmount;
    }

    public BigDecimal getPayAmount() 
    {
        return payAmount;
    }
    public void setGiftAmont(BigDecimal giftAmont) 
    {
        this.giftAmont = giftAmont;
    }

    public BigDecimal getGiftAmont() 
    {
        return giftAmont;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("packageName", getPackageName())
            .append("payAmount", getPayAmount())
            .append("giftAmont", getGiftAmont())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
