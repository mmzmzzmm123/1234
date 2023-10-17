package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * @author LAM
 * @date 2023/10/17 14:21
 */
@Getter
public enum DateTimeUnitEnums {

    MONTH("0","月"),
    WEEK("1", "周"),
    DAY("2", "日"),
    HOUR("3", "时"),
    MINUTE("4", "分");

    private String code;
    private String desc;

    DateTimeUnitEnums(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取对象
     *
     * @param code 编码
     * @return 结果
     * */
    public static DateTimeUnitEnums getByCode(String code){
        for (DateTimeUnitEnums temp : DateTimeUnitEnums.values()){
            if (temp.getCode().equals(code)){
                return temp;
            }
        }
        return null;
    }

}
