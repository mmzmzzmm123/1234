package com.ruoyi.project.data.cases.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 租赁汇总案例
 */
public class CleanResidenceRentAggregationCase {

    private Integer yearMonth;
    /**
     * 案例单套ID
     */
    private String householdsIdSRC;
    /**
     * 案例小区ID
     */
    private String projectIdSRC;
    /**
     * 小区ID
     */
    private String communityId;

    private String buildingId;
    /**
     * WHEN A.RentType=1 THEN ''整租''
     * WHEN A.RentType=2 THEN ''散租''
     * WHEN A.RentType=3 THEN ''分租''
     */
    private Integer rentType;
    /**
     * 室
     */
    private Integer roomNum;
    /**
     * 厅
     */
    private Integer hallNum;
    /**
     * 卫
     */
    private Integer bashRoomNum;
    /**
     * 面积
     */
    private BigDecimal area;
    /**
     * 朝向
     */
    private String toward;
    /**
     * UpperFloorSum 总层数
     */
    private String totalFloor;
    /**
     * UpperFloorNum 所在层
     */
    private String currentFloor;
    /**
     * 有无电梯
     */
    private Boolean elevator;
    /**
     * 装修类型
     */
    private String decoration;
    /**
     * 建成年代
     */
    private Integer year;
    /**
     * 房屋面积系数
     */
    private BigDecimal areaCoefficient;
    /**
     * 朝向系数
     */
    private BigDecimal towardCoefficient;
    /**
     * 楼层系数
     */
    private BigDecimal floorCoefficient;
    /**
     * 装修系数
     */
    private Integer decorationCoefficient;
    /**
     * 建成年代系数
     */
    private BigDecimal yearCoefficient;
    /**
     * 楼栋系数
     */
    private BigDecimal buildingCoefficient;
    /**
     * 室厅修正系数
     */
    private BigDecimal roomTypeCoefficient;
    /**
     * 总价（元）
     */
    private BigDecimal totalPrice;
    /**
     * 单价（元每平方米）
     */
    private BigDecimal unitPrice;
    /**
     * 散租单价
     */
    private BigDecimal priceScatterRent;
    /**
     * 整租单价
     */
    private BigDecimal priceEntireRent;
    /**
     * 合租单间价
     */
    private BigDecimal priceShareRent0;
    /**
     * 合租折算价
     */
    private BigDecimal priceShareRent;
    /**
     * 带看总次数
     */
    private Integer visitedNum;
    /**
     * 首次带看时间
     */
    private Date firstVisitedDate;
    /**
     * 15天内带看总次数
     */
    private Integer visitedNum15;
    /**
     * 30天内带看总次数
     */
    private Integer visitedNum30;
    /**
     * 案例状态
     */
    private Integer status;
    /**
     * 最近一次挂牌总价调整幅度
     */
    private BigDecimal adjustedValue;
    /**
     * 最近一次挂牌总价调整比例
     */
    private BigDecimal adjustedPst;
    /**
     * 累计挂牌总价调整幅度
     */
    private BigDecimal adjustedCumValue;
    /**
     * 累计挂牌总价调整比例
     */
    private BigDecimal adjustedCumPst;
    /**
     * 累计挂牌总价绝对调整幅度
     */
    private BigDecimal adjustedCumValueAbs;
    /**
     * 累计挂牌总价绝对调整比例
     */
    private BigDecimal adjustedCumPstAbs;
    /**
     * 累计挂牌总价调价次数
     */
    private BigDecimal adjustedCumNum;
    /**
     * 首次挂牌总价
     */
    private BigDecimal priceTotalIn;
    /**
     * 下架时挂牌总价
     */
    private BigDecimal priceTotalOut;
    /**
     * 首次挂牌日期
     */
    private Date priceDateIn;
    /**
     * 下架日期
     */
    private Date priceDateOut;
    /**
     * 案例来源
     */
    private String origin;
    /**
     * 案例URL
     */
    private String urlHouseholds;
    /**
     * 案例小区URL
     */
    private String urlProjects;
    /**
     * 案例名称
     */
    private String caseName;
    /**
     * 0=成交
     * 1=挂牌
     */
    private Integer caseType;
    /**
     * 平均租金（上周期）
     */
    private BigDecimal rentPrice_1;
    /**
     * 案例涨跌幅
     */
    private BigDecimal range;
    /**
     * 涨跌类型
     */
    private Integer rangeFlag;
    /**
     * 平均租金
     */
    private BigDecimal rentPrice;
    /**
     * 整租比
     */
    private BigDecimal entireRentRatio;
    /**
     * 分租比
     */
    private BigDecimal shareRentRatio;

