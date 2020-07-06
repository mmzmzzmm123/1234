package com.uvaluation.project.data.price.domain;

import com.uvaluation.framework.aspectj.lang.annotation.Excel;
import com.uvaluation.framework.web.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 【请填写功能名称】对象 office_base_price_ultimate
 *
 * @author ruoyi
 * @date 2020-05-20
 */
public class ComputeResidenceRentBasePrice extends BaseEntity {

    /**
     * 年月
     */
    private Integer yearMonth;
    private Integer pageIndex;
    private Integer pageSize;

    @Excel(name = "id")
    private String id;
    @Excel(name = "小区ID")
    private String communityId;
    @Excel(name = "小区名称")
    private String communityName;
    @Excel(name = "小区地址")
    private String communityAddress;
    @Excel(name = "区域")
    private String county;
    @Excel(name = "板块")
    private String block;
    @Excel(name = "环线")
    private String loop;
    @Excel(name = "是否生成索引")
    private Integer isIndxGen;
    @Excel(name = "参与涨幅计算")
    private Integer isPstCalc;
    @Excel(name = "运行状态")
    private Integer statusRun;
    @Excel(name = "物业类型")
    private String propertyType;
    @Excel(name = "小区类型")
    private String projectType;
    @Excel(name = "小区类型细分")
    private String projectTypeDtl;
    @Excel(name = "物业档次")
    private String projectLevel;
    @Excel(name = "物业开发期数")
    private Integer propertyDevPeriod;
    @Excel(name = "绑定聚类ID")
    private String bindClassID;
    @Excel(name = "租金主力面积系数")
    private BigDecimal mainCoefficientRent;
    @Excel(name = "AI租金(草稿)")
    private BigDecimal rentPriceDft;
    @Excel(name = "主力面积租金(草稿)")
    private BigDecimal mainRentPriceDft;

    @Excel(name = "上月AI租金")
    private BigDecimal rentPrice_1;
    @Excel(name = "成交均价(上周期)")
    private BigDecimal priceDealMean_1;
    @Excel(name = "成交最大价(上周期)")
    private BigDecimal priceDealMax_1;
    @Excel(name = "成交数量(上周期)")
    private Integer sumDeal_1;
    @Excel(name = "成交均价相对AI租金涨跌幅(上周期)")
    private BigDecimal priceDeal_1_ToAI_Pst;

    @Excel(name = "成交均价")
    private BigDecimal priceDealMean;
    @Excel(name = "成交最大价")
    private BigDecimal priceDealMax;
    @Excel(name = "成交数量")
    private Integer sumDeal;
    @Excel(name = "成交均价相对AI租金涨跌幅")
    private BigDecimal priceDeal_ToAI_Pst;
    @Excel(name = "当月比上月成交案例价调整比例")
    private BigDecimal priceDeal_ToLst_Pst;

    @Excel(name = "挂牌下架案例均价")
    private BigDecimal priceCaseOff;
    @Excel(name = "当月比上月挂牌下架案例价调整比例")
    private BigDecimal priceCaseOff_ToLst_Pst;
    @Excel(name = "挂牌最低价")
    private BigDecimal priceListedMin;
    @Excel(name = "挂牌最低价相对AI租金涨跌幅")
    private BigDecimal priceLstMn_ToAI_Pst;

    @Excel(name = "与上月AI租金比链家1案例价格价调整比例")
    private BigDecimal priceCase1_ToAI_Pst;
    @Excel(name = "与上月AI租金比链家2案例价格价调整比例")
    private BigDecimal priceCase2_ToAI_Pst;
    @Excel(name = "当月比上月链家1案例价调整比例")
    private BigDecimal priceCase1_ToLst_Pst;
    @Excel(name = "当月比上月链家2案例价调整比例")
    private BigDecimal priceCase2_ToLst_Pst;

    @Excel(name = "链家1案例价")
    private BigDecimal priceCase1;
    @Excel(name = "链家1调价幅度")
    private BigDecimal priceCase1AdjPst;
    @Excel(name = "链家1案例总量")
    private Integer sumCase1;

    @Excel(name = "链家2案例价")
    private BigDecimal priceCase2;
    @Excel(name = "链家2调价幅度")
    private BigDecimal priceCase2AdjPst;
    @Excel(name = "链家2案例总量")
    private Integer sumCase2;

