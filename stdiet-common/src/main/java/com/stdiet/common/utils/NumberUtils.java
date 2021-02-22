package com.stdiet.common.utils;

import java.math.BigDecimal;

public class NumberUtils {

    /**
     * 对double数字进行四舍五入，返回BigDecimal
     * @param number 数字
     * @param decimalPlaces 保留的小数点位数
     * @return
     */
    public static BigDecimal getNumberByRoundHalfUp(double number, int decimalPlaces){
        return BigDecimal.valueOf(number).setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
    }

}
