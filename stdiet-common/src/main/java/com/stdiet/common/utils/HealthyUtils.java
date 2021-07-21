package com.stdiet.common.utils;

import com.stdiet.common.enums.AgeBMI;

import java.math.BigDecimal;

public class HealthyUtils {

    public static final long maxHeatEveryDayLess = 250;

    //每克蛋白质对应热量（千卡）
    public static final int proteinHeat = 4;

    //每克脂肪对应热量（千卡）
    public static final int fatHeat = 9;

    //每克碳水对应热量（千卡）
    public static final int carbonWaterHeat = 4;

    //营养成分比例
    public static final Integer[] nutritionRate = {30, 20, 50};

    //默认活动因子
    public static final double activityFactor = 0.3;

    /**
     * 计算减脂每天最大摄入量（千卡）
     * @param age 年龄
     * @param tall 身高
     * @param weight 体重
     * @return
     */
    public static Long calculateMaxHeatEveryDay(Integer age, Integer tall, Double weight){
        age = age == null ? 0 : age;
        tall = tall == null ? 0 : tall;
        weight = weight == null ? 0.0 : weight;
        return calculateMetabolizeHeat(age, tall, weight) - maxHeatEveryDayLess;
    }

    /**
     * 计算基础代谢BMR(千卡）
     * @param age
     * @param tall
     * @param weight
     * @return
     */
    public static Long calculateMetabolizeHeat(Integer age, Integer tall, Double weight){
        return Math.round(655+(9.5*weight/2)+(1.8*tall)-(4.7*age));
    }

    /**
     * 计算每公斤体重占比（千卡/公斤）
     * @param metabolizeHeat 基础代谢BMR(千卡）
     * @param weight 体重（斤）
     * @return
     */
    public static Long calculateHeatRateByWeight(Integer metabolizeHeat, Double weight){
        return Math.round(metabolizeHeat/weight*2);
    }

    /**
     * 计算每公斤体重占比目标范围
     * @param heatRateByWeight 每公斤体重占比（千卡/公斤）
     * @return
     */
    public static Long[] calculateHeatTargetRate(Long heatRateByWeight){
        Long[] heatArray = new Long[2];
        heatArray[0] = heatRateByWeight - 10;
        heatArray[1] = heatRateByWeight - 5;
        return heatArray;
    }

    /**
     * 计算减脂热量控制范围(千卡）
     * @param heatTargetRateArray 每公斤体重占比目标范围
     * @param weight 体重
     * @return
     */
    public static Long[] calculateStandardHeatScopeRate(Long[] heatTargetRateArray, Double weight){
        Long[] heatArray = new Long[2];
        heatArray[0] = Math.round(heatTargetRateArray[0] * weight / 2);
        heatArray[1] = Math.round(heatTargetRateArray[1] * weight / 2);
        return heatArray;
    }


    /**
     * 根据蛋白质、脂肪、碳水质量计算热量
     * @param proteinQuality 蛋白质质量（克）
     * @param fatQuality 脂肪质量（克）
     * @param carbonWaterQuality 碳水质量（克）
     * @return
     */
    public static int calculateTotalHeatByProteinFatCarbonWater(Integer proteinQuality, Integer fatQuality, Integer carbonWaterQuality){
        return calculateHeatByProteinQuality(proteinQuality) + calculateHeatByFatQuality(fatQuality) + calculateHeatByCarbonWaterQuality(carbonWaterQuality);
    }

    /**
     * 根据蛋白质质量计算热量
     * @return
     */
    public static int calculateHeatByProteinQuality(Integer proteinQuality){
        proteinQuality = proteinQuality == null ? 0 : proteinQuality;
        return proteinQuality * proteinHeat;
    }

    /**
     * 根据脂肪质量计算热量
     * @return
     */
    public static int calculateHeatByFatQuality(Integer fatQuality){
        fatQuality = fatQuality == null ? 0 : fatQuality;
        return fatQuality * fatHeat;
    }

    /**
     * 根据碳水质量计算热量
     * @return
     */
    public static int calculateHeatByCarbonWaterQuality(Integer carbonWaterQuality){
        carbonWaterQuality = carbonWaterQuality == null ? 0 : carbonWaterQuality;
        return carbonWaterQuality * carbonWaterHeat;
    }

    /**
     * 根据身高计算标准体重
     * @param tall 身高（厘米）
     * @return
     */
    public static double calculateStandardWeight(int tall){
        return NumberUtils.getNumberByRoundHalfUp((tall-107.5)*2, 1).doubleValue();
    }

    /**
     * 返回蛋白质、脂肪、碳水对应热量、质量
     * @param metabolizeHeat 摄入热量
     * @return
     */
    public static Integer[][] calculateNutritionHeatAndQuality(int metabolizeHeat){
        Integer[] heatArray = new Integer[3];
        Integer[] qualityArray = new Integer[3];
        heatArray[0] = Math.round(nutritionRate[0] * metabolizeHeat /100);
        heatArray[1] = Math.round(nutritionRate[1] * metabolizeHeat /100);
        heatArray[2] = Math.round(metabolizeHeat - heatArray[0] - heatArray[1]);
        qualityArray[0] = (int)Math.round(Double.parseDouble(heatArray[0]+"")/proteinHeat);
        qualityArray[1] = (int)Math.round(Double.parseDouble(heatArray[1]+"")/fatHeat);
        qualityArray[2] = (int)Math.round(Double.parseDouble(heatArray[2]+"")/carbonWaterHeat);
        Integer[][] result = {heatArray, qualityArray};
        return result;
    }

    /**
     * 计算每公斤体重占比(克/公斤)
     * @param nutritionQuality 蛋白质、脂肪、碳水对应质量
     * @param weight 体重
     * @return
     */
    public static Double[] calculateNutritionEveryWeight(Integer[] nutritionQuality, double weight){
        Double[] nutritionEveryWeight = new Double[3];
        nutritionEveryWeight[0] = NumberUtils.getNumberByRoundHalfUp(nutritionQuality[0]/weight*2,2).doubleValue();
        nutritionEveryWeight[1] = NumberUtils.getNumberByRoundHalfUp(nutritionQuality[1]/weight*2,2).doubleValue();
        nutritionEveryWeight[2] = NumberUtils.getNumberByRoundHalfUp(nutritionQuality[2]/weight*2, 2).doubleValue();
        return nutritionEveryWeight;
    }

    /**
     * 计算不运动热量、运动热量
     * @param metabolizeHeat
     * @return
     */
    public static Long[] calculateWithoutExerciseHeat(Integer metabolizeHeat){
        Long[] array = new Long[2];
        //不运动热量
        array[0] = Math.round(metabolizeHeat * (1 + activityFactor));
        //运动热量
        array[1] = Math.round(metabolizeHeat * (1 + 0.8));
        return array;
    }


    /**
     * 根据年龄、性别获取BMI值，再根据BMI值、身高计算标准体重
     * @param tall 身高（厘米）
     * @param age 年龄
     * @param sex 性别 0男 1女
     * @return
     */
    public static Double calculateStandardWeightByBMI(int tall, int age, int sex){
        double bmi = AgeBMI.getBmiByAgeAndSex(age, sex);
        if(bmi > 0){
            BigDecimal standardWeight = NumberUtils.getNumberByRoundHalfUp(bmi * Math.pow(tall/100.0, 2) * 2, 2);
            return standardWeight.doubleValue();
        }
        return null;
    }
}
