package com.ruoyi.project.tool.address.model;

import java.util.LinkedList;
import java.util.List;

/**
 * 清洗地址
 *
 * @author lihe
 */
public class CleanAddress {
    /**
     * 原地址
     */
    private String rawAddress;
    /**
     * 待处理的地址
     */
    private StringBuilder boundAddress;
    /**
     * 区域
     */
    private String district;
    /**
     * 镇
     */
    private String region;
    /**
     * 板块
     */
    private String block;
    /**
     * 路名（可能交叉路）
     */
    private String road;
    /**
     * 小区名称
     */
    private String communityName;
    /**
     * 楼层
     * （和地下有关系）
     */
    private String floor;
    /**
     * 单套地址
     */
    private PartialAddress condoAddress;
    /**
     * 物业（普通、车位）
     */
    private String propertyType;
    /**
     * 独栋
     */
    private Boolean independent;
    /**
     * 多个地址
     */
    private List<PartialAddress> addressList = new LinkedList<>();

    /**
     * 是否包含特殊字符
     */
    private Boolean containsSpecialChar;

    private static List<String> specialChar = new LinkedList<>();

    static {
        specialChar.add("。");
        specialChar.add(".");
        specialChar.add("，");
        specialChar.add(",");
        specialChar.add("-");
        specialChar.add("——");
        specialChar.add("_");
        specialChar.add("、");
        specialChar.add("（");
        specialChar.add("）");
        specialChar.add("(");
        specialChar.add(")");
        specialChar.add("《");
        specialChar.add("》");
    }

    public CleanAddress(String rawAddress) {
        this.rawAddress = rawAddress;

    }

    public String getRawAddress() {
        return rawAddress;
    }

    public void setRawAddress(String rawAddress) {
        this.rawAddress = rawAddress;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public PartialAddress getCondoAddress() {
        return condoAddress;
    }

    public void setCondoAddress(PartialAddress condoAddress) {
        this.condoAddress = condoAddress;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public List<PartialAddress> getAddressList() {
        return addressList;
    }

    public void addAddress(PartialAddress partialAddress) {
        this.addressList.add(partialAddress);
    }

    public Boolean getIndependent() {
        return independent;
    }

    public void setIndependent(Boolean independent) {
        this.independent = independent;
    }

    public StringBuilder getBoundAddress() {
        return boundAddress;
    }

    public Boolean getContainsSpecialChar() {
        String todoAddress = rawAddress.trim()
                .replace("\t", "")
                .replace(" ", "");

        this.boundAddress = new StringBuilder(rawAddress);
        for (int i = 0; i < specialChar.size(); i++) {
            if (todoAddress.contains(specialChar.get(i))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "CleanAddress{" +
                "rawAddress='" + rawAddress + '\'' +
                ", district='" + district + '\'' +
                ", region='" + region + '\'' +
                ", block='" + block + '\'' +
                ", road='" + road + '\'' +
                ", communityName='" + communityName + '\'' +
                ", floor=" + floor +
                ", condoAddress=" + condoAddress +
                ", propertyType='" + propertyType + '\'' +
                ", independent=" + independent +
                ", addressList=" + addressList +
                ", containsSpecialChar=" + containsSpecialChar +
                '}';
    }
}
