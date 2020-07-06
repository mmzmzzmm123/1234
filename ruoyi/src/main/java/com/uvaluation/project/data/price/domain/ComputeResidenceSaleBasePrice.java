package com.uvaluation.project.data.price.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.uvaluation.framework.aspectj.lang.annotation.Excel;
import com.uvaluation.framework.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 计算的住宅小区售价
 *
 * @author ruoyi
 * @date 2020-05-20
 */
public class ComputeResidenceSaleBasePrice extends BaseEntity {
    private Integer pageIndex;
    private Integer pageSize;
    @JSONField(serialize = false)
    private Integer yearMonth;
    @JSONField(serialize = false)
    private String nameOrAddress;

    @Excel(name = "ID")
    private String id;
    @Excel(name = "AI小区ID")
    private String communityId;
    @Excel(name = "小区名称")
    private String communityName;
    @Excel(name = "小区地址")
    private String communityAddress;
    @Excel(name = "区域")
    private String countyName;
    @Excel(name = "板块")
    private String blockName;
    @Excel(name = "环线")
    private String loopName;
    @Excel(name = "是否生成索引")
    private Integer hasIndex;
    @Excel(name = "参与涨幅计算")
    private Integer isPstCalc;
    @Excel(name = "运行状态")
    private Integer status;
    @Excel(name = "特殊小区标签")
    private String label;
    @Excel(name = "物业类型")
    private String propertyType;
    @Excel(name = "小区类型")
    private String projectType;
    @Excel(name = "小区类型细分")
    private String projectTypeDtl;
    @Excel(name = "物业档次")
    private String propertyLevel;
    @Excel(name = "建成年代")
    private Integer year;
    @Excel(name = "主力面积")
    private BigDecimal area;
    @Excel(name = "房屋面积系数")
    private BigDecimal areaCoefficient;
    @Excel(name = "建成年代系数")
    private BigDecimal yearCoefficient;
    @Excel(name = "价格说明")
    private String comment;
    @Excel(name = "AI基价(草稿)")
    private BigDecimal basePriceDraft;
    @Excel(name = "主力基价(草稿)")
    private BigDecimal mainPriceDraft;
    @Excel(name = "上月AI基价")
    private BigDecimal basePrice_1;
    @Excel(name = "价格涨跌幅类型-调整后")
    private String voppat;
    @Excel(name = "价格涨跌幅-调整后")
    private BigDecimal voppa;
    @Excel(name = "案例均价")
    private BigDecimal priceUnitAdj;
    /**
     * 累计带看
     */
    @Excel(name = "平均访客数量")
    private Integer visitedNum;
    /**
     * 首次带看日
     */
    @Excel(name = "首次看房时间")
    private Date firstVisitTime;
    /**
     * 近15日带看
     */
    @Excel(name = "15天内平均访客数量")
    private Integer visitedNum15;
    /**
     * 近30天带看
     */
    @Excel(name = "30天内平均访客数量")
    private Integer visitedNum30;
    @Excel(name = "成交均价(上周期)")
    private BigDecimal priceDealMean_1;
    @Excel(name = "成交最大价(上周期)")
    private BigDecimal priceDealMax_1;
    @Excel(name = "成交数量(上周期)")
    private Integer sumDeal_1;
    @Excel(name = "成交均价")
    private BigDecimal priceDealMean;
    @Excel(name = "成交最大价")
    private BigDecimal priceDealMax;
    @Excel(name = "成交数量")
    private Integer sumDeal;

    @Excel(name = "挂牌最低价")
    private Integer priceListedMin;
    @Excel(name = "与上月AI基价链家1案例价格价调整比例")
    private BigDecimal priceCase1_ToAI_Pst;
    @Excel(name = "与上月AI基价链家2案例价格价调整比例")
    private BigDecimal priceCase2_ToAI_Pst;
    @Excel(name = "当月比上月链1案例价调整比例")
    private BigDecimal priceCase1_ToLst_Pst;
    @Excel(name = "当月比上月链2案例价调整比例")
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
    private String voppbt;
    @Excel(name = "价格涨跌幅-调整前")
    private BigDecimal voppb;

    @Excel(name = "绑定小区编号")
    // BindProjID
    private String bindCommunityId;
    @Excel(name = "绑定小区涨跌幅")
    // Bind_Proj_Pst
    private BigDecimal bindCommunityPst;
    @Excel(name = "绑定板块+聚类ID")
    // Bind_Block_Class
    private String bindBlockClass;
    @Excel(name = "绑定板块+聚类ID的涨跌幅")
    // Bind_Block_Class_Pst
    private BigDecimal bindBlockClassPst;
    @Excel(name = "绑定板块+物业档次")
    // Bind_Block_Plevel
    private String bindBlockPropertyLevel;
    @Excel(name = "绑定板块+物业档次的涨跌幅")
    // Bind_Block_Plevel_Pst
    private BigDecimal bindBlockPropertyLevelPst;
    @Excel(name = "绑定板块+小区类型")
    // Bind_Block_PType
    private String bindBlockProjectType;
    @Excel(name = "绑定板块+小区类型的涨跌幅")
    // Bind_Block_Ptype_Pst
    private BigDecimal bindBlockProjectTypePst;
    @Excel(name = "绑定区县+小区类型")
    // Bind_County_PType
    private String bindCountyProjectType;
    @Excel(name = "绑定区县+小区类型的涨跌幅")
    // Bind_County_PType_Pst
    private BigDecimal bindCountyProjectTypePst;

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

