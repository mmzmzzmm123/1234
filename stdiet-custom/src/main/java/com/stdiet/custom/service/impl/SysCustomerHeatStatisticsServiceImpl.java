package com.stdiet.custom.service.impl;

import java.util.List;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.HealthyUtils;
import com.stdiet.custom.domain.SysCustomerHealthy;
import com.stdiet.custom.domain.SysCustomerPhysicalSigns;
import com.stdiet.custom.domain.SysFoodHeatStatistics;
import com.stdiet.custom.mapper.SysFoodHeatStatisticsMapper;
import com.stdiet.custom.service.ISysCustomerHealthyService;
import com.stdiet.custom.service.ISysCustomerPhysicalSignsService;
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
    private SysFoodHeatStatisticsMapper sysFoodHeatStatisticsMapper;

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
     * 更新食材热量并计算当天总热量
     * @param sysCustomerHeatStatistics
     * @return
     */
    @Override
    public int calculateCustomerHeat(SysCustomerHeatStatistics sysCustomerHeatStatistics){
        Long[] foodHeatId = sysCustomerHeatStatistics.getFoodHeatIdList();
        Integer[] foodHeat = sysCustomerHeatStatistics.getFoodHeatList();
        if(foodHeatId != null && foodHeatId.length > 0 && foodHeat != null && foodHeat.length == foodHeatId.length){
            SysFoodHeatStatistics sysFoodHeatStatistics = new SysFoodHeatStatistics();
            int totalHeatCalue = 0;
            for (int i = 0; i < foodHeatId.length; i++) {
                sysFoodHeatStatistics.setId(foodHeatId[i]);
                sysFoodHeatStatistics.setHeatValue(foodHeat[i]);
                sysFoodHeatStatisticsMapper.updateSysFoodHeatStatistics(sysFoodHeatStatistics);
                totalHeatCalue += foodHeat[i];
            }
            sysCustomerHeatStatistics.setHeatValue(totalHeatCalue);
            Long maxHeatValue = getMaxHeatValue(sysCustomerHeatStatistics.getCustomerId());
            sysCustomerHeatStatistics.setMaxHeatValue(maxHeatValue.intValue());
            sysCustomerHeatStatistics.setHeatGap(maxHeatValue.intValue() - totalHeatCalue);
            return sysCustomerHeatStatisticsMapper.updateSysCustomerHeatStatistics(sysCustomerHeatStatistics);
        }
        return 0;
    }

    public long getMaxHeatValue(Long customerId){
        SysCustomerHealthy sysCustomerHealthy = sysCustomerHealthyService.selectSysCustomerHealthyByCustomerId(customerId);
        if(sysCustomerHealthy != null){
            return HealthyUtils.calculateMaxHeatEveryDay(sysCustomerHealthy.getAge().intValue(),sysCustomerHealthy.getTall(),sysCustomerHealthy.getWeight().doubleValue());
        }
        //查询体征信息
        SysCustomerPhysicalSigns sysCustomerPhysicalSigns = sysCustomerPhysicalSignsService.selectSysCustomerPhysicalSignsByCusId(customerId);
        if(sysCustomerPhysicalSigns != null){
            return HealthyUtils.calculateMaxHeatEveryDay(sysCustomerPhysicalSigns.getAge().intValue(),sysCustomerPhysicalSigns.getTall(),sysCustomerPhysicalSigns.getWeight().doubleValue());
        }
        return 0;
    }
}