package com.onethinker.common.utils;

import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Title: MathUtils
 * @Author itmei
 * @Package com.cloud.commons.utils
 * @Date 2023/7/23 15:29
 * @description: 金额计算工具类
 */
public class MathUtils {

    /**
     * 加法
     *
     * @param a
     * @param b
     * @return
     */
    public static <T extends Number> Long add(T a, T... b) {
        if (ObjectUtils.isEmpty(b) || ObjectUtils.isEmpty(b[0])) {
            return a.longValue();
        }
        BigDecimal result = new BigDecimal(a.longValue());
        for (T value : b) {
            BigDecimal valueBigDecimal = new BigDecimal(value.longValue());
            result = valueBigDecimal.add(result);
        }
        return result.longValue();
    }

    /**
     * 减法
     *
     * @param a
     * @param b
     * @return
     */
    public static <T extends Number> Long subtract(T a, T... b) {
        if (ObjectUtils.isEmpty(b) || ObjectUtils.isEmpty(b[0])) {
            return a.longValue();
        }
        BigDecimal result = new BigDecimal(a.longValue());
        for (T value : b) {
            BigDecimal valueBigDecimal = new BigDecimal(value.longValue());
            result = result.subtract(valueBigDecimal);
        }
        return result.longValue();
    }

    /**
     * 乘法
     *
     * @param a
     * @param b
     * @return
     */
    public static <T extends Number> Long multiply(T a, T... b) {
        if (ObjectUtils.isEmpty(b) || ObjectUtils.isEmpty(b[0])) {
            return a.longValue();
        }
        BigDecimal result = new BigDecimal(a.longValue());
        for (T value : b) {
            BigDecimal valueBigDecimal = new BigDecimal(value.longValue());
            result = valueBigDecimal.multiply(result);
        }
        return result.longValue();
    }

    /**
     * 除法
     *
     * @param a
     * @param b
     * @return
     */
    public static <T extends Number> Long divide(T a, T... b) {
        if (ObjectUtils.isEmpty(b) || ObjectUtils.isEmpty(b[0])) {
            return a.longValue();
        }
        BigDecimal result = new BigDecimal(a.longValue());
        for (T value : b) {
            if (ObjectUtils.isEmpty(result)) {
                throw new RuntimeException("计算错误，被除数不能为0");
            }
            BigDecimal valueBigDecimal = new BigDecimal(value.longValue());
            result = result.divide(valueBigDecimal, RoundingMode.HALF_UP);
        }
        return result.longValue();
    }


    /**
     * 求余
     *
     * @param a
     * @param b
     * @return
     */
    public static <T extends Number> Integer remainder(T a, T... b) {
        if (ObjectUtils.isEmpty(b) || ObjectUtils.isEmpty(b[0])) {
            return a.intValue();
        }
        BigDecimal result = new BigDecimal(a.longValue());
        for (T value : b) {
            BigDecimal valueBigDecimal = new BigDecimal(value.longValue());
            result = result.remainder(valueBigDecimal);
        }
        return result.intValue();
    }

    /**
     * 最大值
     *
     * @param a
     * @param b
     * @return
     */
    public static <T extends Number> Long max(T a, T... b) {
        if (ObjectUtils.isEmpty(b) || ObjectUtils.isEmpty(b[0])) {
            return a.longValue();
        }
        BigDecimal result = new BigDecimal(a.longValue());
        for (T value : b) {
            BigDecimal valueBigDecimal = new BigDecimal(value.longValue());
            result = valueBigDecimal.max(result);
        }
        return result.longValue();
    }

    /**
     * 最小值
     *
     * @param a
     * @param b
     * @return
     */
    public static <T extends Number> Long min(T a, T... b) {
        if (ObjectUtils.isEmpty(b) || ObjectUtils.isEmpty(b[0])) {
            return a.longValue();
        }
        BigDecimal result = new BigDecimal(a.longValue());
        for (T value : b) {
            BigDecimal valueBigDecimal = new BigDecimal(value.longValue());
            result = valueBigDecimal.min(result);
        }
        return result.longValue();
    }

    public static Double formatAmount(Long money) {
        return Double.valueOf(money) / 100;
    }
}
