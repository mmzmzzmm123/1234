package com.ruoyi.system.domain.model;

/**
 * 个体工商户
 */
public class DataGTGSH {

    /**
     * 店铺名称
     */
    private String name;
    /**
     * 信用代码
     */
    private String xydm;
    /**
     * 经营范围
     */
    private String jyfw;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXydm() {
        return xydm;
    }

    public void setXydm(String xydm) {
        this.xydm = xydm;
    }

    public String getJyfw() {
        return jyfw;
    }

    public void setJyfw(String jyfw) {
        this.jyfw = jyfw;
    }

    @Override
    public String toString() {
        return "DataGTGSH{" +
                "name='" + name + '\'' +
                ", xydm='" + xydm + '\'' +
                ", jyfw='" + jyfw + '\'' +
                '}';
    }
}
