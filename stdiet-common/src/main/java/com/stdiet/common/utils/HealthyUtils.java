package com.stdiet.common.utils;

public class HealthyUtils {

    public static final double maxHeatEveryDayLess = 250.0;

    /**
     * 计算每天最大摄入量
     * @param age 年龄
     * @param tall 身高
     * @param weight 体重
     * @return
     */
    public static long calculateMaxHeatEveryDay(Integer age, Integer tall, Double weight){
        age = age == null ? 0 : age;
        tall = tall == null ? 0 : tall;
        weight = weight == null ? 0.0 : weight;
        return Math.round(655+(9.5*weight/2)+(1.8*tall)-(4.7*age) - maxHeatEveryDayLess);
    }
}
