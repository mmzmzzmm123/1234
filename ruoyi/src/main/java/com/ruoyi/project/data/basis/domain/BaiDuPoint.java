package com.ruoyi.project.data.basis.domain;

/**
 * 百度地图点
 *
 * @author lihe
 */
public class BaiDuPoint {

    private Double x;
    private Double y;

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
