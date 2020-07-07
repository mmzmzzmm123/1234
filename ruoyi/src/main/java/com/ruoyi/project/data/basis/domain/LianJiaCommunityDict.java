package com.ruoyi.project.data.basis.domain;

import com.ruoyi.framework.web.domain.BaseEntity;


/**
 * 链家id和联城小区、楼栋id对应关系
 */
public class LianJiaCommunityDict extends BaseEntity {

    /**
     *
     */
    private Integer pageIndex;
    /**
     *
     */
    private Integer pageSize;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 案例id
     */
    private String lianJiaCommunityId;
    /**
     * 案例小区名称
     */
    private String lianJiaCommunityName;
    /**
     * 案例小区地址
     */
    private String lianJiaCommunityAddress;
    /**
     * 链家小区url
     */
    private String lianJiaCommunityUrl;
    /**
     * 链家案例url
     */
    private String lianJiaUrl;

    /**
     * 联城小区id
     */
    private String communityId;
    /**
     * 联城楼栋id
     */
    private String buildingId;
    /**
     * 小区名称（不带特殊符号的）
     */
    private String cleanCommunityName;
    /**
     * 小区地址（不带特殊符号的）
     */
    private String cleanCommunityAddress;
    /**
     * 楼栋地址（不带特殊符号的）
     */
    private String cleanBuildingAddress;

    /**
     * 是否有效
     */
    private Boolean enable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLianJiaCommunityId() {
        return lianJiaCommunityId;
    }

    public void setLianJiaCommunityId(String lianJiaCommunityId) {
        this.lianJiaCommunityId = lianJiaCommunityId;
    }

    public String getLianJiaCommunityName() {
        return lianJiaCommunityName;
    }

    public void setLianJiaCommunityName(String lianJiaCommunityName) {
        this.lianJiaCommunityName = lianJiaCommunityName;
    }

    public String getLianJiaCommunityAddress() {
        return lianJiaCommunityAddress;
    }

    public void setLianJiaCommunityAddress(String lianJiaCommunityAddress) {
        this.lianJiaCommunityAddress = lianJiaCommunityAddress;
    }

    public String getLianJiaCommunityUrl() {
        return lianJiaCommunityUrl;
    }

    public void setLianJiaCommunityUrl(String lianJiaCommunityUrl) {
        this.lianJiaCommunityUrl = lianJiaCommunityUrl;
    }

    public String getLianJiaUrl() {
        return lianJiaUrl;
    }

    public void setLianJiaUrl(String lianJiaUrl) {
        this.lianJiaUrl = lianJiaUrl;
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

    public String getCleanCommunityName() {
        return cleanCommunityName;
    }

    public void setCleanCommunityName(String cleanCommunityName) {
        this.cleanCommunityName = cleanCommunityName;
    }

    public String getCleanCommunityAddress() {
        return cleanCommunityAddress;
    }

    public void setCleanCommunityAddress(String cleanCommunityAddress) {
        this.cleanCommunityAddress = cleanCommunityAddress;
    }

    public String getCleanBuildingAddress() {
        return cleanBuildingAddress;
    }

    public void setCleanBuildingAddress(String cleanBuildingAddress) {
        this.cleanBuildingAddress = cleanBuildingAddress;
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

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
