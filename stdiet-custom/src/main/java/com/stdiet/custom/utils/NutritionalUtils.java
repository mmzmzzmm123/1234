package com.stdiet.custom.utils;

import com.stdiet.common.utils.HealthyUtils;
import com.stdiet.common.utils.NumberUtils;
import com.stdiet.custom.domain.SysCustomerHealthy;
import com.stdiet.custom.dto.response.NutritionalCalories;

public class NutritionalUtils {

    /**
     * 根据客户健康信息获营养热量成分比例
     * @param sysCustomerHealthy
     * @return
     */
    public static final NutritionalCalories getNutritionalCaloriesData(SysCustomerHealthy sysCustomerHealthy){
        NutritionalCalories nutritionalCalories = new NutritionalCalories();
        if(sysCustomerHealthy != null){
            nutritionalCalories.setName(sysCustomerHealthy.getName());
            nutritionalCalories.setAge(sysCustomerHealthy.getAge().intValue());
            nutritionalCalories.setTall(sysCustomerHealthy.getTall());
            nutritionalCalories.setWeight(sysCustomerHealthy.getWeight().doubleValue());
            nutritionalCalories.setStandardWeight(HealthyUtils.calculateStandardWeight(nutritionalCalories.getTall()));
            double overHeight = NumberUtils.getNumberByRoundHalfUp(nutritionalCalories.getWeight() - nutritionalCalories.getStandardWeight(),1).doubleValue();
            overHeight = overHeight > 0 ? overHeight : 0;
            nutritionalCalories.setOverWeight(overHeight);
            nutritionalCalories.setMetabolizeHeat(HealthyUtils.calculateMetabolizeHeat(nutritionalCalories.getAge(), nutritionalCalories.getTall(), nutritionalCalories.getWeight()).intValue());
            nutritionalCalories.setMaxIntakeHeat(HealthyUtils.calculateMaxHeatEveryDay(sysCustomerHealthy.getAge().intValue(), sysCustomerHealthy.getTall(), sysCustomerHealthy.getWeight().doubleValue()));
            nutritionalCalories.setEveryWeightHeat(HealthyUtils.calculateHeatRateByWeight(nutritionalCalories.getMetabolizeHeat(), nutritionalCalories.getWeight()));
            nutritionalCalories.setTargetEveryWeightHeat(HealthyUtils.calculateHeatTargetRate(nutritionalCalories.getEveryWeightHeat()));
            nutritionalCalories.setStandardEveryWeightHeat(HealthyUtils.calculateStandardHeatScopeRate(nutritionalCalories.getTargetEveryWeightHeat(), nutritionalCalories.getWeight()));
            nutritionalCalories.setNutritionalRate(HealthyUtils.nutritionRate);
            Integer[][] nutritionalHeatAndQuality = HealthyUtils.calculateNutritionHeatAndQuality(nutritionalCalories.getMaxIntakeHeat().intValue());
            nutritionalCalories.setNutritionalHeat(nutritionalHeatAndQuality[0]);
            nutritionalCalories.setNutritionalQuality(nutritionalHeatAndQuality[1]);
            nutritionalCalories.setWeightNutritionalRate(HealthyUtils.calculateNutritionEveryWeight(nutritionalHeatAndQuality[1], nutritionalCalories.getWeight()));
            //活动因子
            nutritionalCalories.setActivityFactor(HealthyUtils.activityFactor);
            Long[] heatArray = HealthyUtils.calculateWithoutExerciseHeat(nutritionalCalories.getMetabolizeHeat());
            //不运动热量
            nutritionalCalories.setWithoutExerciseHeat(heatArray[0].intValue());
            //运动热量
            nutritionalCalories.setExerciseHeat(heatArray[1].intValue());
        }
        return nutritionalCalories;
    }
}
