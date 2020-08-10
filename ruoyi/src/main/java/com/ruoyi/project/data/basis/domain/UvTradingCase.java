package com.ruoyi.project.data.basis.domain;

import java.io.Serializable;

/**
 * 联城买卖成交案例
 *
 * @author lihe
 */
public class UvTradingCase implements Serializable {

    private static final long serialVersionUID = 8510634155374943623L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 案例id
     */
    private String dealId;
    /**
     * 模糊地址
     */
    private String cleanAddress;
    /**
     * 区域
     */
    private String countryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getCleanAddress() {
        return cleanAddress;
    }

    public void setCleanAddress(String cleanAddress) {
        this.cleanAddress = cleanAddress;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
