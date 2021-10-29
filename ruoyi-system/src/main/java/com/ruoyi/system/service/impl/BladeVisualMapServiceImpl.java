package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BladeVisualMapMapper;
import com.ruoyi.system.domain.BladeVisualMap;
import com.ruoyi.system.service.IBladeVisualMapService;

/**
 * 可视化地图配置Service业务层处理
 * 
 * @author yangsj
 * @date 2021-10-25
 */
@Service
public class BladeVisualMapServiceImpl implements IBladeVisualMapService 
{
    @Autowired
    private BladeVisualMapMapper bladeVisualMapMapper;

    /**
     * 查询可视化地图配置
     * 
     * @param id 可视化地图配置主键
     * @return 可视化地图配置
     */
    @Override
    public BladeVisualMap selectBladeVisualMapById(Long id)
    {
        return bladeVisualMapMapper.selectBladeVisualMapById(id);
    }

    /**
     * 查询可视化地图配置列表
     * 
     * @param bladeVisualMap 可视化地图配置
     * @return 可视化地图配置
     */
    @Override
    public List<BladeVisualMap> selectBladeVisualMapList(BladeVisualMap bladeVisualMap)
    {
        return bladeVisualMapMapper.selectBladeVisualMapList(bladeVisualMap);
    }

    /**
     * 新增可视化地图配置
     * 
     * @param bladeVisualMap 可视化地图配置
     * @return 结果
     */
    @Override
    public int insertBladeVisualMap(BladeVisualMap bladeVisualMap)
    {
        return bladeVisualMapMapper.insertBladeVisualMap(bladeVisualMap);
    }

    /**
     * 修改可视化地图配置
     * 
     * @param bladeVisualMap 可视化地图配置
     * @return 结果
     */
    @Override
    public int updateBladeVisualMap(BladeVisualMap bladeVisualMap)
    {
        return bladeVisualMapMapper.updateBladeVisualMap(bladeVisualMap);
    }

    /**
     * 批量删除可视化地图配置
     * 
     * @param ids 需要删除的可视化地图配置主键
     * @return 结果
     */
    @Override
    public int deleteBladeVisualMapByIds(Long[] ids)
    {
        return bladeVisualMapMapper.deleteBladeVisualMapByIds(ids);
    }

    /**
     * 删除可视化地图配置信息
     * 
     * @param id 可视化地图配置主键
     * @return 结果
     */
    @Override
    public int deleteBladeVisualMapById(Long id)
    {
        return bladeVisualMapMapper.deleteBladeVisualMapById(id);
    }
}
