package com.ruoyi.system.domain.model;

public class DataMatchCompany {

    /**
     * 法人企业
     */
    public static final int TYPE_COMPANY = 0;
    /**
     * 个体工商户
     */
    public static final int TYPE_PERSON = 1;

    /**
     * 企业名称
     */
    private String name;
    /**
     * 企业类型：0为企业，1为个体工商户
     */
    private int type;
    /**
     * 信用代码
     */
    private String xydm;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getXydm() {
        return xydm;
    }

    public void setXydm(String xydm) {
        this.xydm = xydm;
    }
}
