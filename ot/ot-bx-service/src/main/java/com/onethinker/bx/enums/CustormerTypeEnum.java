package com.onethinker.bx.enums;

import lombok.Data;
import lombok.Getter;

/**
 * 客户类别
 * @author yangyouqi
 * @date 2024/5/21
 */
@Getter
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

    public static CustormerTypeEnum valueDesc(String desc) {
        for (CustormerTypeEnum value : CustormerTypeEnum.values()) {
            if (value.desc.equals(desc)) {
                return value;
            }
        }
        throw new RuntimeException("not desc:" + desc);
    }
}
