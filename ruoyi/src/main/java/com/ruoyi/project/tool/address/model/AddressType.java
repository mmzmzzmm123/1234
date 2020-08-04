package com.ruoyi.project.tool.address.model;

public enum AddressType {
    CONDO(1, "单套"), BUILDING(2, "楼栋"), COMMUNITY(3, "小区");
    private Integer code;
    private String name;

    private AddressType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return this.code;
    }

}
