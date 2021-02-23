package com.stdiet.custom.service.impl;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.HealthyUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.sign.AesUtils;
import com.stdiet.custom.domain.*;
import com.stdiet.custom.dto.request.FoodHeatCalculatorRequest;
import com.stdiet.custom.mapper.SysCustomerHeatStatisticsMapper;
import com.stdiet.custom.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysFoodHeatStatisticsMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;

/**
 * 外食热量统计Service业务层处理
 *
 * @author xzj
 * @date 2021-02-19
 */
@Service
@Transactional
public class SysFoodHeatStatisticsServiceImpl implements ISysFoodHeatStatisticsService
{
    @Autowired
    private SysFoodHeatStatisticsMapper sysFoodHeatStatisticsMapper;

    @Autowired
    private SysCustomerHeatStatisticsMapper sysCustomerHeatStatisticsMapper;

    @Autowired
    private ISysCustomerPhysicalSignsService sysCustomerPhysicalSignsService;

    @Autowired
    private ISysCustomerHealthyService sysCustomerHealthyService;

    @Autowired
    private AsyncCommonService asyncCommonService;

    /**
     * 查询外食热量统计
     *
     * @param id 外食热量统计ID
     * @return 外食热量统计
     */
    @Override
    public SysFoodHeatStatistics selectSysFoodHeatStatisticsById(Long id)
    {
        return sysFoodHeatStatisticsMapper.selectSysFoodHeatStatisticsById(id);
    }

    /**
     * 查询外食热量统计列表
     *
     * @param sysFoodHeatStatistics 外食热量统计
     * @return 外食热量统计
     */
    @Override
    public List<SysFoodHeatStatistics> selectSysFoodHeatStatisticsList(SysFoodHeatStatistics sysFoodHeatStatistics)
    {
        return sysFoodHeatStatisticsMapper.selectSysFoodHeatStatisticsList(sysFoodHeatStatistics);
    }

    /**
     * 新增外食热量统计
     *
     * @param sysFoodHeatStatistics 外食热量统计
     * @return 结果
     */
    @Override
    public int insertSysFoodHeatStatistics(SysFoodHeatStatistics sysFoodHeatStatistics)
    {
        sysFoodHeatStatistics.setCreateTime(DateUtils.getNowDate());
        return sysFoodHeatStatisticsMapper.insertSysFoodHeatStatistics(sysFoodHeatStatistics);
    }

    /**
     * 修改外食热量统计
     *
     * @param sysFoodHeatStatistics 外食热量统计
     * @return 结果
     */
    @Override
    public int updateSysFoodHeatStatistics(SysFoodHeatStatistics sysFoodHeatStatistics)
    {
        sysFoodHeatStatistics.setUpdateTime(DateUtils.getNowDate());
        return sysFoodHeatStatisticsMapper.updateSysFoodHeatStatistics(sysFoodHeatStatistics);
    }

    /**
     * 批量删除外食热量统计
     *
     * @param ids 需要删除的外食热量统计ID
     * @return 结果
     */
    @Override
    public int deleteSysFoodHeatStatisticsByIds(Long[] ids)
    {
        return sysFoodHeatStatisticsMapper.deleteSysFoodHeatStatisticsByIds(ids);
    }

    /**
     * 删除外食热量统计信息
     *
     * @param id 外食热量统计ID
     * @return 结果
     */
    @Override
    public int deleteSysFoodHeatStatisticsById(Long id)
    {
        return sysFoodHeatStatisticsMapper.deleteSysFoodHeatStatisticsById(id);
    }

