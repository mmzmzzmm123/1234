package com.stdiet.custom.service.impl;

import java.math.BigDecimal;
import java.util.List;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.HealthyUtils;
import com.stdiet.custom.domain.SysCustomerHealthy;
import com.stdiet.custom.domain.SysCustomerPhysicalSigns;
import com.stdiet.custom.domain.SysFoodHeatStatistics;
import com.stdiet.custom.dto.response.NutritionalCalories;
import com.stdiet.custom.service.ISysCustomerHealthyService;
import com.stdiet.custom.service.ISysCustomerPhysicalSignsService;
import com.stdiet.custom.service.ISysFoodHeatStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysCustomerHeatStatisticsMapper;
import com.stdiet.custom.domain.SysCustomerHeatStatistics;
import com.stdiet.custom.service.ISysCustomerHeatStatisticsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 外食热量统计Service业务层处理
 *
 * @author xzj
 * @date 2021-02-20
 */
@Service
@Transactional
public class SysCustomerHeatStatisticsServiceImpl implements ISysCustomerHeatStatisticsService
{
    @Autowired
    private SysCustomerHeatStatisticsMapper sysCustomerHeatStatisticsMapper;

    @Autowired
    private ISysFoodHeatStatisticsService sysFoodHeatStatisticsService;

    @Autowired
    private ISysCustomerPhysicalSignsService sysCustomerPhysicalSignsService;

    @Autowired
    private ISysCustomerHealthyService sysCustomerHealthyService;

    /**
     * 查询外食热量统计
     *
     * @param id 外食热量统计ID
     * @return 外食热量统计
     */
    @Override
    public SysCustomerHeatStatistics selectSysCustomerHeatStatisticsById(Long id)
    {
        return sysCustomerHeatStatisticsMapper.selectSysCustomerHeatStatisticsById(id);
    }

    /**
     * 查询外食热量统计列表
     *
     * @param sysCustomerHeatStatistics 外食热量统计
     * @return 外食热量统计
     */
    @Override
    public List<SysCustomerHeatStatistics> selectSysCustomerHeatStatisticsList(SysCustomerHeatStatistics sysCustomerHeatStatistics)
    {
        return sysCustomerHeatStatisticsMapper.selectSysCustomerHeatStatisticsList(sysCustomerHeatStatistics);
    }

    /**
     * 新增外食热量统计
     *
     * @param sysCustomerHeatStatistics 外食热量统计
     * @return 结果
     */
    @Override
    public int insertSysCustomerHeatStatistics(SysCustomerHeatStatistics sysCustomerHeatStatistics)
    {
        sysCustomerHeatStatistics.setCreateTime(DateUtils.getNowDate());
        return sysCustomerHeatStatisticsMapper.insertSysCustomerHeatStatistics(sysCustomerHeatStatistics);
    }

    /**
     * 修改外食热量统计
     *
     * @param sysCustomerHeatStatistics 外食热量统计
     * @return 结果
     */
    @Override
    public int updateSysCustomerHeatStatistics(SysCustomerHeatStatistics sysCustomerHeatStatistics)
    {
        sysCustomerHeatStatistics.setUpdateTime(DateUtils.getNowDate());
        return sysCustomerHeatStatisticsMapper.updateSysCustomerHeatStatistics(sysCustomerHeatStatistics);
    }

    /**
     * 批量删除外食热量统计
     *
     * @param ids 需要删除的外食热量统计ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomerHeatStatisticsByIds(Long[] ids)
    {
        return sysCustomerHeatStatisticsMapper.deleteSysCustomerHeatStatisticsByIds(ids);
    }

    /**
     * 删除外食热量统计信息
     *
     * @param id 外食热量统计ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomerHeatStatisticsById(Long id)
    {
        return sysCustomerHeatStatisticsMapper.deleteSysCustomerHeatStatisticsById(id);
    }

    /**
     * 根据日期查询是否客户热量统计
     * @param sysCustomerHeatStatistics
     * @return
     */
    public SysCustomerHeatStatistics getCustomerHeatStatisticsByDate(SysCustomerHeatStatistics sysCustomerHeatStatistics){
        return sysCustomerHeatStatisticsMapper.getCustomerHeatStatisticsByDate(sysCustomerHeatStatistics);
    }

