package com.jlt.csa.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlt.csa.mapper.GardenLogMapper;
import com.jlt.csa.domain.GardenLog;
import com.jlt.csa.service.IGardenLogService;

/**
 * 菜地日志Service业务层处理
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
@Service
public class GardenLogServiceImpl implements IGardenLogService 
{
    @Autowired
    private GardenLogMapper gardenLogMapper;

    /**
     * 查询菜地日志
     * 
     * @param id 菜地日志主键
     * @return 菜地日志
     */
    @Override
    public GardenLog selectGardenLogById(Long id)
    {
        return gardenLogMapper.selectGardenLogById(id);
    }

    /**
     * 查询菜地日志列表
     * 
     * @param gardenLog 菜地日志
     * @return 菜地日志
     */
    @Override
    public List<GardenLog> selectGardenLogList(GardenLog gardenLog)
    {
        return gardenLogMapper.selectGardenLogList(gardenLog);
    }

    /**
     * 新增菜地日志
     * 
     * @param gardenLog 菜地日志
     * @return 结果
     */
    @Override
    public int insertGardenLog(GardenLog gardenLog)
    {
        gardenLog.setCreateTime(DateUtils.getNowDate());
        return gardenLogMapper.insertGardenLog(gardenLog);
    }

    /**
     * 修改菜地日志
     * 
     * @param gardenLog 菜地日志
     * @return 结果
     */
    @Override
    public int updateGardenLog(GardenLog gardenLog)
    {
        gardenLog.setUpdateTime(DateUtils.getNowDate());
        return gardenLogMapper.updateGardenLog(gardenLog);
    }

    /**
     * 批量删除菜地日志
     * 
     * @param ids 需要删除的菜地日志主键
     * @return 结果
     */
    @Override
    public int deleteGardenLogByIds(Long[] ids)
    {
        return gardenLogMapper.deleteGardenLogByIds(ids);
    }

    /**
     * 删除菜地日志信息
     * 
     * @param id 菜地日志主键
     * @return 结果
     */
    @Override
    public int deleteGardenLogById(Long id)
    {
        return gardenLogMapper.deleteGardenLogById(id);
    }
}
