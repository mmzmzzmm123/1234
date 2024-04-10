package com.baoli.order.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单对象 baoli_biz_order
 * 
 * @author niujs
 * @date 2024-04-07
 */
public class BaoliBizOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 订单模式（01 先放款后抵押，02 先抵押后放款，03 无抵押，04 借指标） */
    @Excel(name = "订单模式", readConverterExp = "0=1,先=放款后抵押，02,先=抵押后放款，03,无=抵押，04,借=指标")
    private String orderSchema;

    /** 城市 */
    @Excel(name = "城市")
    private String city;

    /** 经销商类型（01 一级经销商 02 二级经销商） */
    @Excel(name = "经销商类型", readConverterExp = "0=1,一=级经销商,0=2,二=级经销商")
    private String dealerType;

    /** 经销商 */
    @Excel(name = "经销商")
    private Long dealerId;

    /** 是否新车（01 新车 02 二手车） */
    @Excel(name = "是否新车", readConverterExp = "0=1,新=车,0=2,二=手车")
    private String carType;

    /** 贷款类型（01 信用卡 02 消费贷） */
    @Excel(name = "贷款类型", readConverterExp = "0=1,信=用卡,0=2,消=费贷")
    private String loanType;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNumber;

    /** 银行 */
    @Excel(name = "银行")
    private Long bank;

    /** 内勤 */
    @Excel(name = "内勤")
    private String innerOfficer;

    /** 客户姓名 */
    @Excel(name = "客户姓名")
    private String customerName;

    /** 申请编号 */
    @Excel(name = "申请编号")
    private String applyNumber;

    /** 是否新能源（00 否 01 是） */
    @Excel(name = "是否新能源", readConverterExp = "0=0,否=,0=1,是=")
    private String newEnergy;

    /** 开票价格 */
    @Excel(name = "开票价格")
    private Long invoicePrice;

    /** 贷款金额 */
    @Excel(name = "贷款金额")
    private Long loanAmount;

    /** 首付金额 */
    @Excel(name = "首付金额")
    private Long downPayment;

    /** 贷款比率 */
    @Excel(name = "贷款比率")
    private Long loanRatio;

    /** 期数（12 十二期 24 二十四期 36 三十六期 48 四十八期 60 六十期） */
    @Excel(name = "期数", readConverterExp = "1=2,十=二期,2=4,二=十四期,3=6,三=十六期,4=8,四=十八期,6=0,六=十期")
    private String periodNumber;

    /** 进件材料类型（01 社保 02 公积金 03 个税 04 房产 05 车产 06 工作证明 07 营业执照） */
    @Excel(name = "进件材料类型", readConverterExp = "0=1,社=保,0=2,公=积金,0=3,个=税,0=4,房=产,0=5,车=产,0=6,工=作证明,0=7,营=业执照")
    private String supplyMaterialType;

    /** 客户联系方式 */
    @Excel(name = "客户联系方式")
    private String customerPhoneNumber;

    /** 客户工作单位 */
    @Excel(name = "客户工作单位")
    private String customerWorkUnion;

    /** 是否贴息 */
    @Excel(name = "是否贴息")
    private String isDiscount;

    /** 总费率 */
    @Excel(name = "总费率")
    private Long feeRatio;

    /** 是否惠享荣 */
    @Excel(name = "是否惠享荣")
    private String isHxr;

    /** 业务编号 */
    @Excel(name = "业务编号")
    private String businessNumber;

    /** 信用卡号 */
    @Excel(name = "信用卡号")
    private String creditCardNumber;

    /** 客户年龄 */
    @Excel(name = "客户年龄")
    private String customerAge;

    /** 婚否 */
    @Excel(name = "婚否")
    private String married;

    /** 有无驾照 */
    @Excel(name = "有无驾照")
    private String existsDriveLicense;

    /** 户籍类型 */
    @Excel(name = "户籍类型")
    private String domicileType;

    /** 工作所在地 */
    @Excel(name = "工作所在地")
    private String workPlace;

    /** 行业 */
    @Excel(name = "行业")
    private String workField;

    /** 工作性质 */
    @Excel(name = "工作性质")
    private String workCategory;

    /** 学历 */
    @Excel(name = "学历")
    private String education;

    /** 陪同人员关系 */
    @Excel(name = "陪同人员关系")
    private String companionRelationship;

    /** 陪同人员数量 */
    @Excel(name = "陪同人员数量")
    private Long companionNumber;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrderSchema(String orderSchema) 
    {
        this.orderSchema = orderSchema;
    }

    public String getOrderSchema() 
    {
        return orderSchema;
    }
    public void setCity(String city) 
    {
        this.city = city;
    }

    public String getCity() 
    {
        return city;
    }
    public void setDealerType(String dealerType) 
    {
        this.dealerType = dealerType;
    }

    public String getDealerType() 
    {
        return dealerType;
    }
    public void setDealerId(Long dealerId) 
    {
        this.dealerId = dealerId;
    }

    public Long getDealerId() 
    {
        return dealerId;
    }
    public void setCarType(String carType) 
    {
        this.carType = carType;
    }

    public String getCarType() 
    {
        return carType;
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
    public void setInnerOfficer(String innerOfficer) 
    {
        this.innerOfficer = innerOfficer;
    }

    public String getInnerOfficer() 
    {
        return innerOfficer;
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
    public void setNewEnergy(String newEnergy) 
    {
        this.newEnergy = newEnergy;
    }

    public String getNewEnergy() 
    {
        return newEnergy;
    }
    public void setInvoicePrice(Long invoicePrice) 
    {
        this.invoicePrice = invoicePrice;
    }

    public Long getInvoicePrice() 
    {
        return invoicePrice;
    }
    public void setLoanAmount(Long loanAmount) 
    {
        this.loanAmount = loanAmount;
    }

    public Long getLoanAmount() 
    {
        return loanAmount;
    }
    public void setDownPayment(Long downPayment) 
    {
        this.downPayment = downPayment;
    }

    public Long getDownPayment() 
    {
        return downPayment;
    }
    public void setLoanRatio(Long loanRatio) 
    {
        this.loanRatio = loanRatio;
    }

    public Long getLoanRatio() 
    {
        return loanRatio;
    }
    public void setPeriodNumber(String periodNumber) 
    {
        this.periodNumber = periodNumber;
    }

    public String getPeriodNumber() 
    {
        return periodNumber;
    }
    public void setSupplyMaterialType(String supplyMaterialType) 
    {
        this.supplyMaterialType = supplyMaterialType;
    }

    public String getSupplyMaterialType() 
    {
        return supplyMaterialType;
    }
    public void setCustomerPhoneNumber(String customerPhoneNumber) 
    {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCustomerPhoneNumber() 
    {
        return customerPhoneNumber;
    }
    public void setCustomerWorkUnion(String customerWorkUnion) 
    {
        this.customerWorkUnion = customerWorkUnion;
    }

    public String getCustomerWorkUnion() 
    {
        return customerWorkUnion;
    }
    public void setIsDiscount(String isDiscount) 
    {
        this.isDiscount = isDiscount;
    }

    public String getIsDiscount() 
    {
        return isDiscount;
    }
    public void setFeeRatio(Long feeRatio) 
    {
        this.feeRatio = feeRatio;
    }

    public Long getFeeRatio() 
    {
        return feeRatio;
    }
    public void setIsHxr(String isHxr) 
    {
        this.isHxr = isHxr;
    }

    public String getIsHxr() 
    {
        return isHxr;
    }
    public void setBusinessNumber(String businessNumber) 
    {
        this.businessNumber = businessNumber;
    }

    public String getBusinessNumber() 
    {
        return businessNumber;
    }
    public void setCreditCardNumber(String creditCardNumber) 
    {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardNumber() 
    {
        return creditCardNumber;
    }
    public void setCustomerAge(String customerAge) 
    {
        this.customerAge = customerAge;
    }

    public String getCustomerAge() 
    {
        return customerAge;
    }
    public void setMarried(String married) 
    {
        this.married = married;
    }

    public String getMarried() 
    {
        return married;
    }
    public void setExistsDriveLicense(String existsDriveLicense) 
    {
        this.existsDriveLicense = existsDriveLicense;
    }

    public String getExistsDriveLicense() 
    {
        return existsDriveLicense;
    }
    public void setDomicileType(String domicileType) 
    {
        this.domicileType = domicileType;
    }

    public String getDomicileType() 
    {
        return domicileType;
    }
    public void setWorkPlace(String workPlace) 
    {
        this.workPlace = workPlace;
    }

    public String getWorkPlace() 
    {
        return workPlace;
    }
    public void setWorkField(String workField) 
    {
        this.workField = workField;
    }

    public String getWorkField() 
    {
        return workField;
    }
    public void setWorkCateory(String workCategory)
    {
        this.workCategory = workCategory;
    }

    public String getWorkCateory()
    {
        return workCategory;
    }
    public void setEducation(String education) 
    {
        this.education = education;
    }

    public String getEducation() 
    {
        return education;
    }
    public void setCompanionRelationship(String companionRelationship) 
    {
        this.companionRelationship = companionRelationship;
    }

    public String getCompanionRelationship() 
    {
        return companionRelationship;
    }
    public void setCompanionNumber(Long companionNumber) 
    {
        this.companionNumber = companionNumber;
    }

    public Long getCompanionNumber() 
    {
        return companionNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderSchema", getOrderSchema())
            .append("city", getCity())
            .append("dealerType", getDealerType())
            .append("dealerId", getDealerId())
            .append("carType", getCarType())
            .append("loanType", getLoanType())
            .append("orderNumber", getOrderNumber())
            .append("bank", getBank())
            .append("innerOfficer", getInnerOfficer())
            .append("customerName", getCustomerName())
            .append("applyNumber", getApplyNumber())
            .append("newEnergy", getNewEnergy())
            .append("invoicePrice", getInvoicePrice())
            .append("loanAmount", getLoanAmount())
            .append("downPayment", getDownPayment())
            .append("loanRatio", getLoanRatio())
            .append("periodNumber", getPeriodNumber())
            .append("supplyMaterialType", getSupplyMaterialType())
            .append("customerPhoneNumber", getCustomerPhoneNumber())
            .append("customerWorkUnion", getCustomerWorkUnion())
            .append("isDiscount", getIsDiscount())
            .append("feeRatio", getFeeRatio())
            .append("isHxr", getIsHxr())
            .append("businessNumber", getBusinessNumber())
            .append("creditCardNumber", getCreditCardNumber())
            .append("customerAge", getCustomerAge())
            .append("married", getMarried())
            .append("existsDriveLicense", getExistsDriveLicense())
            .append("domicileType", getDomicileType())
            .append("workPlace", getWorkPlace())
            .append("workField", getWorkField())
            .append("workCategory", getWorkCateory())
            .append("education", getEducation())
            .append("companionRelationship", getCompanionRelationship())
            .append("companionNumber", getCompanionNumber())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
