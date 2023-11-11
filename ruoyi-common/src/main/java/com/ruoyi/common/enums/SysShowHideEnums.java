package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * @author LAM
 * @date 2023/9/13 4:18
 */
@Getter
public enum SysShowHideEnums {

    SHOW("0","展示"),
    HIDE("1", "隐藏");

    private String code;
    private String desc;

    SysShowHideEnums(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