    public Integer getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Integer yearMonth) {
        this.yearMonth = yearMonth;
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

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getLoopName() {
        return loopName;
    }

    public void setLoopName(String loopName) {
        this.loopName = loopName;
    }

    public Integer getHasIndex() {
        return hasIndex;
    }

    public void setHasIndex(Integer hasIndex) {
        this.hasIndex = hasIndex;
    }

    public Integer getIsPstCalc() {
        return isPstCalc;
    }

    public void setIsPstCalc(Integer isPstCalc) {
        this.isPstCalc = isPstCalc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public String getPropertyLevel() {
        return propertyLevel;
    }

    public void setPropertyLevel(String propertyLevel) {
        this.propertyLevel = propertyLevel;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getAreaCoefficient() {
        return areaCoefficient;
    }

    public void setAreaCoefficient(BigDecimal areaCoefficient) {
        this.areaCoefficient = areaCoefficient;
    }

    public BigDecimal getYearCoefficient() {
        return yearCoefficient;
    }

    public void setYearCoefficient(BigDecimal yearCoefficient) {
        this.yearCoefficient = yearCoefficient;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getBasePriceDraft() {
        return basePriceDraft;
    }

    public void setBasePriceDraft(BigDecimal basePriceDraft) {
        this.basePriceDraft = basePriceDraft;
    }

    public BigDecimal getMainPriceDraft() {
        return mainPriceDraft;
    }

    public void setMainPriceDraft(BigDecimal mainPriceDraft) {
        this.mainPriceDraft = mainPriceDraft;
    }

    public BigDecimal getBasePrice_1() {
        return basePrice_1;
    }

    public void setBasePrice_1(BigDecimal basePrice_1) {
        this.basePrice_1 = basePrice_1;
    }



    public BigDecimal getPriceUnitAdj() {
        return priceUnitAdj;
    }

    public void setPriceUnitAdj(BigDecimal priceUnitAdj) {
        this.priceUnitAdj = priceUnitAdj;
    }

    public Integer getVisitedNum() {
        return visitedNum;
    }

    public void setVisitedNum(Integer visitedNum) {
        this.visitedNum = visitedNum;
    }

    public Date getFirstVisitTime() {
        return firstVisitTime;
    }

    public void setFirstVisitTime(Date firstVisitTime) {
        this.firstVisitTime = firstVisitTime;
    }

    public Integer getVisitedNum15() {
        return visitedNum15;
    }

    public void setVisitedNum15(Integer visitedNum15) {
        this.visitedNum15 = visitedNum15;
    }

    public Integer getVisitedNum30() {
        return visitedNum30;
    }

    public void setVisitedNum30(Integer visitedNum30) {
        this.visitedNum30 = visitedNum30;
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

    public Integer getPriceListedMin() {
        return priceListedMin;
    }

    public void setPriceListedMin(Integer priceListedMin) {
        this.priceListedMin = priceListedMin;
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

    public String getVoppbt() {
        return voppbt;
    }

    public void setVoppbt(String voppbt) {
        this.voppbt = voppbt;
    }

    public String getBindCommunityId() {
        return bindCommunityId;
    }

    public void setBindCommunityId(String bindCommunityId) {
        this.bindCommunityId = bindCommunityId;
    }

    public BigDecimal getBindCommunityPst() {
        return bindCommunityPst;
    }

    public void setBindCommunityPst(BigDecimal bindCommunityPst) {
        this.bindCommunityPst = bindCommunityPst;
    }

    public String getBindBlockClass() {
        return bindBlockClass;
    }

    public void setBindBlockClass(String bindBlockClass) {
        this.bindBlockClass = bindBlockClass;
    }

    public BigDecimal getBindBlockClassPst() {
        return bindBlockClassPst;
    }

    public void setBindBlockClassPst(BigDecimal bindBlockClassPst) {
        this.bindBlockClassPst = bindBlockClassPst;
    }

    public String getBindBlockPropertyLevel() {
        return bindBlockPropertyLevel;
    }

    public void setBindBlockPropertyLevel(String bindBlockPropertyLevel) {
        this.bindBlockPropertyLevel = bindBlockPropertyLevel;
    }

    public BigDecimal getBindBlockPropertyLevelPst() {
        return bindBlockPropertyLevelPst;
    }

    public void setBindBlockPropertyLevelPst(BigDecimal bindBlockPropertyLevelPst) {
        this.bindBlockPropertyLevelPst = bindBlockPropertyLevelPst;
    }

    public String getBindBlockProjectType() {
        return bindBlockProjectType;
    }

    public void setBindBlockProjectType(String bindBlockProjectType) {
        this.bindBlockProjectType = bindBlockProjectType;
    }

    public BigDecimal getBindBlockProjectTypePst() {
        return bindBlockProjectTypePst;
    }

    public void setBindBlockProjectTypePst(BigDecimal bindBlockProjectTypePst) {
        this.bindBlockProjectTypePst = bindBlockProjectTypePst;
    }

    public String getBindCountyProjectType() {
        return bindCountyProjectType;
    }

    public void setBindCountyProjectType(String bindCountyProjectType) {
        this.bindCountyProjectType = bindCountyProjectType;
    }

    public BigDecimal getBindCountyProjectTypePst() {
        return bindCountyProjectTypePst;
    }

    public void setBindCountyProjectTypePst(BigDecimal bindCountyProjectTypePst) {
        this.bindCountyProjectTypePst = bindCountyProjectTypePst;
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

    public BigDecimal getVoppb() {
        return voppb;
    }

    public void setVoppb(BigDecimal voppb) {
        this.voppb = voppb;
    }

    public String getNameOrAddress() {
        return nameOrAddress;
    }

    public void setNameOrAddress(String nameOrAddress) {
        this.nameOrAddress = nameOrAddress;
    }
}
