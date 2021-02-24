package com.stdiet.custom.service;

public interface AsyncCommonService {

    /**
     * 异步更新每个食材对应的蛋白质、脂肪、碳水的质量
     */
    public void updateFoodNutritionalQualityAndHeat(Long customerHeatId);
}
