package com.ruoyi.project.data.basis.domain;

import java.math.BigDecimal;

/**
 * 小区坐标
 *
 * @author lihe
 */
public class UvCommunityGeo {

    private String communityId;
    private BigDecimal baiduLng;
    private BigDecimal baiduLat;
    private BigDecimal gaodeLng;
    private BigDecimal gaodeLat;


    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public BigDecimal getBaiduLng() {
        return baiduLng;
    }

    public void setBaiduLng(BigDecimal baiduLng) {
        this.baiduLng = baiduLng;
    }

    public BigDecimal getBaiduLat() {
        return baiduLat;
    }

    public void setBaiduLat(BigDecimal baiduLat) {
        this.baiduLat = baiduLat;
    }

    public BigDecimal getGaodeLng() {
        return gaodeLng;
    }

    public void setGaodeLng(BigDecimal gaodeLng) {
        this.gaodeLng = gaodeLng;
    }

    public BigDecimal getGaodeLat() {
        return gaodeLat;
    }

    public void setGaodeLat(BigDecimal gaodeLat) {
        this.gaodeLat = gaodeLat;
    }
}
