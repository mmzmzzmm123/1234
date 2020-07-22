package com.ruoyi.project.data.basis.domain;

/**
 * 高德坐标系
 *
 * @author lihe
 */
public class GDConvertCordinateResult {

    private Integer status;
    private String info;
    private String locations;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }
}
