package com.ruoyi.project.data.basis.domain;


/**
 * 成交案例地址
 *
 * @author lihe
 */
public class ClosingCaseAddress {
    private String dealId;
    private String address;
    private String cleanAddress;
    private String tag;

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCleanAddress() {
        return cleanAddress;
    }

    public void setCleanAddress(String cleanAddress) {
        this.cleanAddress = cleanAddress;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
