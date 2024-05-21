package com.onethinker.bx.enums;

/**
 * 客户类别
 * @author yangyouqi
 * @date 2024/5/21
 */
public enum CustormerTypeEnum {
    YI_YUAN("医院"),

    GE_REN("个人"),

    JI_GONG_SUO("技工所"),

    ZHEN_SUO("诊所")
    ;

    final String desc;

    CustormerTypeEnum(String desc) {
        this.desc = desc;
    }
}
