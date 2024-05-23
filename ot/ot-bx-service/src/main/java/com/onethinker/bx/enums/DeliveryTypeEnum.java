package com.onethinker.bx.enums;

import lombok.Getter;

/**
 * 送货方式
 * @author yangyouqi
 * @date 2024/5/21
 */
@Getter
public enum DeliveryTypeEnum {
    SF("顺丰快递"),

    RG("直接送货"),

    SF_SHORT("顺丰");


    final String desc;

    DeliveryTypeEnum(String message) {
        this.desc = message;
    }

    public static DeliveryTypeEnum valueDesc(String desc) {
        for (DeliveryTypeEnum value : DeliveryTypeEnum.values()) {
            if (value.desc.equals(desc)) {
                return value;
            }
        }
        throw new RuntimeException("not desc:" + desc);
    }
}
