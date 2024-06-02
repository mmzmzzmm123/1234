package com.baoli.sysmanage.mapper;

import java.util.List;
import com.baoli.sysmanage.domain.BlSysCities;

/**
 * 行政区域地州市信息Mapper接口
 * 
 * @author niujs
 * @date 2024-04-07
 */
public interface BlSysCitiesMapper 
{
    /**
     * 查询行政区域地州市信息
     * 
     * @param id 行政区域地州市信息主键
     * @return 行政区域地州市信息
     */
    public BlSysCities selectBlSysCitiesById(Long id);

    /**
     * 查询行政区域地州市信息列表
     * 
     * @param blSysCities 行政区域地州市信息
     * @return 行政区域地州市信息集合
     */
    public List<BlSysCities> selectBlSysCitiesList(BlSysCities blSysCities);

    /**
     * 新增行政区域地州市信息
     * 
     * @param blSysCities 行政区域地州市信息
     * @return 结果
     */
    public int insertBlSysCities(BlSysCities blSysCities);

    /**
     * 修改行政区域地州市信息
     * 
     * @param blSysCities 行政区域地州市信息
     * @return 结果
     */
    public int updateBlSysCities(BlSysCities blSysCities);

    /**
     * 删除行政区域地州市信息
     * 
     * @param id 行政区域地州市信息主键
     * @return 结果
     */
    public int deleteBlSysCitiesById(Long id);

    /**
     * 批量删除行政区域地州市信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBlSysCitiesByIds(Long[] ids);
}
