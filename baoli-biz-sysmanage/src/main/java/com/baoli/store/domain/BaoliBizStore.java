package com.baoli.store.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商户管理对象 baoli_biz_store
 * 
 * @author niujs
 * @date 2024-04-08
 */
public class BaoliBizStore extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 商户名称 */
    @Excel(name = "商户名称")
    private String name;

    /** 签约品牌 */
    @Excel(name = "签约品牌")
    private Long brandId;

    /** 所属集团 */
    @Excel(name = "所属集团")
    private String group;

    /** 省份 */
    @Excel(name = "省份")
    private String provinceId;

    /** 城市 */
    @Excel(name = "城市")
    private String cityId;

    /** 经销商类型（01 一级经销商 02 二级经销商） */
    @Excel(name = "经销商类型", readConverterExp = "0=1,一=级经销商,0=2,二=级经销商")
    private String dealerType;

    /** 主销车型 */
    @Excel(name = "主销车型")
    private String mostSellModel;

    /** 员工数量 */
    @Excel(name = "员工数量")
    private Long staffAmount;

    /** 店总 */
    @Excel(name = "店总")
    private String gmName;

    /** 店总联系方式 */
    @Excel(name = "店总联系方式")
    private String gmPhoneNumber;

    /** 销售经理 */
    @Excel(name = "销售经理")
    private String smName;

    /** 销售经理联系方式 */
    @Excel(name = "销售经理联系方式")
    private String smPhoneNumber;

    /** 金融专员 */
    @Excel(name = "金融专员")
    private String foName;

    /** 金融专员联系方式 */
    @Excel(name = "金融专员联系方式")
    private String foPhoneNumber;

    /** 月销量 */
    @Excel(name = "月销量")
    private Long monthSellsAmout;

    /** 整体渗透率 */
    @Excel(name = "整体渗透率")
    private String permeationRate;

    /** 厂家渗透率 */
    @Excel(name = "厂家渗透率")
    private String brandPr;

    /** 银行渗透率 */
    @Excel(name = "银行渗透率")
    private String bankPr;

    /** 库融资方 */
    @Excel(name = "库融资方")
    private String sfInvestor;

    /** 库融占比 */
    @Excel(name = "库融占比")
    private String sfRate;

    /** 贴息车型 */
    @Excel(name = "贴息车型")
    private String discountCars;

    /** 贴息销售占比 */
    @Excel(name = "贴息销售占比")
    private String discountSellsRate;

    /** 贴息银行 */
    @Excel(name = "贴息银行")
    private String discountBank;

    /** 非贴息车型 */
    @Excel(name = "非贴息车型")
    private String nonDiscountCars;

    /** 非贴息车型销售比率 */
    @Excel(name = "非贴息车型销售比率")
    private String nonDiscountSellsRate;

    /** 不良率 */
    @Excel(name = "不良率")
    private String defectRate;

    /** 厂家不良率 */
    @Excel(name = "厂家不良率")
    private String brandDefectRate;

    /** 商业银行不良率 */
    @Excel(name = "商业银行不良率")
    private String bankDefectRate;

    /** 关键人 */
    @Excel(name = "关键人")
    private String keyPerson;

    /** 关键人职务 */
    @Excel(name = "关键人职务")
    private String kpPosition;

    /** 关键人电话 */
    @Excel(name = "关键人电话")
    private String kpPhoneNumber;

    /** 销售策略 */
    @Excel(name = "销售策略")
    private String sellsPolicy;

    /** 被回访人 */
    @Excel(name = "被回访人")
    private String beReviewPerson;

    /** 被回访人职务 */
    @Excel(name = "被回访人职务")
    private String brpPosition;

    /** 被回访人联系方式 */
    @Excel(name = "被回访人联系方式")
    private String brpPhoneNumber;

    /** 被回访驻店 */
    @Excel(name = "被回访驻店")
    private String brpInStore;

    private String cityName;
    private String brandName;
    private String signBanks;
    private String status;
    private String exceptionDesc;
    private String feeSchema;
    private String feeRateSchema;
    private String exceptionReason;
    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setBrandId(Long brandId) 
    {
        this.brandId = brandId;
    }

    public Long getBrandId() 
    {
        return brandId;
    }
    public void setGroup(String group) 
    {
        this.group = group;
    }

    public String getGroup() 
    {
        return group;
    }
    public void setProvinceId(String provinceId) 
    {
        this.provinceId = provinceId;
    }

    public String getProvinceId() 
    {
        return provinceId;
    }
    public void setCityId(String cityId) 
    {
        this.cityId = cityId;
    }

    public String getCityId() 
    {
        return cityId;
    }
    public void setDealerType(String dealerType) 
    {
        this.dealerType = dealerType;
    }

    public String getDealerType() 
    {
        return dealerType;
    }
    public void setMostSellModel(String mostSellModel) 
    {
        this.mostSellModel = mostSellModel;
    }

    public String getMostSellModel() 
    {
        return mostSellModel;
    }
    public void setStaffAmount(Long staffAmount) 
    {
        this.staffAmount = staffAmount;
    }

    public Long getStaffAmount() 
    {
        return staffAmount;
    }
    public void setGmName(String gmName) 
    {
        this.gmName = gmName;
    }

    public String getGmName() 
    {
        return gmName;
    }
    public void setGmPhoneNumber(String gmPhoneNumber) 
    {
        this.gmPhoneNumber = gmPhoneNumber;
    }

    public String getGmPhoneNumber() 
    {
        return gmPhoneNumber;
    }
    public void setSmName(String smName) 
    {
        this.smName = smName;
    }

    public String getSmName() 
    {
        return smName;
    }
    public void setSmPhoneNumber(String smPhoneNumber) 
    {
        this.smPhoneNumber = smPhoneNumber;
    }

    public String getSmPhoneNumber() 
    {
        return smPhoneNumber;
    }
    public void setFoName(String foName) 
    {
        this.foName = foName;
    }

    public String getFoName() 
    {
        return foName;
    }
    public void setFoPhoneNumber(String foPhoneNumber) 
    {
        this.foPhoneNumber = foPhoneNumber;
    }

    public String getFoPhoneNumber() 
    {
        return foPhoneNumber;
    }
    public void setMonthSellsAmout(Long monthSellsAmout) 
    {
        this.monthSellsAmout = monthSellsAmout;
    }

    public Long getMonthSellsAmout() 
    {
        return monthSellsAmout;
    }
    public void setPermeationRate(String permeationRate) 
    {
        this.permeationRate = permeationRate;
    }

    public String getPermeationRate() 
    {
        return permeationRate;
    }
    public void setBrandPr(String brandPr) 
    {
        this.brandPr = brandPr;
    }

    public String getBrandPr() 
    {
        return brandPr;
    }
    public void setBankPr(String bankPr) 
    {
        this.bankPr = bankPr;
    }

    public String getBankPr() 
    {
        return bankPr;
    }
    public void setSfInvestor(String sfInvestor) 
    {
        this.sfInvestor = sfInvestor;
    }

    public String getSfInvestor() 
    {
        return sfInvestor;
    }
    public void setSfRate(String sfRate) 
    {
        this.sfRate = sfRate;
    }

    public String getSfRate() 
    {
        return sfRate;
    }
    public void setDiscountCars(String discountCars) 
    {
        this.discountCars = discountCars;
    }

    public String getDiscountCars() 
    {
        return discountCars;
    }
    public void setDiscountSellsRate(String discountSellsRate) 
    {
        this.discountSellsRate = discountSellsRate;
    }

    public String getDiscountSellsRate() 
    {
        return discountSellsRate;
    }
    public void setDiscountBank(String discountBank) 
    {
        this.discountBank = discountBank;
    }

    public String getDiscountBank() 
    {
        return discountBank;
    }
    public void setNonDiscountCars(String nonDiscountCars) 
    {
        this.nonDiscountCars = nonDiscountCars;
    }

    public String getNonDiscountCars() 
    {
        return nonDiscountCars;
    }
    public void setNonDiscountSellsRate(String nonDiscountSellsRate) 
    {
        this.nonDiscountSellsRate = nonDiscountSellsRate;
    }

    public String getNonDiscountSellsRate() 
    {
        return nonDiscountSellsRate;
    }
    public void setDefectRate(String defectRate) 
    {
        this.defectRate = defectRate;
    }

    public String getDefectRate() 
    {
        return defectRate;
    }
    public void setBrandDefectRate(String brandDefectRate) 
    {
        this.brandDefectRate = brandDefectRate;
    }

    public String getBrandDefectRate() 
    {
        return brandDefectRate;
    }
    public void setBankDefectRate(String bankDefectRate) 
    {
        this.bankDefectRate = bankDefectRate;
    }

    public String getBankDefectRate() 
    {
        return bankDefectRate;
    }
    public void setKeyPerson(String keyPerson) 
    {
        this.keyPerson = keyPerson;
    }

    public String getKeyPerson() 
    {
        return keyPerson;
    }
    public void setKpPosition(String kpPosition) 
    {
        this.kpPosition = kpPosition;
    }

    public String getKpPosition() 
    {
        return kpPosition;
    }
    public void setKpPhoneNumber(String kpPhoneNumber) 
    {
        this.kpPhoneNumber = kpPhoneNumber;
    }

    public String getKpPhoneNumber() 
    {
        return kpPhoneNumber;
    }
    public void setSellsPolicy(String sellsPolicy) 
    {
        this.sellsPolicy = sellsPolicy;
    }

    public String getSellsPolicy() 
    {
        return sellsPolicy;
    }
    public void setBeReviewPerson(String beReviewPerson) 
    {
        this.beReviewPerson = beReviewPerson;
    }

    public String getBeReviewPerson() 
    {
        return beReviewPerson;
    }
    public void setBrpPosition(String brpPosition) 
    {
        this.brpPosition = brpPosition;
    }

    public String getBrpPosition() 
    {
        return brpPosition;
    }
    public void setBrpPhoneNumber(String brpPhoneNumber) 
    {
        this.brpPhoneNumber = brpPhoneNumber;
    }

    public String getBrpPhoneNumber() 
    {
        return brpPhoneNumber;
    }
    public void setBrpInStore(String brpInStore) 
    {
        this.brpInStore = brpInStore;
    }

    public String getBrpInStore() 
    {
        return brpInStore;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSignBanks() {
        return signBanks;
    }

    public void setSignBanks(String signBanks) {
        this.signBanks = signBanks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExceptionDesc() {
        return exceptionDesc;
    }

    public void setExceptionDesc(String exceptionDesc) {
        this.exceptionDesc = exceptionDesc;
    }

    public String getFeeSchema() {
        return feeSchema;
    }

    public void setFeeSchema(String feeSchema) {
        this.feeSchema = feeSchema;
    }

    public String getFeeRateSchema() {
        return feeRateSchema;
    }

    public void setFeeRateSchema(String feeRateSchema) {
        this.feeRateSchema = feeRateSchema;
    }

    public String getExceptionReason() {
        return exceptionReason;
    }

    public void setExceptionReason(String exceptionReason) {
        this.exceptionReason = exceptionReason;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("brandId", getBrandId())
            .append("group", getGroup())
            .append("provinceId", getProvinceId())
            .append("cityId", getCityId())
            .append("dealerType", getDealerType())
            .append("mostSellModel", getMostSellModel())
            .append("staffAmount", getStaffAmount())
            .append("gmName", getGmName())
            .append("gmPhoneNumber", getGmPhoneNumber())
            .append("smName", getSmName())
            .append("smPhoneNumber", getSmPhoneNumber())
            .append("foName", getFoName())
            .append("foPhoneNumber", getFoPhoneNumber())
            .append("monthSellsAmout", getMonthSellsAmout())
            .append("permeationRate", getPermeationRate())
            .append("brandPr", getBrandPr())
            .append("bankPr", getBankPr())
            .append("sfInvestor", getSfInvestor())
            .append("sfRate", getSfRate())
            .append("discountCars", getDiscountCars())
            .append("discountSellsRate", getDiscountSellsRate())
            .append("discountBank", getDiscountBank())
            .append("nonDiscountCars", getNonDiscountCars())
            .append("nonDiscountSellsRate", getNonDiscountSellsRate())
            .append("defectRate", getDefectRate())
            .append("brandDefectRate", getBrandDefectRate())
            .append("bankDefectRate", getBankDefectRate())
            .append("keyPerson", getKeyPerson())
            .append("kpPosition", getKpPosition())
            .append("kpPhoneNumber", getKpPhoneNumber())
            .append("remark", getRemark())
            .append("sellsPolicy", getSellsPolicy())
            .append("beReviewPerson", getBeReviewPerson())
            .append("brpPosition", getBrpPosition())
            .append("brpPhoneNumber", getBrpPhoneNumber())
            .append("brpInStore", getBrpInStore())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