    /**
     * 客户自己添加外食计算数据，批量添加
     * @param foodHeatCalculatorRequest
     * @return
     */
    @Override
    public int addMuchFoodHeat(FoodHeatCalculatorRequest foodHeatCalculatorRequest){
        //客户ID解密
        String customerId = StringUtils.isNotEmpty(foodHeatCalculatorRequest.getCustomerEncId()) ? AesUtils.decrypt(foodHeatCalculatorRequest.getCustomerEncId(), null) : "";
        if(StringUtils.isEmpty(customerId) || StringUtils.isEmpty(foodHeatCalculatorRequest.getIngredientArray())){
            return 0;
        }
        List<HashMap> foodHeatList = JSON.parseArray(foodHeatCalculatorRequest.getIngredientArray(), HashMap.class);
        if(foodHeatList == null || foodHeatList.size() == 0){
            return 0;
        }
        Map<String, List<HashMap>> dateFoodMap = new HashMap<>();
        //根据日期分类
        for(HashMap map : foodHeatList){
            String edibleDate = map.get("edibleDate").toString();
            if(dateFoodMap.containsKey(edibleDate)){
                dateFoodMap.get(edibleDate).add(map);
            }else{
                List<HashMap> list = new ArrayList<>();
                list.add(map);
                dateFoodMap.put(edibleDate, list);
            }
        }
        int row = 0;
        int maxHeatValue = getMaxHeatValue(Long.parseLong(customerId)).intValue();
        for (String dateKey : dateFoodMap.keySet()) {
            //先判断该日期下是否已存在
            SysCustomerHeatStatistics sysCustomerHeatStatistics = new SysCustomerHeatStatistics();
            sysCustomerHeatStatistics.setCustomerId(Long.parseLong(customerId));
            sysCustomerHeatStatistics.setEdibleDate(DateUtils.parseDate(dateKey));
            SysCustomerHeatStatistics customerHeatResult = sysCustomerHeatStatisticsMapper.getCustomerHeatStatisticsByDate(sysCustomerHeatStatistics);
            if(customerHeatResult == null){
                sysCustomerHeatStatistics.setMaxHeatValue(maxHeatValue);
                sysCustomerHeatStatisticsMapper.insertSysCustomerHeatStatistics(sysCustomerHeatStatistics);
            }else{
                sysCustomerHeatStatistics.setId(customerHeatResult.getId());
            }
            if(sysCustomerHeatStatistics.getId() != null){
                for(HashMap map : dateFoodMap.get(dateKey)){
                    map.put("customerHeatId", sysCustomerHeatStatistics.getId());
                    map.put("number", map.get("number") != null && "".equals(map.get("number").toString().trim()) ? null : map.get("number"));
                    map.put("unit", map.get("unit") != null && "".equals(map.get("unit").toString().trim()) ? null : map.get("unit"));
                    map.put("quantity", map.get("quantity") != null && "".equals(map.get("quantity").toString().trim()) ? null : map.get("quantity"));
                }
                row = sysFoodHeatStatisticsMapper.insertFoodHeatBatch(dateFoodMap.get(dateKey));
                //异步查询食材对应蛋白质、脂肪、碳水热量、质量，并更新热量缺口
                asyncCommonService.updateFoodNutritionalQualityAndHeat(sysCustomerHeatStatistics.getId());
            }
        }
        return row;
    }

    /**
     * 根据用户ID查询该用户每天最大摄入量
     * @param customerId
     * @return
     */
    private Long getMaxHeatValue(Long customerId){
        SysCustomerHealthy sysCustomerHealthy = sysCustomerHealthyService.selectSysCustomerHealthyByCustomerId(customerId);
        if(sysCustomerHealthy != null){
            return HealthyUtils.calculateMaxHeatEveryDay(sysCustomerHealthy.getAge().intValue(),sysCustomerHealthy.getTall(),sysCustomerHealthy.getWeight().doubleValue());
        }
        //查询体征信息
        SysCustomerPhysicalSigns sysCustomerPhysicalSigns = sysCustomerPhysicalSignsService.selectSysCustomerPhysicalSignsByCusId(customerId);
        if(sysCustomerPhysicalSigns != null){
            return HealthyUtils.calculateMaxHeatEveryDay(sysCustomerPhysicalSigns.getAge().intValue(),sysCustomerPhysicalSigns.getTall(),sysCustomerPhysicalSigns.getWeight().doubleValue());
        }
        return 0L;
    }
}