package com.baoli.order.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 拒单订单管理对象 baoli_biz_refuse_order
 * 
 * @author niujs
 * @date 2024-04-11
 */
public class BaoliBizRefuseOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 城市 */
    @Excel(name = "城市")
    private String city;

    /** 经销商 */
    @Excel(name = "经销商")
    private Long storeId;

    /** 是否新车 */
    @Excel(name = "是否新车")
    private String carType;

    /** 一级品牌 */
    @Excel(name = "一级品牌")
    private Long level1BrandId;

    /** 二级品牌 */
    @Excel(name = "二级品牌")
    private Long level2BrandId;

    /** 车系 */
    @Excel(name = "车系")
    private Long carSeriesId;

    /** 型号 */
    @Excel(name = "型号")
    private Long carModelId;

    /** 贷款类型 */
    @Excel(name = "贷款类型")
    private String loanType;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNumber;

    /** 银行 */
    @Excel(name = "银行")
    private Long bank;

    /** 客户姓名 */
    @Excel(name = "客户姓名")
    private String customerName;

    /** 申请编号 */
    @Excel(name = "申请编号")
    private String applyNumber;

    /** 贷款金额 */
    @Excel(name = "贷款金额")
    private Long loanAmount;

    /** 驻店 */
    @Excel(name = "驻店")
    private Long inStore;

    /** 拒绝原因 */
    @Excel(name = "拒绝原因")
    private String refuseReason;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    private String cityName;
    private String storeName;
    private String brandName;
    private String bankName;
    private String inStoreName;
    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCity(String city) 
    {
        this.city = city;
    }

    public String getCity() 
    {
        return city;
    }
    public void setCarType(String carType) 
    {
        this.carType = carType;
    }

    public String getCarType() 
    {
        return carType;
    }
    public void setLevel1BrandId(Long level1BrandId) 
    {
        this.level1BrandId = level1BrandId;
    }

    public Long getLevel1BrandId() 
    {
        return level1BrandId;
    }
    public void setLevel2BrandId(Long level2BrandId) 
    {
        this.level2BrandId = level2BrandId;
    }

    public Long getLevel2BrandId() 
    {
        return level2BrandId;
    }
    public void setCarSeriesId(Long carSeriesId) 
    {
        this.carSeriesId = carSeriesId;
    }

    public Long getCarSeriesId() 
    {
        return carSeriesId;
    }
    public void setCarModelId(Long carModelId) 
    {
        this.carModelId = carModelId;
    }

    public Long getCarModelId() 
    {
        return carModelId;
    }
    public void setLoanType(String loanType) 
    {
        this.loanType = loanType;
    }

    public String getLoanType() 
    {
        return loanType;
    }
    public void setOrderNumber(String orderNumber) 
    {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() 
    {
        return orderNumber;
    }
    public void setBank(Long bank) 
    {
        this.bank = bank;
    }

    public Long getBank() 
    {
        return bank;
    }
    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    public String getCustomerName() 
    {
        return customerName;
    }
    public void setApplyNumber(String applyNumber) 
    {
        this.applyNumber = applyNumber;
    }

    public String getApplyNumber() 
    {
        return applyNumber;
    }
    public void setLoanAmount(Long loanAmount) 
    {
        this.loanAmount = loanAmount;
    }

    public Long getLoanAmount() 
    {
        return loanAmount;
    }
    public void setInStore(Long inStore) 
    {
        this.inStore = inStore;
    }

    public Long getInStore() 
    {
        return inStore;
    }
    public void setRefuseReason(String refuseReason) 
    {
        this.refuseReason = refuseReason;
    }

    public String getRefuseReason() 
    {
        return refuseReason;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getInStoreName() {
        return inStoreName;
    }

    public void setInStoreName(String inStoreName) {
        this.inStoreName = inStoreName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("city", getCity())
            .append("carType", getCarType())
            .append("level1BrandId", getLevel1BrandId())
            .append("level2BrandId", getLevel2BrandId())
            .append("carSeriesId", getCarSeriesId())
            .append("carModelId", getCarModelId())
            .append("loanType", getLoanType())
            .append("orderNumber", getOrderNumber())
            .append("bank", getBank())
            .append("customerName", getCustomerName())
            .append("applyNumber", getApplyNumber())
            .append("loanAmount", getLoanAmount())
            .append("inStore", getInStore())
            .append("refuseReason", getRefuseReason())
            .append("remark", getRemark())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
