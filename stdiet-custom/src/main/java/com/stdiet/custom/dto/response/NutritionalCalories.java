package com.stdiet.custom.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class NutritionalCalories implements Serializable {

    private static final long serialVersionUID = 1L;

    //姓名
    public String name;

    //实际体重（斤）
    public Double weight;

    //实际身高(厘米)
    public Integer tall;

    //年龄
    public Integer age;

    //标准体重（斤）
    public Double standardWeight;

    //超重（斤）
    public Double overWeight;

    //活动因子
    public Double activityFactor;

    //基础代谢BMR(千卡）
    public Integer metabolizeHeat;

    //减脂最大摄入量(千卡）
    public Long maxIntakeHeat;

    //不运动总热量(千卡）
    public Integer withoutExerciseHeat;

    //运动总热量(千卡）
    public Integer exerciseHeat;

    //每公斤体重占比(千卡/公斤）
    public Long everyWeightHeat;

    //目标范围（千卡/公斤）
    public Long[] targetEveryWeightHeat;

    //减脂热量标准范围（千卡/公斤）
    public Long[] standardEveryWeightHeat;

    //蛋白质、脂肪、碳水比例
    public Integer[] nutritionalRate;

    //蛋白质、脂肪、碳水对应热量（千卡）
    public Integer[]  nutritionalHeat;

    //蛋白质、脂肪、碳水对应质量(克)
    public Integer[] nutritionalQuality;

    //每公斤体重对应蛋白质、脂肪、碳水占比(克/公斤)
    public Double[] weightNutritionalRate;

    //蛋白质、脂肪、碳水已摄入热量（千卡）
    public Integer[] ingestedNutritionalHeat;

    //蛋白质、脂肪、碳水剩余可摄入热量
    public Integer[] surplusNutritionalHeat;


    private String nutritionalHeat_one;

    private String nutritionalHeat_two;

    private String nutritionalHeat_three;

    private String nutritionalQuality_one;

    private String nutritionalQuality_two;

    private String nutritionalQuality_three;

    private String weightNutritionalRate_one;

    private String weightNutritionalRate_two;

    private String weightNutritionalRate_three;

}
