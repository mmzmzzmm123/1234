package com.ruoyi.portal.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.xss.Xss;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-18 14:20:54
 * <p>
// * Modification History:
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
    private Integer validityPeriod;

    /** 产品类型 */
    private Integer productType;

    /** 总数量 */
    private Integer quantity;

    /** 打烊时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date sampleTime;

    private Integer payType;

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

    /**
     * false:未接单 true:已接单
     */
    private Boolean orderPending;


    private List<String>imageList;

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

    public Integer getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(Integer validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getSampleTime() {
        return sampleTime;
    }

    public void setSampleTime(Date sampleTime) {
        this.sampleTime = sampleTime;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
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

    public Boolean getOrderPending() {
        return orderPending;
    }

    public void setOrderPending(Boolean orderPending) {
        this.orderPending = orderPending;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
