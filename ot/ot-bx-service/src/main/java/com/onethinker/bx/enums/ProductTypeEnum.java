package com.onethinker.bx.enums;

/**
 * 产品类别
 *
 * @author yangyouqi
 * @date 2024/5/21
 */
public enum ProductTypeEnum {
    CHONG_QING("重庆"),
    DA_QING("大清"),
    DENG_TENG("登腾"),

    QIANG_ZHI_JIE("腔治捷"),

    YI_MEI("易美"),
    YIN_SHI_MEI("隐适美");

    final String desc;

    ProductTypeEnum(String desc) {
        this.desc = desc;
    }
}