    @Excel(name = "价格涨跌幅类型-调整前")
    private BigDecimal voppbt;
    @Excel(name = "价格涨跌幅-调整前")
    private String voppb;
    @Excel(name = "绑定小区编号")
    private String bindProjID;
    @Excel(name = "绑定小区涨跌幅")
    private BigDecimal bind_Proj_Pst;
    @Excel(name = "绑定板块+聚类ID")
    private String bind_Block_Class;
    @Excel(name = "绑定板块+聚类ID的涨跌幅")
    private BigDecimal bind_Block_Class_Pst;
    @Excel(name = "绑定板块+物业档次")
    private String bind_Block_Plevel;
    @Excel(name = "绑定板块+物业档次的涨跌幅")
    private BigDecimal bind_Block_Plevel_Pst;
    @Excel(name = "绑定板块+小区类型")
    private String bind_Block_PType;
    @Excel(name = "绑定板块+小区类型的涨跌幅")
    private BigDecimal bind_Block_Ptype_Pst;
    @Excel(name = "绑定区县+小区类型")
    private String bind_County_PType;
    @Excel(name = "绑定区县+小区类型的涨跌幅")
    private BigDecimal bind_County_Ptype_Pst;
    @Excel(name = "绑定混合小区ID")
    private String bind_MixProject_PType;
    @Excel(name = "绑定混合小区涨跌幅")
    private BigDecimal bind_MixProject_Pst;
    @Excel(name = "价格涨跌幅类型-调整后")
    private String voppat;
    @Excel(name = "价格涨跌幅-调整后")
    private BigDecimal voppa;

    public Integer getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Integer yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityAddress() {
        return communityAddress;
    }

