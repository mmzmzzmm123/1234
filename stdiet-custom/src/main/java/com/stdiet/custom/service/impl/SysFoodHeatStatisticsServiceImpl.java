package com.stdiet.custom.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.sign.AesUtils;
import com.stdiet.custom.domain.SysCustomerHeatStatistics;
import com.stdiet.custom.dto.request.FoodHeatCalculatorRequest;
import com.stdiet.custom.mapper.SysCustomerHeatStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysFoodHeatStatisticsMapper;
import com.stdiet.custom.domain.SysFoodHeatStatistics;
import com.stdiet.custom.service.ISysFoodHeatStatisticsService;
import org.springframework.transaction.annotation.Transactional;

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
        if(StringUtils.isEmpty(customerId)){
            return 0;
        }
        //先判断该日期下是否已存在
        SysCustomerHeatStatistics sysCustomerHeatStatistics = new SysCustomerHeatStatistics();
        sysCustomerHeatStatistics.setCustomerId(Long.parseLong(customerId));
        sysCustomerHeatStatistics.setEdibleDate(new Date());
        SysCustomerHeatStatistics customerHeatResult = sysCustomerHeatStatisticsMapper.getCustomerHeatStatisticsByDate(sysCustomerHeatStatistics);
        if(customerHeatResult == null){
            sysCustomerHeatStatisticsMapper.insertSysCustomerHeatStatistics(sysCustomerHeatStatistics);
        }else{
            sysCustomerHeatStatistics.setId(customerHeatResult.getId());
        }
        if(sysCustomerHeatStatistics.getId() != null){
            List<SysFoodHeatStatistics> list = new ArrayList<>();
            if(StringUtils.isNotEmpty(foodHeatCalculatorRequest.getIngredientArray())){
                List<HashMap> foodHeatList = JSON.parseArray(foodHeatCalculatorRequest.getIngredientArray(), HashMap.class);
                for(HashMap map : foodHeatList){
                    map.put("customerHeatId", sysCustomerHeatStatistics.getId());
                }
                return sysFoodHeatStatisticsMapper.insertFoodHeatBatch(foodHeatList);
            }
        }
        return 0;
    }
}