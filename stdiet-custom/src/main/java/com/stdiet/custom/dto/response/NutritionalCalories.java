package com.stdiet.custom.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class NutritionalCalories implements Serializable {

    private static final long serialVersionUID = 1L;

    //姓名
    public String name;

    //实际体重（斤）
    public double weight;

    //实际身高(厘米)
    public int tall;

    //年龄
    public int age;

    //标准体重（斤）
    public double standardWeight;

    //超重（斤）
    public double overWeight;

    //活动因子
    public double activityFactor;

    //基础代谢BMR(千卡）
    public int metabolizeHeat;

    //减脂最大摄入量(千卡）
    public int maxIntakeHeat;

    //不运动总热量(千卡）
    public int withoutExerciseHeat;

    //运动总热量(千卡）
    public int exerciseHeat;

    //每公斤体重占比(千卡/公斤）
    public double everyWeightHeat;

    //目标范围（千卡/公斤）
    public double[] targetEveryWeightHeat;

    //减脂热量标准范围（千卡/公斤）
    public double[] standardEveryWeightHeat;

    //蛋白质、脂肪、碳水比例
    public Integer[] nutritionalRate;

    //蛋白质、脂肪、碳水对应热量（千卡）
    public Integer[]  nutritionalHeat;

    //蛋白质、脂肪、碳水对应质量(克)
    public Integer[] nutritionalQuality;

    //每公斤体重对应蛋白质、脂肪、碳水占比(克/公斤)
    public double[] weightNutritionalRate;

    //蛋白质、脂肪、碳水已摄入热量（千卡）
    public Integer[] ingestedNutritionalHeat;

    //蛋白质、脂肪、碳水剩余可摄入热量
    public Integer[] surplusNutritionalHeat;

}