    /**
     * 更新食材热量并计算当天总热量
     * @param sysCustomerHeatStatistics
     * @return
     */
    @Override
    public int calculateCustomerHeat(SysCustomerHeatStatistics sysCustomerHeatStatistics){
        Long[] foodHeatId = sysCustomerHeatStatistics.getFoodHeatIdList();
        Integer[] foodHeat = sysCustomerHeatStatistics.getFoodHeatList();
        if(foodHeatId != null && foodHeatId.length > 0){
            SysFoodHeatStatistics sysFoodHeatStatistics = new SysFoodHeatStatistics();
            int totalHeatCalue = 0;
            for (int i = 0; i < foodHeatId.length; i++) {
                sysFoodHeatStatistics.setId(foodHeatId[i]);
                sysFoodHeatStatistics.setProteinQuality(sysCustomerHeatStatistics.getProteinQualityList()[i]);
                sysFoodHeatStatistics.setFatQuality(sysCustomerHeatStatistics.getFatQualityList()[i]);
                sysFoodHeatStatistics.setCarbonWaterQuality(sysCustomerHeatStatistics.getCarbonWaterQualityList()[i]);
                //根据蛋白质、脂肪、碳水计算热量
                sysFoodHeatStatistics.setHeatValue(HealthyUtils.calculateTotalHeatByProteinFatCarbonWater(sysCustomerHeatStatistics.getProteinQualityList()[i],
                        sysCustomerHeatStatistics.getFatQualityList()[i], sysCustomerHeatStatistics.getCarbonWaterQualityList()[i]));
                sysFoodHeatStatisticsService.updateSysFoodHeatStatistics(sysFoodHeatStatistics);
                totalHeatCalue += sysFoodHeatStatistics.getHeatValue();
            }
            sysCustomerHeatStatistics.setHeatValue(totalHeatCalue);
            sysCustomerHeatStatistics.setHeatGap(sysCustomerHeatStatistics.getMaxHeatValue() - totalHeatCalue);
            return sysCustomerHeatStatisticsMapper.updateSysCustomerHeatStatistics(sysCustomerHeatStatistics);
        }
        return 0;
    }

    /**
     * 根据客户热量食材统计ID查询详情
     * @param id
     * @return
     */
    public NutritionalCalories getNutritionalCaloriesByCustomer(Long id){
        NutritionalCalories nutritionalCalories = new NutritionalCalories();
        SysCustomerHeatStatistics sysCustomerHeatStatistics = sysCustomerHeatStatisticsMapper.selectSysCustomerHeatStatisticsById(id);
        if(sysCustomerHeatStatistics != null){
            SysCustomerHealthy sysCustomerHealthy = getSysCustomerHealthy(sysCustomerHeatStatistics.getCustomerId());
            if(sysCustomerHealthy != null){
                nutritionalCalories.setName(sysCustomerHealthy.getName());
                nutritionalCalories.setAge(sysCustomerHealthy.getAge().intValue());
                nutritionalCalories.setTall(sysCustomerHealthy.getTall());
                nutritionalCalories.setWeight(sysCustomerHealthy.getWeight().doubleValue());
                nutritionalCalories.setStandardWeight(HealthyUtils.calculateStandardWeight(nutritionalCalories.getTall()));
                double overHeight = nutritionalCalories.getWeight() - nutritionalCalories.getStandardWeight();
                overHeight = overHeight > 0 ? overHeight : 0;
                nutritionalCalories.setOverWeight(overHeight);
                nutritionalCalories.setMetabolizeHeat(HealthyUtils.calculateMetabolizeHeat(nutritionalCalories.getAge(), nutritionalCalories.getTall(), nutritionalCalories.getWeight()).intValue());
                nutritionalCalories.setMaxIntakeHeat(sysCustomerHeatStatistics.getMaxHeatValue());
                nutritionalCalories.setEveryWeightHeat(HealthyUtils.calculateHeatRateByWeight(nutritionalCalories.getMetabolizeHeat(), nutritionalCalories.getWeight()));
                nutritionalCalories.setTargetEveryWeightHeat(HealthyUtils.calculateHeatTargetRate(nutritionalCalories.getEveryWeightHeat()));
                //nutritionalCalories.setStandardEveryWeightHeat(HealthyUtils.calculateHeatTargetRate() );
                nutritionalCalories.setNutritionalRate(HealthyUtils.nutritionRate);
                Integer[][] nutritionalHeatAndQuality = HealthyUtils.calculateNutritionHeatAndQuality(nutritionalCalories.getMetabolizeHeat());
                nutritionalCalories.setNutritionalHeat(nutritionalHeatAndQuality[0]);
                nutritionalCalories.setNutritionalQuality(nutritionalHeatAndQuality[1]);
            }
        }
        return nutritionalCalories;
    }

    /**
     * 根据用户ID查询该用户每天最大摄入量
     * @param customerId
     * @return
     */
    private SysCustomerHealthy getSysCustomerHealthy(Long customerId){
        SysCustomerHealthy sysCustomerHealthy = sysCustomerHealthyService.selectSysCustomerHealthyByCustomerId(customerId);
        if(sysCustomerHealthy != null){
            return sysCustomerHealthy;
        }
        //查询体征信息
        SysCustomerPhysicalSigns sysCustomerPhysicalSigns = sysCustomerPhysicalSignsService.selectSysCustomerPhysicalSignsByCusId(customerId);
        if(sysCustomerPhysicalSigns != null){
            sysCustomerHealthy = new SysCustomerHealthy();
            sysCustomerHealthy.setName(sysCustomerPhysicalSigns.getName());
            sysCustomerHealthy.setTall(sysCustomerPhysicalSigns.getTall());
            sysCustomerHealthy.setAge(sysCustomerPhysicalSigns.getAge().longValue());
            sysCustomerHealthy.setWeight(BigDecimal.valueOf(sysCustomerPhysicalSigns.getWeight()));
        }
        return sysCustomerHealthy;
    }


}