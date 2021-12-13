package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BladeVisualConfigMapper;
import com.ruoyi.system.domain.BladeVisualConfig;
import com.ruoyi.system.service.IBladeVisualConfigService;

/**
 * 可视化配置Service业务层处理
 * 
 * @author yangsj
 * @date 2021-10-25
 */
@Service
public class BladeVisualConfigServiceImpl implements IBladeVisualConfigService 
{
    @Autowired
    private BladeVisualConfigMapper bladeVisualConfigMapper;

    /**
     * 查询可视化配置
     * 
     * @param id 可视化配置主键
     * @return 可视化配置
     */
    @Override
    public BladeVisualConfig selectBladeVisualConfigById(Long id)
    {
        return bladeVisualConfigMapper.selectBladeVisualConfigById(id);
    }

    /**
     * 查询可视化配置列表
     * 
     * @param bladeVisualConfig 可视化配置
     * @return 可视化配置
     */
    @Override
    public List<BladeVisualConfig> selectBladeVisualConfigList(BladeVisualConfig bladeVisualConfig)
    {
        return bladeVisualConfigMapper.selectBladeVisualConfigList(bladeVisualConfig);
    }

    /**
     * 新增可视化配置
     * 
     * @param bladeVisualConfig 可视化配置
     * @return 结果
     */
    @Override
    public int insertBladeVisualConfig(BladeVisualConfig bladeVisualConfig)
    {
        return bladeVisualConfigMapper.insertBladeVisualConfig(bladeVisualConfig);
    }

    /**
     * 修改可视化配置
     * 
     * @param bladeVisualConfig 可视化配置
     * @return 结果
     */
    @Override
    public int updateBladeVisualConfig(BladeVisualConfig bladeVisualConfig)
    {
        return bladeVisualConfigMapper.updateBladeVisualConfig(bladeVisualConfig);
    }

    /**
     * 批量删除可视化配置
     * 
     * @param ids 需要删除的可视化配置主键
     * @return 结果
     */
    @Override
    public int deleteBladeVisualConfigByIds(Long[] ids)
    {
        return bladeVisualConfigMapper.deleteBladeVisualConfigByIds(ids);
    }

    /**
     * 删除可视化配置信息
     * 
     * @param id 可视化配置主键
     * @return 结果
     */
    @Override
    public int deleteBladeVisualConfigById(Long id)
    {
        return bladeVisualConfigMapper.deleteBladeVisualConfigById(id);
    }
}
