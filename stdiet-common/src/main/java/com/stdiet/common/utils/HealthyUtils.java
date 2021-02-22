package com.stdiet.common.utils;

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

    /**
     * 计算减脂每天最大摄入量（千卡）
     * @param age 年龄
     * @param tall 身高
     * @param weight 体重
     * @return
     */
    public static long calculateMaxHeatEveryDay(Integer age, Integer tall, Double weight){
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
    public static double calculateHeatRateByWeight(long metabolizeHeat, double weight){
        return NumberUtils.getNumberByRoundHalfUp(metabolizeHeat/weight*2, 2).doubleValue();
    }

    /**
     * 计算每公斤体重占比目标范围
     * @param heatRateByWeight 每公斤体重占比（千卡/公斤）
     * @return
     */
    public static double[] calculateHeatTargetRate(double heatRateByWeight){
        double[] heatArray = new double[2];
        heatArray[0] = heatRateByWeight - 10;
        heatArray[1] = heatRateByWeight - 5;
        return heatArray;
    }

    /**
     * 计算减脂热量控制范围(千卡）
     * @param heatTargetRateArray 每公斤体重占比目标范围
     * @param fatRateWeight 每公斤体重脂肪占比(克/公斤)
     * @return
     */
    public static long[] calculateStandardHeatScopeRate(double[] heatTargetRateArray, double fatRateWeight){
        long[] heatArray = new long[2];
        heatArray[0] = Math.round(heatTargetRateArray[0] * fatRateWeight / 2);
        heatArray[1] = Math.round(heatTargetRateArray[1] * fatRateWeight / 2);
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
        return (tall-107.5)*2;
    }

    /**
     * 返回蛋白质、脂肪、碳水对应热量、质量
     * @param metabolizeHeat
     * @return
     */
    public static Integer[][] calculateNutritionHeatAndQuality(int metabolizeHeat){
        Integer[] heatArray = new Integer[3];
        Integer[] qualityArray = new Integer[3];
       heatArray[0] = Math.round(nutritionRate[0] * metabolizeHeat /100);
       heatArray[1] = Math.round(nutritionRate[1] * metabolizeHeat /100);
       heatArray[2] = Math.round(nutritionRate[2] * metabolizeHeat /100);
       qualityArray[0] = (int)Math.round(Double.parseDouble(heatArray[0]+"")/proteinHeat);
       qualityArray[1] = (int)Math.round(Double.parseDouble(heatArray[1]+"")/fatHeat);
       qualityArray[2] = (int)Math.round(Double.parseDouble(heatArray[2]+"")/carbonWaterHeat);
       Integer[][] result = {heatArray, qualityArray};
       return result;
    }

}
