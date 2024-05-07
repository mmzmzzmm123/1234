package com.ruoyi.common.enums;

public enum QueryTypeEnum {
    HALF_YEAR("半年",1),
    SEASON("季度",2),
    THIRTY_DAYS("30天",3);

    QueryTypeEnum(String desc, Integer value) {
        this.desc = desc;
        this.value = value;
    }

    private String desc;
    private Integer value;

    public String getDesc() {
        return desc;
    }

    public Integer getValue() {
        return value;
    }
}
