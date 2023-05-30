package com.ruoyi.office.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户套餐购买记录对象 t_wx_user_package
 *
 * @author ruoyi
 * @date 2023-05-30
 */
public class TWxUserPackage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 套餐名称
     */
    @Excel(name = "套餐名称")
    private String packageName;

    /**
     * 实付金额
     */
    @Excel(name = "实付金额")
    private BigDecimal payAmount;

    /**
     * 赠送金额
     */
    @Excel(name = "赠送金额")
    private BigDecimal giftAmont;

    /**
     * 套餐id
     */
    @Excel(name = "套餐id")
    private Long packId;

    /**
     * 套餐商户
     */
    @Excel(name = "套餐商户")
    private Long merchant;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Long status;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setGiftAmont(BigDecimal giftAmont) {
        this.giftAmont = giftAmont;
    }

    public BigDecimal getGiftAmont() {
        return giftAmont;
    }

    public void setPackId(Long packId) {
        this.packId = packId;
    }

    public Long getPackId() {
        return packId;
    }

    public void setMerchant(Long merchant) {
        this.merchant = merchant;
    }

    public Long getMerchant() {
        return merchant;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id" , getId())
                .append("packageName" , getPackageName())
                .append("payAmount" , getPayAmount())
                .append("giftAmont" , getGiftAmont())
                .append("packId" , getPackId())
                .append("merchant" , getMerchant())
                .append("status" , getStatus())
                .append("remark" , getRemark())
                .append("createBy" , getCreateBy())
                .append("createTime" , getCreateTime())
                .append("updateBy" , getUpdateBy())
                .append("updateTime" , getUpdateTime())
                .toString();
    }
}
