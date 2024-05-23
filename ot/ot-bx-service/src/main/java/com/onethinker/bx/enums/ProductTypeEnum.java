package com.onethinker.bx.enums;

import lombok.Getter;

/**
 * 产品类别
 *
 * @author yangyouqi
 * @date 2024/5/21
 */
@Getter
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


    public static ProductTypeEnum valueDesc(String desc) {
        for (ProductTypeEnum value : ProductTypeEnum.values()) {
            if (value.desc.equals(desc)) {
                return value;
            }
        }
        throw new RuntimeException("not desc:" + desc);
    }
}
