package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * @author LAM
 * @date 2023/9/23 21:59
 */
@Getter
public enum StaffStateEnums {

    DISABLE("-1","禁用"),
    NORMAL("0", "正常"),
    EXAMINE("1", "审核中"),
    NOT_PASS("2", "审核不通过");

    private String code;
    private String desc;

    StaffStateEnums(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
