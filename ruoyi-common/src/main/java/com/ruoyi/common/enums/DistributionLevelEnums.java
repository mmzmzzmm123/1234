package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * @author LAM
 * @date 2023/11/3 15:02
 */
@Getter
public enum DistributionLevelEnums {

    ONE_LEVEL("0","一级分销"),
    TWO_LEVEL("1", "二级分销");

    private String code;
    private String desc;

    DistributionLevelEnums(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
