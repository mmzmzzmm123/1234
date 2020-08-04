package com.ruoyi.project.tool.address.model;

/**
 * 地址分段
 */
public class SegmentAddress {
    /**
     * 弄、支弄
     */
    private String nong;
    /**
     * 号
     */
    private String hao;
    /**
     * 是否有多个号
     */
    private Boolean multiHao;
    /**
     * 楼层（包含复式）
     */
    private String floor;
    /**
     * 是否多楼层
     */
    private Boolean multiFloor;
    /**
     * 栋、幢、座
     */
    private String dong;
    /**
     * 室号
     */
    private String shi;
    /**
     * 多个室号
     */
    private Boolean multiShi;

    public String getNong() {
        return nong;
    }

    public void setNong(String nong) {
        this.nong = nong;
    }

    public String getHao() {
        return hao;
    }

    public void setHao(String hao) {
        this.hao = hao;
    }

    public Boolean getMultiHao() {
        return multiHao;
    }

    public void setMultiHao(Boolean multiHao) {
        this.multiHao = multiHao;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Boolean getMultiFloor() {
        return multiFloor;
    }

    public void setMultiFloor(Boolean multiFloor) {
        this.multiFloor = multiFloor;
    }

    public String getDong() {
        return dong;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }

    public String getShi() {
        return shi;
    }

    public void setShi(String shi) {
        this.shi = shi;
    }

    public Boolean getMultiShi() {
        return multiShi;
    }

    public void setMultiShi(Boolean multiShi) {
        this.multiShi = multiShi;
    }
}
