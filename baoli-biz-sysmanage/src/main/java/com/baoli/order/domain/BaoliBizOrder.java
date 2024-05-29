package com.baoli.order.domain;

import com.alibaba.fastjson2.JSONObject;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

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
    @Excel(name = "订单模式")
    private String orderSchema;

    /** 城市 */
    @Excel(name = "城市")
    private String city;

    /** 经销商等级（01 一级经销商 02 二级经销商） */
    @Excel(name = "经销商等级")
    private String storeLevel;

    /** 商户id */
    @Excel(name = "商户id")
    private Long storeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderSchema() {
        return orderSchema;
    }

    public void setOrderSchema(String orderSchema) {
        this.orderSchema = orderSchema;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStoreLevel() {
        return storeLevel;
    }

    public void setStoreLevel(String storeLevel) {
        this.storeLevel = storeLevel;
    }

    public Long getStoreId() {
        return storeId;
    }

    public Date getMortgageDate() {
        return mortgageDate;
    }

    public void setMortgageDate(Date mortgageDate) {
        this.mortgageDate = mortgageDate;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Date getShareDate() {
        return shareDate;
    }

    public void setShareDate(Date shareDate) {
        this.shareDate = shareDate;
    }

    public Date getReleaseLoanDate() {
        return releaseLoanDate;
    }

    public void setReleaseLoanDate(Date releaseLoanDate) {
        this.releaseLoanDate = releaseLoanDate;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getBank() {
        return bank;
    }

    public void setBank(Long bank) {
        this.bank = bank;
    }

    public Long getInnerOfficer() {
        return innerOfficer;
    }

    public void setInnerOfficer(Long innerOfficer) {
        this.innerOfficer = innerOfficer;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getApplyNumber() {
        return applyNumber;
    }

    public void setApplyNumber(String applyNumber) {
        this.applyNumber = applyNumber;
    }

    public String getNewEnergy() {
        return newEnergy;
    }

    public void setNewEnergy(String newEnergy) {
        this.newEnergy = newEnergy;
    }

    public Long getInvoicePrice() {
        return invoicePrice;
    }

    public void setInvoicePrice(Long invoicePrice) {
        this.invoicePrice = invoicePrice;
    }

    public Long getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Long loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Long getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(Long downPayment) {
        this.downPayment = downPayment;
    }

    public Float getLoanRatio() {
        return loanRatio;
    }

    public void setLoanRatio(Float loanRatio) {
        this.loanRatio = loanRatio;
    }

    public String getPeriodNumber() {
        return periodNumber;
    }

    public void setPeriodNumber(String periodNumber) {
        this.periodNumber = periodNumber;
    }

    public String getSupplyMaterialType() {
        return supplyMaterialType;
    }

    public void setSupplyMaterialType(String supplyMaterialType) {
        this.supplyMaterialType = supplyMaterialType;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCustomerWorkUnion() {
        return customerWorkUnion;
    }

    public void setCustomerWorkUnion(String customerWorkUnion) {
        this.customerWorkUnion = customerWorkUnion;
    }

    public String getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(String isDiscount) {
        this.isDiscount = isDiscount;
    }

    public Double getFeeRatio() {
        return feeRatio;
    }

    public void setFeeRatio(Double feeRatio) {
        this.feeRatio = feeRatio;
    }

    public String getIsHxr() {
        return isHxr;
    }

    public void setIsHxr(String isHxr) {
        this.isHxr = isHxr;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCustomerAge() {
        return customerAge;
    }

    public void setCustomerAge(String customerAge) {
        this.customerAge = customerAge;
    }

    public String getMarried() {
        return married;
    }

    public void setMarried(String married) {
        this.married = married;
    }

    public String getExistsDriveLicense() {
        return existsDriveLicense;
    }

    public void setExistsDriveLicense(String existsDriveLicense) {
        this.existsDriveLicense = existsDriveLicense;
    }

    public String getDomicileType() {
        return domicileType;
    }

    public void setDomicileType(String domicileType) {
        this.domicileType = domicileType;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getWorkField() {
        return workField;
    }

    public void setWorkField(String workField) {
        this.workField = workField;
    }

    public String getWorkCategory() {
        return workCategory;
    }

    public void setWorkCategory(String workCategory) {
        this.workCategory = workCategory;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCompanionRelationship() {
        return companionRelationship;
    }

    public void setCompanionRelationship(String companionRelationship) {
        this.companionRelationship = companionRelationship;
    }

    public String getDetailInfoFiles() {
        return detailInfoFiles;
    }

    public void setDetailInfoFiles(String detailInfoFiles) {
        this.detailInfoFiles = detailInfoFiles;
    }

    public String getUploadLoanFiles() {
        return uploadLoanFiles;
    }

    public void setUploadLoanFiles(String uploadLoanFiles) {
        this.uploadLoanFiles = uploadLoanFiles;
    }

    public String getUploadArchiveFiles() {
        return uploadArchiveFiles;
    }

    public void setUploadArchiveFiles(String uploadArchiveFiles) {
        this.uploadArchiveFiles = uploadArchiveFiles;
    }

    public Long getCompanionNumber() {
        return companionNumber;
    }

    public void setCompanionNumber(Long companionNumber) {
        this.companionNumber = companionNumber;
    }

    public Long getLevel1BrandId() {
        return level1BrandId;
    }

    public void setLevel1BrandId(Long level1BrandId) {
        this.level1BrandId = level1BrandId;
    }

    public Long getLevel2BrandId() {
        return level2BrandId;
    }

    public void setLevel2BrandId(Long level2BrandId) {
        this.level2BrandId = level2BrandId;
    }

    public Long getCarSeriesId() {
        return carSeriesId;
    }

    public void setCarSeriesId(Long carSeriesId) {
        this.carSeriesId = carSeriesId;
    }

    public Long getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(Long carModelId) {
        this.carModelId = carModelId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getInnerOfficerName() {
        return innerOfficerName;
    }

    public void setInnerOfficerName(String innerOfficerName) {
        this.innerOfficerName = innerOfficerName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCarSeriesName() {
        return carSeriesName;
    }

    public void setCarSeriesName(String carSeriesName) {
        this.carSeriesName = carSeriesName;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public Long getRefuseOrderId() {
        return refuseOrderId;
    }

    public void setRefuseOrderId(Long refuseOrderId) {
        this.refuseOrderId = refuseOrderId;
    }

    public String getOrderYear() {
        return orderYear;
    }

    public void setOrderYear(String orderYear) {
        this.orderYear = orderYear;
    }

    public Float getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(Float guidePrice) {
        this.guidePrice = guidePrice;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /** 是否新车（01 新车 02 二手车） */
    @Excel(name = "是否新车")
    private String carType;

    /** 贷款类型（01 信用卡 02 消费贷） */
    @Excel(name = "贷款类型")
    private String loanType;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNumber;

    /** 银行 */
    @Excel(name = "银行")
    private Long bank;

    /** 内勤 */
    @Excel(name = "内勤")
    private Long innerOfficer;

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
    private Float loanRatio;

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

    private Long level1BrandId;
    private Long level2BrandId;
    private Long carSeriesId;
    private Long carModelId;
    private Float guidePrice;
    private String status;
    // 申请人
    private Long applicantId;
    // 拒单Id
    private Long refuseOrderId;
    //出单年份
    private String orderYear;
    private String applicantName;
    private String cityName;
    private String storeName;
    private String bankName;
    private String innerOfficerName;
    private String brandName;
    private String carSeriesName;
    private String carModelName;
    private int orderCount;
    private String provinceId;
    private String refuseReason;
    private String label;
    private String detailInfoFiles;
    private String uploadLoanFiles;
    private String uploadArchiveFiles;
    private Date mortgageDate;
    private Date submitDate;
    private Date shareDate;
    private Date licenseDate;
    private Date releaseLoanDate;
    private JSONObject auditInfo;
    private String preCheckResult;
    private String workPlaceLocation;
    private String domicilePlaceLocation;
    private String gender;
    private String propertyType;
    private String offerTransRecords;
    private String psbcBankVip;
    private String licenseLocationType;
    private String licenseLocation;
    private Date startTime;
    private Date endTime;
    private Long[] ids;
    private Long adjustLoanAmount;
    private String auditResult;
    private String auditComment;

    private String publicAccount;
    private String accountName;
    private Double cardPersonRate;
    private Double storeFeeRate;
    private Double rebateRate;
    private Double fee1;
    private Double fee2;
    private Double fee3;
    private Double fee4;
    private Double storeFee;

    public Double getFee1() {
        return fee1;
    }

    public void setFee1(Double fee1) {
        this.fee1 = fee1;
    }

    public Double getFee2() {
        return fee2;
    }

    public void setFee2(Double fee2) {
        this.fee2 = fee2;
    }

    public Double getFee3() {
        return fee3;
    }

    public void setFee3(Double fee3) {
        this.fee3 = fee3;
    }

    public Double getFee4() {
        return fee4;
    }

    public void setFee4(Double fee4) {
        this.fee4 = fee4;
    }

    public Double getStoreFee() {
        return storeFee;
    }

    public void setStoreFee(Double storeFee) {
        this.storeFee = storeFee;
    }

    public Double getRebateRate() {
        return rebateRate;
    }

    public void setRebateRate(Double rebateRate) {
        this.rebateRate = rebateRate;
    }

    public String getPublicAccount() {
        return publicAccount;
    }

    public void setPublicAccount(String publicAccount) {
        this.publicAccount = publicAccount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Double getCardPersonRate() {
        return cardPersonRate;
    }

    public void setCardPersonRate(Double cardPersonRate) {
        this.cardPersonRate = cardPersonRate;
    }

    public Double getStoreFeeRate() {
        return storeFeeRate;
    }

    public void setStoreFeeRate(Double storeFeeRate) {
        this.storeFeeRate = storeFeeRate;
    }

    /** 总费率 */
    @Excel(name = "总费率")
    private Double feeRatio;

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public String getAuditComment() {
        return auditComment;
    }

    public void setAuditComment(String auditComment) {
        this.auditComment = auditComment;
    }

    public Long getAdjustLoanAmount() {
        return adjustLoanAmount;
    }

    public void setAdjustLoanAmount(Long adjustLoanAmount) {
        this.adjustLoanAmount = adjustLoanAmount;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public JSONObject getAuditInfo() {
        return auditInfo;
    }

    public Date getLicenseDate() {
        return licenseDate;
    }

    public void setLicenseDate(Date licenseDate) {
        this.licenseDate = licenseDate;
    }

    public void setAuditInfo(JSONObject auditInfo) {
        this.auditInfo = auditInfo;
    }

    public String getPreCheckResult() {
        return preCheckResult;
    }

    public void setPreCheckResult(String preCheckResult) {
        this.preCheckResult = preCheckResult;
    }

    public String getWorkPlaceLocation() {
        return workPlaceLocation;
    }

    public void setWorkPlaceLocation(String workPlaceLocation) {
        this.workPlaceLocation = workPlaceLocation;
    }

    public String getDomicilePlaceLocation() {
        return domicilePlaceLocation;
    }

    public void setDomicilePlaceLocation(String domicilePlaceLocation) {
        this.domicilePlaceLocation = domicilePlaceLocation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getOfferTransRecords() {
        return offerTransRecords;
    }

    public void setOfferTransRecords(String offerTransRecords) {
        this.offerTransRecords = offerTransRecords;
    }

    public String getPsbcBankVip() {
        return psbcBankVip;
    }

    public void setPsbcBankVip(String psbcBankVip) {
        this.psbcBankVip = psbcBankVip;
    }

    public String getLicenseLocationType() {
        return licenseLocationType;
    }

    public void setLicenseLocationType(String licenseLocationType) {
        this.licenseLocationType = licenseLocationType;
    }

    public String getLicenseLocation() {
        return licenseLocation;
    }

    public void setLicenseLocation(String licenseLocation) {
        this.licenseLocation = licenseLocation;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