    public Integer getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Integer yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getHouseholdsIdSRC() {
        return householdsIdSRC;
    }

    public void setHouseholdsIdSRC(String householdsIdSRC) {
        this.householdsIdSRC = householdsIdSRC;
    }

    public String getProjectIdSRC() {
        return projectIdSRC;
    }

    public void setProjectIdSRC(String projectIdSRC) {
        this.projectIdSRC = projectIdSRC;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getRentType() {
        return rentType;
    }

    public void setRentType(Integer rentType) {
        this.rentType = rentType;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public Integer getHallNum() {
        return hallNum;
    }

    public void setHallNum(Integer hallNum) {
        this.hallNum = hallNum;
    }

    public Integer getBashRoomNum() {
        return bashRoomNum;
    }

    public void setBashRoomNum(Integer bashRoomNum) {
        this.bashRoomNum = bashRoomNum;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public String getToward() {
        return toward;
    }

    public void setToward(String toward) {
        this.toward = toward;
    }

    public String getTotalFloor() {
        return totalFloor;
    }

    public void setTotalFloor(String totalFloor) {
        this.totalFloor = totalFloor;
    }

    public String getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(String currentFloor) {
        this.currentFloor = currentFloor;
    }

    public Boolean getElevator() {
        return elevator;
    }

    public void setElevator(Boolean elevator) {
        this.elevator = elevator;
    }

    public String getDecoration() {
        return decoration;
    }

    public void setDecoration(String decoration) {
        this.decoration = decoration;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getAreaCoefficient() {
        return areaCoefficient;
    }

    public void setAreaCoefficient(BigDecimal areaCoefficient) {
        this.areaCoefficient = areaCoefficient;
    }

    public BigDecimal getTowardCoefficient() {
        return towardCoefficient;
    }

    public void setTowardCoefficient(BigDecimal towardCoefficient) {
        this.towardCoefficient = towardCoefficient;
    }

    public BigDecimal getFloorCoefficient() {
        return floorCoefficient;
    }

    public void setFloorCoefficient(BigDecimal floorCoefficient) {
        this.floorCoefficient = floorCoefficient;
    }

    public Integer getDecorationCoefficient() {
        return decorationCoefficient;
    }

    public void setDecorationCoefficient(Integer decorationCoefficient) {
        this.decorationCoefficient = decorationCoefficient;
    }

    public BigDecimal getYearCoefficient() {
        return yearCoefficient;
    }

    public void setYearCoefficient(BigDecimal yearCoefficient) {
        this.yearCoefficient = yearCoefficient;
    }

    public BigDecimal getBuildingCoefficient() {
        return buildingCoefficient;
    }

    public void setBuildingCoefficient(BigDecimal buildingCoefficient) {
        this.buildingCoefficient = buildingCoefficient;
    }

    public BigDecimal getRoomTypeCoefficient() {
        return roomTypeCoefficient;
    }

    public void setRoomTypeCoefficient(BigDecimal roomTypeCoefficient) {
        this.roomTypeCoefficient = roomTypeCoefficient;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getPriceScatterRent() {
        return priceScatterRent;
    }

    public void setPriceScatterRent(BigDecimal priceScatterRent) {
        this.priceScatterRent = priceScatterRent;
    }

    public BigDecimal getPriceEntireRent() {
        return priceEntireRent;
    }

    public void setPriceEntireRent(BigDecimal priceEntireRent) {
        this.priceEntireRent = priceEntireRent;
    }

    public BigDecimal getPriceShareRent0() {
        return priceShareRent0;
    }

    public void setPriceShareRent0(BigDecimal priceShareRent0) {
        this.priceShareRent0 = priceShareRent0;
    }

    public BigDecimal getPriceShareRent() {
        return priceShareRent;
    }

    public void setPriceShareRent(BigDecimal priceShareRent) {
        this.priceShareRent = priceShareRent;
    }

    public Integer getVisitedNum() {
        return visitedNum;
    }

    public void setVisitedNum(Integer visitedNum) {
        this.visitedNum = visitedNum;
    }

    public Date getFirstVisitedDate() {
        return firstVisitedDate;
    }

    public void setFirstVisitedDate(Date firstVisitedDate) {
        this.firstVisitedDate = firstVisitedDate;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getAdjustedValue() {
        return adjustedValue;
    }

    public void setAdjustedValue(BigDecimal adjustedValue) {
        this.adjustedValue = adjustedValue;
    }

    public BigDecimal getAdjustedPst() {
        return adjustedPst;
    }

    public void setAdjustedPst(BigDecimal adjustedPst) {
        this.adjustedPst = adjustedPst;
    }

    public BigDecimal getAdjustedCumValue() {
        return adjustedCumValue;
    }

    public void setAdjustedCumValue(BigDecimal adjustedCumValue) {
        this.adjustedCumValue = adjustedCumValue;
    }

    public BigDecimal getAdjustedCumPst() {
        return adjustedCumPst;
    }

    public void setAdjustedCumPst(BigDecimal adjustedCumPst) {
        this.adjustedCumPst = adjustedCumPst;
    }

    public BigDecimal getAdjustedCumValueAbs() {
        return adjustedCumValueAbs;
    }

    public void setAdjustedCumValueAbs(BigDecimal adjustedCumValueAbs) {
        this.adjustedCumValueAbs = adjustedCumValueAbs;
    }

    public BigDecimal getAdjustedCumPstAbs() {
        return adjustedCumPstAbs;
    }

    public void setAdjustedCumPstAbs(BigDecimal adjustedCumPstAbs) {
        this.adjustedCumPstAbs = adjustedCumPstAbs;
    }

    public BigDecimal getAdjustedCumNum() {
        return adjustedCumNum;
    }

    public void setAdjustedCumNum(BigDecimal adjustedCumNum) {
        this.adjustedCumNum = adjustedCumNum;
    }

    public BigDecimal getPriceTotalIn() {
        return priceTotalIn;
    }

    public void setPriceTotalIn(BigDecimal priceTotalIn) {
        this.priceTotalIn = priceTotalIn;
    }

    public BigDecimal getPriceTotalOut() {
        return priceTotalOut;
    }

    public void setPriceTotalOut(BigDecimal priceTotalOut) {
        this.priceTotalOut = priceTotalOut;
    }

    public Date getPriceDateIn() {
        return priceDateIn;
    }

    public void setPriceDateIn(Date priceDateIn) {
        this.priceDateIn = priceDateIn;
    }

    public Date getPriceDateOut() {
        return priceDateOut;
    }

    public void setPriceDateOut(Date priceDateOut) {
        this.priceDateOut = priceDateOut;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUrlHouseholds() {
        return urlHouseholds;
    }

    public void setUrlHouseholds(String urlHouseholds) {
        this.urlHouseholds = urlHouseholds;
    }

    public String getUrlProjects() {
        return urlProjects;
    }

    public void setUrlProjects(String urlProjects) {
        this.urlProjects = urlProjects;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public Integer getCaseType() {
        return caseType;
    }

    public void setCaseType(Integer caseType) {
        this.caseType = caseType;
    }

    public BigDecimal getRentPrice_1() {
        return rentPrice_1;
    }

    public void setRentPrice_1(BigDecimal rentPrice_1) {
        this.rentPrice_1 = rentPrice_1;
    }

    public BigDecimal getRange() {
        return range;
    }

    public void setRange(BigDecimal range) {
        this.range = range;
    }

    public Integer getRangeFlag() {
        return rangeFlag;
    }

    public void setRangeFlag(Integer rangeFlag) {
        this.rangeFlag = rangeFlag;
    }

    public BigDecimal getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(BigDecimal rentPrice) {
        this.rentPrice = rentPrice;
    }

    public BigDecimal getEntireRentRatio() {
        return entireRentRatio;
    }

    public void setEntireRentRatio(BigDecimal entireRentRatio) {
        this.entireRentRatio = entireRentRatio;
    }

    public BigDecimal getShareRentRatio() {
        return shareRentRatio;
    }

    public void setShareRentRatio(BigDecimal shareRentRatio) {
        this.shareRentRatio = shareRentRatio;
    }
}
