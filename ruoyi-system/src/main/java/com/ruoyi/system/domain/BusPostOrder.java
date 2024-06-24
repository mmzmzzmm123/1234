package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单对象 bus_post_order
 * 
 * @author ruoyi
 * @date 2024-06-20
 */
public class BusPostOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    private Long orderId;

    /** 商家ID */
    @Excel(name = "商家ID")
    private Long merchantId;

    /** 订单标题 */
    @Excel(name = "订单标题")
    private String title;

    /** 订单金额 */
    @Excel(name = "订单金额")
    private Long amount;

    /** 订单时效（小时） */
    @Excel(name = "订单时效", readConverterExp = "小=时")
    private Integer validityPeriod;

    /** 产品类型 */
    @Excel(name = "产品类型")
    private Integer productType;

    /** 总数量 */
    @Excel(name = "总数量")
    private Integer quantity;

    /** 打烊时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "打烊时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sampleTime;

    /** 时效保证金 */
    @Excel(name = "时效保证金")
    private Long securityDeposit;

    /** 运费承担方 */
    @Excel(name = "运费承担方")
    private String shippingParty;

    /** 备注信息 */
    @Excel(name = "备注信息")
    private String remarks;

    /** 教程URL */
    @Excel(name = "教程URL")
    private String tutorialUrl;

    /** 是否可分批寄出 */
    @Excel(name = "是否可分批寄出")
    private Integer partialShipment;

    /** 订单状态 */
    @Excel(name = "订单状态")
    private Integer status;

    /** 收货地址 */
    @Excel(name = "收货地址")
    private String address;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedAt;

    /** 订单有效状态 */
    @Excel(name = "订单有效状态")
    private Integer orderValidityStatus;

    /** 支付类型1.钱包 2.微信支付 */
    @Excel(name = "支付类型1.钱包 2.微信支付")
    private Integer payType;

    /** 是否需要打样 0 不需要 1需要 */
    @Excel(name = "是否需要打样 0 不需要 1需要")
    private Integer sample;

    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }
    public void setMerchantId(Long merchantId) 
    {
        this.merchantId = merchantId;
    }

    public Long getMerchantId() 
    {
        return merchantId;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setAmount(Long amount) 
    {
        this.amount = amount;
    }

    public Long getAmount() 
    {
        return amount;
    }
    public void setValidityPeriod(Integer validityPeriod) 
    {
        this.validityPeriod = validityPeriod;
    }

    public Integer getValidityPeriod() 
    {
        return validityPeriod;
    }
    public void setProductType(Integer productType) 
    {
        this.productType = productType;
    }

    public Integer getProductType() 
    {
        return productType;
    }
    public void setQuantity(Integer quantity) 
    {
        this.quantity = quantity;
    }

    public Integer getQuantity() 
    {
        return quantity;
    }
    public void setSampleTime(Date sampleTime) 
    {
        this.sampleTime = sampleTime;
    }

    public Date getSampleTime() 
    {
        return sampleTime;
    }
    public void setSecurityDeposit(Long securityDeposit) 
    {
        this.securityDeposit = securityDeposit;
    }

    public Long getSecurityDeposit() 
    {
        return securityDeposit;
    }
    public void setShippingParty(String shippingParty) 
    {
        this.shippingParty = shippingParty;
    }

    public String getShippingParty() 
    {
        return shippingParty;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }
    public void setTutorialUrl(String tutorialUrl) 
    {
        this.tutorialUrl = tutorialUrl;
    }

    public String getTutorialUrl() 
    {
        return tutorialUrl;
    }
    public void setPartialShipment(Integer partialShipment) 
    {
        this.partialShipment = partialShipment;
    }

    public Integer getPartialShipment() 
    {
        return partialShipment;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }
    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }
    public void setOrderValidityStatus(Integer orderValidityStatus) 
    {
        this.orderValidityStatus = orderValidityStatus;
    }

    public Integer getOrderValidityStatus() 
    {
        return orderValidityStatus;
    }
    public void setPayType(Integer payType) 
    {
        this.payType = payType;
    }

    public Integer getPayType() 
    {
        return payType;
    }
    public void setSample(Integer sample) 
    {
        this.sample = sample;
    }

    public Integer getSample() 
    {
        return sample;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("merchantId", getMerchantId())
            .append("title", getTitle())
            .append("amount", getAmount())
            .append("validityPeriod", getValidityPeriod())
            .append("productType", getProductType())
            .append("quantity", getQuantity())
            .append("sampleTime", getSampleTime())
            .append("securityDeposit", getSecurityDeposit())
            .append("shippingParty", getShippingParty())
            .append("remarks", getRemarks())
            .append("tutorialUrl", getTutorialUrl())
            .append("partialShipment", getPartialShipment())
            .append("status", getStatus())
            .append("address", getAddress())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .append("orderValidityStatus", getOrderValidityStatus())
            .append("payType", getPayType())
            .append("sample", getSample())
            .toString();
    }
}
