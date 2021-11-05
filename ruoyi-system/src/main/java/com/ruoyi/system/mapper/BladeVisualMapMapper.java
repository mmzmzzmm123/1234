package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.BladeVisualMap;

/**
 * 可视化地图配置Mapper接口
 * 
 * @author yangsj
 * @date 2021-10-25
 */
public interface BladeVisualMapMapper 
{
    /**
     * 查询可视化地图配置
     * 
     * @param id 可视化地图配置主键
     * @return 可视化地图配置
     */
    public BladeVisualMap selectBladeVisualMapById(Long id);

    /**
     * 查询可视化地图配置列表
     * 
     * @param bladeVisualMap 可视化地图配置
     * @return 可视化地图配置集合
     */
    public List<BladeVisualMap> selectBladeVisualMapList(BladeVisualMap bladeVisualMap);

    /**
     * 新增可视化地图配置
     * 
     * @param bladeVisualMap 可视化地图配置
     * @return 结果
     */
    public int insertBladeVisualMap(BladeVisualMap bladeVisualMap);

    /**
     * 修改可视化地图配置
     * 
     * @param bladeVisualMap 可视化地图配置
     * @return 结果
     */
    public int updateBladeVisualMap(BladeVisualMap bladeVisualMap);

    /**
     * 删除可视化地图配置
     * 
     * @param id 可视化地图配置主键
     * @return 结果
     */
    public int deleteBladeVisualMapById(Long id);

    /**
     * 批量删除可视化地图配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBladeVisualMapByIds(Long[] ids);
}
