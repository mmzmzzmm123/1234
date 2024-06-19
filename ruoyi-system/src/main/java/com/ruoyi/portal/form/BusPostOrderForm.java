package com.ruoyi.portal.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.xss.Xss;

import java.util.Date;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-18 14:20:54
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-18     张李鑫                     1.0         1.0 Version
 */
public class BusPostOrderForm extends BaseEntity {

    /** 订单ID */
    private Long orderId;
    /** 订单标题 */
    @Xss(message = "标题标题不能包含脚本字符")
    private String title;

    /** 订单金额 */
    private Long amount;

    /** 订单时效（小时） */
    private Long validityPeriod;

    /** 产品类型 */
    private Long productType;

    /** 总数量 */
    private Long quantity;

    /** 打烊时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date closingTime;

    /** 时效保证金 */
    private Long securityDeposit;

    /** 运费承担方 */
    @Xss(message = "运费承担方不能包含脚本字符")
    private String shippingParty;

    /** 备注信息 */
    @Xss(message = "备注信息不能包含脚本字符")
    private String remarks;

    /** 教程URL */
    private String tutorialUrl;

    /** 是否可分批寄出 */
    private Integer partialShipment;

    /** 收货地址 */
    private String address;

    private Boolean orderPending;


    public Boolean getOrderPending() {
        return orderPending;
    }

    public void setOrderPending(Boolean orderPending) {
        this.orderPending = orderPending;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(Long validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

    public Long getSecurityDeposit() {
        return securityDeposit;
    }

    public void setSecurityDeposit(Long securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public String getShippingParty() {
        return shippingParty;
    }

    public void setShippingParty(String shippingParty) {
        this.shippingParty = shippingParty;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTutorialUrl() {
        return tutorialUrl;
    }

    public void setTutorialUrl(String tutorialUrl) {
        this.tutorialUrl = tutorialUrl;
    }

    public Integer getPartialShipment() {
        return partialShipment;
    }

    public void setPartialShipment(Integer partialShipment) {
        this.partialShipment = partialShipment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "BusPostOrderForm{" +
                "orderId=" + orderId +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", validityPeriod=" + validityPeriod +
                ", productType=" + productType +
                ", quantity=" + quantity +
                ", closingTime=" + closingTime +
                ", securityDeposit=" + securityDeposit +
                ", shippingParty='" + shippingParty + '\'' +
                ", remarks='" + remarks + '\'' +
                ", tutorialUrl='" + tutorialUrl + '\'' +
                ", partialShipment=" + partialShipment +
                ", address='" + address + '\'' +
                '}';
    }
}
