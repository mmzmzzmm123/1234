package com.onethinker.bx.enums;

/**
 * 送货方式
 * @author yangyouqi
 * @date 2024/5/21
 */
public enum DeliveryTypeEnum {
    SF("顺丰快递"),

    RG("直接送货");

    final String desc;

    DeliveryTypeEnum(String message) {
        this.desc = message;
    }
}
