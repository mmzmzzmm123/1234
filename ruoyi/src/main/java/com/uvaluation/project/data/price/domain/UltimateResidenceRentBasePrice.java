package com.uvaluation.project.data.price.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.uvaluation.framework.aspectj.lang.annotation.Excel;
import com.uvaluation.framework.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 核准的住宅租赁基价
 *
 * @author ruoyi
 * @date 2020-05-20
 */
public class UltimateResidenceRentBasePrice extends BaseEntity {
    @JSONField(serialize = false)
    private Integer pageIndex;
    @JSONField(serialize = false)
    private Integer pageSize;

    private Integer yearMonth;
    @Excel(name = "ID")
    private Integer id;
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
    @Excel(name = "国际社区分")
    private String interCommunity;
    @Excel(name = "重点小学名称")
    private String magnetSchool;
    @Excel(name = "物业档次")
    private String projectLevel;
    @Excel(name = "室数量-AI")
    private String roomNum;

    @Excel(name = "价格类型")
    private String priceType;
    @Excel(name = "标准基价")
    private BigDecimal basePrice_1;
    @Excel(name = "平均租金")
    private BigDecimal rentPrice;
    @Excel(name = "散租挂牌案例")
    private Integer sumCase1;
    @Excel(name = "租金涨跌幅")
    private BigDecimal voppa;

    @Excel(name = "租金-工房1室")
    private BigDecimal gf_1Room;
    @Excel(name = "租金-工房2室")
    private BigDecimal gf_2Room;
    @Excel(name = "租金-公寓2室")
    private BigDecimal gy_2Room;
    @Excel(name = "租金-公寓3室")
    private BigDecimal gy_3Room;
    @Excel(name = "租金-1房")
    private BigDecimal one_Room;
    @Excel(name = "租金-2房")
    private BigDecimal two_Room;
    @Excel(name = "租金-3房")
    private BigDecimal three_Room;
    @Excel(name = "整租比")
    private BigDecimal entireRentRatio;
    @Excel(name = "整租案例数")
    private Integer entireRentNum;
    @Excel(name = "分租比")
    private BigDecimal shareRentRatio;
    @Excel(name = "分租案例数")
    private Integer shareRentNum;
    @Excel(name = "租售比")
    private BigDecimal saleRentRatio;
    @Excel(name = "状态")
    private Boolean status;
    @Excel(name = "价格变更说明")
    private String adjEvd;
    @Excel(name = "修改日期")
    private Date modifyDate;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public BigDecimal getBasePrice_1() {
        return basePrice_1;
    }

    public void setBasePrice_1(BigDecimal basePrice_1) {
        this.basePrice_1 = basePrice_1;
    }

    public BigDecimal getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(BigDecimal rentPrice) {
        this.rentPrice = rentPrice;
    }

    public Integer getSumCase1() {
        return sumCase1;
    }

    public void setSumCase1(Integer sumCase1) {
        this.sumCase1 = sumCase1;
    }

    public BigDecimal getVoppa() {
        return voppa;
    }

    public void setVoppa(BigDecimal voppa) {
        this.voppa = voppa;
    }

    public BigDecimal getGf_1Room() {
        return gf_1Room;
    }

    public void setGf_1Room(BigDecimal gf_1Room) {
        this.gf_1Room = gf_1Room;
    }

    public BigDecimal getGf_2Room() {
        return gf_2Room;
    }

    public void setGf_2Room(BigDecimal gf_2Room) {
        this.gf_2Room = gf_2Room;
    }

    public BigDecimal getGy_2Room() {
        return gy_2Room;
    }

    public void setGy_2Room(BigDecimal gy_2Room) {
        this.gy_2Room = gy_2Room;
    }

    public BigDecimal getGy_3Room() {
        return gy_3Room;
    }

    public void setGy_3Room(BigDecimal gy_3Room) {
        this.gy_3Room = gy_3Room;
    }

    public BigDecimal getOne_Room() {
        return one_Room;
    }

    public void setOne_Room(BigDecimal one_Room) {
        this.one_Room = one_Room;
    }

    public BigDecimal getTwo_Room() {
        return two_Room;
    }

    public void setTwo_Room(BigDecimal two_Room) {
        this.two_Room = two_Room;
    }

    public BigDecimal getThree_Room() {
        return three_Room;
    }

    public void setThree_Room(BigDecimal three_Room) {
        this.three_Room = three_Room;
    }

    public BigDecimal getEntireRentRatio() {
        return entireRentRatio;
    }

    public void setEntireRentRatio(BigDecimal entireRentRatio) {
        this.entireRentRatio = entireRentRatio;
    }

    public Integer getEntireRentNum() {
        return entireRentNum;
    }

    public void setEntireRentNum(Integer entireRentNum) {
        this.entireRentNum = entireRentNum;
    }

    public BigDecimal getShareRentRatio() {
        return shareRentRatio;
    }

    public void setShareRentRatio(BigDecimal shareRentRatio) {
        this.shareRentRatio = shareRentRatio;
    }

    public Integer getShareRentNum() {
        return shareRentNum;
    }

    public void setShareRentNum(Integer shareRentNum) {
        this.shareRentNum = shareRentNum;
    }

    public BigDecimal getSaleRentRatio() {
        return saleRentRatio;
    }

    public void setSaleRentRatio(BigDecimal saleRentRatio) {
        this.saleRentRatio = saleRentRatio;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getAdjEvd() {
        return adjEvd;
    }

    public void setAdjEvd(String adjEvd) {
        this.adjEvd = adjEvd;
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

    public String getInterCommunity() {
        return interCommunity;
    }

    public void setInterCommunity(String interCommunity) {
        this.interCommunity = interCommunity;
    }

    public String getMagnetSchool() {
        return magnetSchool;
    }

    public void setMagnetSchool(String magnetSchool) {
        this.magnetSchool = magnetSchool;
    }

    public String getProjectLevel() {
        return projectLevel;
    }

    public void setProjectLevel(String projectLevel) {
        this.projectLevel = projectLevel;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }
}
