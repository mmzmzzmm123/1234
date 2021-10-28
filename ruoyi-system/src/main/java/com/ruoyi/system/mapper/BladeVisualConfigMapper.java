package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.BladeVisualConfig;

/**
 * 可视化配置Mapper接口
 * 
 * @author yangsj
 * @date 2021-10-25
 */
public interface BladeVisualConfigMapper 
{
    /**
     * 查询可视化配置
     * 
     * @param id 可视化配置主键
     * @return 可视化配置
     */
    public BladeVisualConfig selectBladeVisualConfigById(Long id);

    /**
     * 查询可视化配置列表
     * 
     * @param bladeVisualConfig 可视化配置
     * @return 可视化配置集合
     */
    public List<BladeVisualConfig> selectBladeVisualConfigList(BladeVisualConfig bladeVisualConfig);

    /**
     * 新增可视化配置
     * 
     * @param bladeVisualConfig 可视化配置
     * @return 结果
     */
    public int insertBladeVisualConfig(BladeVisualConfig bladeVisualConfig);

    /**
     * 修改可视化配置
     * 
     * @param bladeVisualConfig 可视化配置
     * @return 结果
     */
    public int updateBladeVisualConfig(BladeVisualConfig bladeVisualConfig);

    /**
     * 删除可视化配置
     * 
     * @param id 可视化配置主键
     * @return 结果
     */
    public int deleteBladeVisualConfigById(Long id);

    /**
     * 批量删除可视化配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBladeVisualConfigByIds(Long[] ids);
}
