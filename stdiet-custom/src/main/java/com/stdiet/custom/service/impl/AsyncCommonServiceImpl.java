package com.stdiet.custom.service.impl;

import com.stdiet.common.utils.HealthyUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.SysCustomerHeatStatistics;
import com.stdiet.custom.domain.SysFoodHeatStatistics;
import com.stdiet.custom.domain.SysIngredient;
import com.stdiet.custom.mapper.SysCustomerHeatStatisticsMapper;
import com.stdiet.custom.mapper.SysFoodHeatStatisticsMapper;
import com.stdiet.custom.service.AsyncCommonService;
import com.stdiet.custom.service.ISysIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsyncCommonServiceImpl implements AsyncCommonService {

    @Autowired
    private ISysIngredientService sysIngredientService;

    @Autowired
    private SysCustomerHeatStatisticsMapper sysCustomerHeatStatisticsMapper;

    @Autowired
    private SysFoodHeatStatisticsMapper sysFoodHeatStatisticsMapper;


    /**
     * 异步更新每个食材对应的蛋白质、脂肪、碳水的质量
     */
    @Override
    @Async
    public void updateFoodNutritionalQualityAndHeat(Long customerHeatId){
        SysCustomerHeatStatistics sysCustomerHeatStatistics = sysCustomerHeatStatisticsMapper.selectSysCustomerHeatStatisticsById(customerHeatId);
        if(sysCustomerHeatStatistics != null && sysCustomerHeatStatistics.getFoodHeatStatisticsList() != null
                && sysCustomerHeatStatistics.getFoodHeatStatisticsList().size() > 0){
            List<SysFoodHeatStatistics> foodList = sysCustomerHeatStatistics.getFoodHeatStatisticsList();
            //食材是否全部存在食材库，并且存在对应比例
            int totalProteinHeat = 0;
            int totalFatHeat = 0;
            int totalCarbonWaterHeat = 0;
            boolean allExistFlag = true;
            for (SysFoodHeatStatistics sysFoodHeatStatistics : foodList) {
                //判断食材库中是否存在该食材以及该食材是否存在质量
                if(sysFoodHeatStatistics.getQuantity() == null || StringUtils.isEmpty(sysFoodHeatStatistics.getIngredient())){
                    allExistFlag = false;
                    continue;
                }
                //如果该食材之前未计算出热量则从食材库中查询并查询计算
                if(sysFoodHeatStatistics.getHeatValue() == null || sysFoodHeatStatistics.getHeatValue().intValue() == 0){
                    SysIngredient sysIngredient = sysIngredientService.selectSysIngredientByName(sysFoodHeatStatistics.getIngredient().trim());
                    if(sysIngredient == null || sysIngredient.getProteinRatio() == null || sysIngredient.getFatRatio() == null || sysIngredient.getCarbonRatio() == null){
                        allExistFlag = false;
                        continue;
                    }
                    Long proteinValue = Math.round(sysFoodHeatStatistics.getQuantity()*sysIngredient.getProteinRatio().doubleValue()/100);
                    Long fatValue = Math.round(sysFoodHeatStatistics.getQuantity()*sysIngredient.getFatRatio().doubleValue()/100);
                    Long carbonValue = Math.round(sysFoodHeatStatistics.getQuantity()*sysIngredient.getCarbonRatio().doubleValue()/100);
                    sysFoodHeatStatistics.setProteinQuality(proteinValue.intValue());
                    sysFoodHeatStatistics.setFatQuality(fatValue.intValue());
                    sysFoodHeatStatistics.setCarbonWaterQuality(carbonValue.intValue());
                    //根据蛋白质、脂肪、碳水计算热量
                    sysFoodHeatStatistics.setProteinHeat(HealthyUtils.calculateHeatByProteinQuality(proteinValue.intValue()));
                    sysFoodHeatStatistics.setFatHeat(HealthyUtils.calculateHeatByFatQuality(fatValue.intValue()));
                    sysFoodHeatStatistics.setCarbonWaterHeat(HealthyUtils.calculateHeatByCarbonWaterQuality(carbonValue.intValue()));
                    sysFoodHeatStatistics.setHeatValue(sysFoodHeatStatistics.getProteinHeat()+sysFoodHeatStatistics.getFatHeat()+sysFoodHeatStatistics.getCarbonWaterHeat());
                    sysFoodHeatStatisticsMapper.updateSysFoodHeatStatistics(sysFoodHeatStatistics);
                }else{
                    //根据蛋白质、脂肪、碳水计算热量
                    sysFoodHeatStatistics.setProteinHeat(HealthyUtils.calculateHeatByProteinQuality(sysFoodHeatStatistics.getProteinQuality()));
                    sysFoodHeatStatistics.setFatHeat(HealthyUtils.calculateHeatByFatQuality(sysFoodHeatStatistics.getFatQuality()));
                    sysFoodHeatStatistics.setCarbonWaterHeat(HealthyUtils.calculateHeatByCarbonWaterQuality(sysFoodHeatStatistics.getCarbonWaterQuality()));
                }
                totalProteinHeat += sysFoodHeatStatistics.getProteinHeat();
                totalFatHeat += sysFoodHeatStatistics.getFatHeat();
                totalCarbonWaterHeat += sysFoodHeatStatistics.getCarbonWaterHeat();
            }
            //是否全部食材都存在于食材库
            if(allExistFlag){
                sysCustomerHeatStatistics.setHeatValue(totalProteinHeat + totalFatHeat + totalCarbonWaterHeat);
                sysCustomerHeatStatistics.setProteinHeat(totalProteinHeat);
                sysCustomerHeatStatistics.setFatHeat(totalFatHeat);
                sysCustomerHeatStatistics.setCarbonWaterHeat(totalCarbonWaterHeat);
                sysCustomerHeatStatistics.setHeatGap(sysCustomerHeatStatistics.getMaxHeatValue() - sysCustomerHeatStatistics.getHeatValue());
                sysCustomerHeatStatisticsMapper.updateSysCustomerHeatStatistics(sysCustomerHeatStatistics);
            }else{
                //新添加的食材如果不存在食材库，需要将热量数据更新为0，需要重新计算
                if(sysCustomerHeatStatistics.getHeatValue() != null && sysCustomerHeatStatistics.getHeatValue().intValue() > 0){
                    sysCustomerHeatStatistics.setHeatValue(0);
                    sysCustomerHeatStatistics.setProteinHeat(0);
                    sysCustomerHeatStatistics.setFatHeat(0);
                    sysCustomerHeatStatistics.setCarbonWaterHeat(0);
                    sysCustomerHeatStatistics.setHeatGap(sysCustomerHeatStatistics.getMaxHeatValue() - sysCustomerHeatStatistics.getHeatValue());
                    sysCustomerHeatStatisticsMapper.updateSysCustomerHeatStatistics(sysCustomerHeatStatistics);
                }
            }
        }
    }
}
