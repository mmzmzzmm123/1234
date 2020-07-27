package com.ruoyi.project.data.basis.domain;

/**
 * Geo对象
 *
 * @author lihe
 */
public class UvGeo {
    private Integer id;
    private String geoText;
    /**
     * 1 百度
     * 2 高德
     * 3 84
     */
    private Integer coordinateType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGeoText() {
        return geoText;
    }

    public void setGeoText(String geoText) {
        this.geoText = geoText;
    }

    public Integer getCoordinateType() {
        return coordinateType;
    }

    public void setCoordinateType(Integer coordinateType) {
        this.coordinateType = coordinateType;
    }
}