    public void setCommunityAddress(String communityAddress) {
        this.communityAddress = communityAddress;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getLoop() {
        return loop;
    }

    public void setLoop(String loop) {
        this.loop = loop;
    }

    public Integer getIsIndxGen() {
        return isIndxGen;
    }

    public void setIsIndxGen(Integer isIndxGen) {
        this.isIndxGen = isIndxGen;
    }

    public Integer getIsPstCalc() {
        return isPstCalc;
    }

    public void setIsPstCalc(Integer isPstCalc) {
        this.isPstCalc = isPstCalc;
    }

    public Integer getStatusRun() {
        return statusRun;
    }

    public void setStatusRun(Integer statusRun) {
        this.statusRun = statusRun;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectTypeDtl() {
        return projectTypeDtl;
    }

    public void setProjectTypeDtl(String projectTypeDtl) {
        this.projectTypeDtl = projectTypeDtl;
    }

    public String getProjectLevel() {
        return projectLevel;
    }

    public void setProjectLevel(String projectLevel) {
        this.projectLevel = projectLevel;
    }

    public Integer getPropertyDevPeriod() {
        return propertyDevPeriod;
    }

    public void setPropertyDevPeriod(Integer propertyDevPeriod) {
        this.propertyDevPeriod = propertyDevPeriod;
    }

    public String getBindClassID() {
        return bindClassID;
    }

    public void setBindClassID(String bindClassID) {
        this.bindClassID = bindClassID;
    }

    public BigDecimal getMainCoefficientRent() {
        return mainCoefficientRent;
    }

    public void setMainCoefficientRent(BigDecimal mainCoefficientRent) {
        this.mainCoefficientRent = mainCoefficientRent;
    }

    public BigDecimal getRentPriceDft() {
        return rentPriceDft;
    }

    public void setRentPriceDft(BigDecimal rentPriceDft) {
        this.rentPriceDft = rentPriceDft;
    }

    public BigDecimal getMainRentPriceDft() {
        return mainRentPriceDft;
    }

    public void setMainRentPriceDft(BigDecimal mainRentPriceDft) {
        this.mainRentPriceDft = mainRentPriceDft;
    }

    public BigDecimal getRentPrice_1() {
        return rentPrice_1;
    }

    public void setRentPrice_1(BigDecimal rentPrice_1) {
        this.rentPrice_1 = rentPrice_1;
    }

    public BigDecimal getPriceDealMean_1() {
        return priceDealMean_1;
    }

    public void setPriceDealMean_1(BigDecimal priceDealMean_1) {
        this.priceDealMean_1 = priceDealMean_1;
    }

    public BigDecimal getPriceDealMax_1() {
        return priceDealMax_1;
    }

    public void setPriceDealMax_1(BigDecimal priceDealMax_1) {
        this.priceDealMax_1 = priceDealMax_1;
    }

    public Integer getSumDeal_1() {
        return sumDeal_1;
    }

    public void setSumDeal_1(Integer sumDeal_1) {
        this.sumDeal_1 = sumDeal_1;
    }

    public BigDecimal getPriceDeal_1_ToAI_Pst() {
        return priceDeal_1_ToAI_Pst;
    }

    public void setPriceDeal_1_ToAI_Pst(BigDecimal priceDeal_1_ToAI_Pst) {
        this.priceDeal_1_ToAI_Pst = priceDeal_1_ToAI_Pst;
    }

    public BigDecimal getPriceDealMean() {
        return priceDealMean;
    }

    public void setPriceDealMean(BigDecimal priceDealMean) {
        this.priceDealMean = priceDealMean;
    }

    public BigDecimal getPriceDealMax() {
        return priceDealMax;
    }

    public void setPriceDealMax(BigDecimal priceDealMax) {
        this.priceDealMax = priceDealMax;
    }

    public Integer getSumDeal() {
        return sumDeal;
    }

    public void setSumDeal(Integer sumDeal) {
        this.sumDeal = sumDeal;
    }

    public BigDecimal getPriceDeal_ToAI_Pst() {
        return priceDeal_ToAI_Pst;
    }

    public void setPriceDeal_ToAI_Pst(BigDecimal priceDeal_ToAI_Pst) {
        this.priceDeal_ToAI_Pst = priceDeal_ToAI_Pst;
    }

    public BigDecimal getPriceDeal_ToLst_Pst() {
        return priceDeal_ToLst_Pst;
    }

    public void setPriceDeal_ToLst_Pst(BigDecimal priceDeal_ToLst_Pst) {
        this.priceDeal_ToLst_Pst = priceDeal_ToLst_Pst;
    }

    public BigDecimal getPriceCaseOff() {
        return priceCaseOff;
    }

    public void setPriceCaseOff(BigDecimal priceCaseOff) {
        this.priceCaseOff = priceCaseOff;
    }

    public BigDecimal getPriceCaseOff_ToLst_Pst() {
        return priceCaseOff_ToLst_Pst;
    }

    public void setPriceCaseOff_ToLst_Pst(BigDecimal priceCaseOff_ToLst_Pst) {
        this.priceCaseOff_ToLst_Pst = priceCaseOff_ToLst_Pst;
    }

    public BigDecimal getPriceListedMin() {
        return priceListedMin;
    }

    public void setPriceListedMin(BigDecimal priceListedMin) {
        this.priceListedMin = priceListedMin;
    }

    public BigDecimal getPriceLstMn_ToAI_Pst() {
        return priceLstMn_ToAI_Pst;
    }

    public void setPriceLstMn_ToAI_Pst(BigDecimal priceLstMn_ToAI_Pst) {
        this.priceLstMn_ToAI_Pst = priceLstMn_ToAI_Pst;
    }

    public BigDecimal getPriceCase1_ToAI_Pst() {
        return priceCase1_ToAI_Pst;
    }

    public void setPriceCase1_ToAI_Pst(BigDecimal priceCase1_ToAI_Pst) {
        this.priceCase1_ToAI_Pst = priceCase1_ToAI_Pst;
    }

    public BigDecimal getPriceCase2_ToAI_Pst() {
        return priceCase2_ToAI_Pst;
    }

    public void setPriceCase2_ToAI_Pst(BigDecimal priceCase2_ToAI_Pst) {
        this.priceCase2_ToAI_Pst = priceCase2_ToAI_Pst;
    }

    public BigDecimal getPriceCase1_ToLst_Pst() {
        return priceCase1_ToLst_Pst;
    }

    public void setPriceCase1_ToLst_Pst(BigDecimal priceCase1_ToLst_Pst) {
        this.priceCase1_ToLst_Pst = priceCase1_ToLst_Pst;
    }

    public BigDecimal getPriceCase2_ToLst_Pst() {
        return priceCase2_ToLst_Pst;
    }

    public void setPriceCase2_ToLst_Pst(BigDecimal priceCase2_ToLst_Pst) {
        this.priceCase2_ToLst_Pst = priceCase2_ToLst_Pst;
    }

    public BigDecimal getPriceCase1() {
        return priceCase1;
    }

    public void setPriceCase1(BigDecimal priceCase1) {
        this.priceCase1 = priceCase1;
    }

    public BigDecimal getPriceCase1AdjPst() {
        return priceCase1AdjPst;
    }

    public void setPriceCase1AdjPst(BigDecimal priceCase1AdjPst) {
        this.priceCase1AdjPst = priceCase1AdjPst;
    }

    public Integer getSumCase1() {
        return sumCase1;
    }

    public void setSumCase1(Integer sumCase1) {
        this.sumCase1 = sumCase1;
    }

    public BigDecimal getPriceCase2() {
        return priceCase2;
    }

    public void setPriceCase2(BigDecimal priceCase2) {
        this.priceCase2 = priceCase2;
    }

    public BigDecimal getPriceCase2AdjPst() {
        return priceCase2AdjPst;
    }

    public void setPriceCase2AdjPst(BigDecimal priceCase2AdjPst) {
        this.priceCase2AdjPst = priceCase2AdjPst;
    }

    public Integer getSumCase2() {
        return sumCase2;
    }

    public void setSumCase2(Integer sumCase2) {
        this.sumCase2 = sumCase2;
    }

    public BigDecimal getVoppbt() {
        return voppbt;
    }

    public void setVoppbt(BigDecimal voppbt) {
        this.voppbt = voppbt;
    }

    public String getVoppb() {
        return voppb;
    }

    public void setVoppb(String voppb) {
        this.voppb = voppb;
    }

    public String getBindProjID() {
        return bindProjID;
    }

    public void setBindProjID(String bindProjID) {
        this.bindProjID = bindProjID;
    }

    public BigDecimal getBind_Proj_Pst() {
        return bind_Proj_Pst;
    }

    public void setBind_Proj_Pst(BigDecimal bind_Proj_Pst) {
        this.bind_Proj_Pst = bind_Proj_Pst;
    }

    public String getBind_Block_Class() {
        return bind_Block_Class;
    }

    public void setBind_Block_Class(String bind_Block_Class) {
        this.bind_Block_Class = bind_Block_Class;
    }

    public BigDecimal getBind_Block_Class_Pst() {
        return bind_Block_Class_Pst;
    }

    public void setBind_Block_Class_Pst(BigDecimal bind_Block_Class_Pst) {
        this.bind_Block_Class_Pst = bind_Block_Class_Pst;
    }

    public String getBind_Block_Plevel() {
        return bind_Block_Plevel;
    }

    public void setBind_Block_Plevel(String bind_Block_Plevel) {
        this.bind_Block_Plevel = bind_Block_Plevel;
    }

    public BigDecimal getBind_Block_Plevel_Pst() {
        return bind_Block_Plevel_Pst;
    }

    public void setBind_Block_Plevel_Pst(BigDecimal bind_Block_Plevel_Pst) {
        this.bind_Block_Plevel_Pst = bind_Block_Plevel_Pst;
    }

    public String getBind_Block_PType() {
        return bind_Block_PType;
    }

    public void setBind_Block_PType(String bind_Block_PType) {
        this.bind_Block_PType = bind_Block_PType;
    }

    public BigDecimal getBind_Block_Ptype_Pst() {
        return bind_Block_Ptype_Pst;
    }

    public void setBind_Block_Ptype_Pst(BigDecimal bind_Block_Ptype_Pst) {
        this.bind_Block_Ptype_Pst = bind_Block_Ptype_Pst;
    }

    public String getBind_County_PType() {
        return bind_County_PType;
    }

    public void setBind_County_PType(String bind_County_PType) {
        this.bind_County_PType = bind_County_PType;
    }

    public BigDecimal getBind_County_Ptype_Pst() {
        return bind_County_Ptype_Pst;
    }

    public void setBind_County_Ptype_Pst(BigDecimal bind_County_Ptype_Pst) {
        this.bind_County_Ptype_Pst = bind_County_Ptype_Pst;
    }

    public String getBind_MixProject_PType() {
        return bind_MixProject_PType;
    }

    public void setBind_MixProject_PType(String bind_MixProject_PType) {
        this.bind_MixProject_PType = bind_MixProject_PType;
    }

    public BigDecimal getBind_MixProject_Pst() {
        return bind_MixProject_Pst;
    }

    public void setBind_MixProject_Pst(BigDecimal bind_MixProject_Pst) {
        this.bind_MixProject_Pst = bind_MixProject_Pst;
    }

    public String getVoppat() {
        return voppat;
    }

    public void setVoppat(String voppat) {
        this.voppat = voppat;
    }

    public BigDecimal getVoppa() {
        return voppa;
    }

    public void setVoppa(BigDecimal voppa) {
        this.voppa = voppa;
    }
}
