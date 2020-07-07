package com.ruoyi.project.data.cases.domain;

import java.math.BigDecimal;

/**
 * 租赁挂牌案例
 */
public class OriginalResidenceRentOpeningCase extends OriginalResidenceSaleOpeningCase {

    /**
     * 外部案例编号
     */
    private String caseNo;
    /**
     * 案例匹配小区id
     */
    private String cleanCommunityId;
    /**
     * 总面积
     */
    private BigDecimal caseTotalArea;
    /**
     * 出租类型
     */
    private String caseRentType;
    /**
     * 状态
     */
    private String caseStatus;
    /**
     * 板块
     */
    private String caseBlock;
    /**
     * 区域
     */
    private String caseCounty;
    /**
     * 环线
     */
    private String caseLoop;
    /**
     * 厅数量
     */
    private Integer caseHallNum;
    /**
     * 室数量
     */
    private Integer caseRoomNum;

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public BigDecimal getCaseTotalArea() {
        return caseTotalArea;
    }

    public void setCaseTotalArea(BigDecimal caseTotalArea) {
        this.caseTotalArea = caseTotalArea;
    }

    public String getCaseRentType() {
        return caseRentType;
    }

    public void setCaseRentType(String caseRentType) {
        this.caseRentType = caseRentType;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getCaseBlock() {
        return caseBlock;
    }

    public void setCaseBlock(String caseBlock) {
        this.caseBlock = caseBlock;
    }

    public String getCaseCounty() {
        return caseCounty;
    }

    public void setCaseCounty(String caseCounty) {
        this.caseCounty = caseCounty;
    }

    public String getCaseLoop() {
        return caseLoop;
    }

    public void setCaseLoop(String caseLoop) {
        this.caseLoop = caseLoop;
    }

    public Integer getCaseHallNum() {
        return caseHallNum;
    }

    public void setCaseHallNum(Integer caseHallNum) {
        this.caseHallNum = caseHallNum;
    }

    public Integer getCaseRoomNum() {
        return caseRoomNum;
    }

    public void setCaseRoomNum(Integer caseRoomNum) {
        this.caseRoomNum = caseRoomNum;
    }

    public String getCleanCommunityId() {
        return cleanCommunityId;
    }

    public void setCleanCommunityId(String cleanCommunityId) {
        this.cleanCommunityId = cleanCommunityId;
    }
}
