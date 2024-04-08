package com.baoli.sysmanage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.sysmanage.mapper.BlSysCitiesMapper;
import com.baoli.sysmanage.domain.BlSysCities;
import com.baoli.sysmanage.service.IBlSysCitiesService;

/**
 * 行政区域地州市信息Service业务层处理
 * 
 * @author niujs
 * @date 2024-04-07
 */
@Service
public class BlSysCitiesServiceImpl implements IBlSysCitiesService 
{
    @Autowired
    private BlSysCitiesMapper blSysCitiesMapper;

    /**
     * 查询行政区域地州市信息
     * 
     * @param id 行政区域地州市信息主键
     * @return 行政区域地州市信息
     */
    @Override
    public BlSysCities selectBlSysCitiesById(Long id)
    {
        return blSysCitiesMapper.selectBlSysCitiesById(id);
    }

    /**
     * 查询行政区域地州市信息列表
     * 
     * @param blSysCities 行政区域地州市信息
     * @return 行政区域地州市信息
     */
    @Override
    public List<BlSysCities> selectBlSysCitiesList(BlSysCities blSysCities)
    {
        return blSysCitiesMapper.selectBlSysCitiesList(blSysCities);
    }

    /**
     * 新增行政区域地州市信息
     * 
     * @param blSysCities 行政区域地州市信息
     * @return 结果
     */
    @Override
    public int insertBlSysCities(BlSysCities blSysCities)
    {
        return blSysCitiesMapper.insertBlSysCities(blSysCities);
    }

    /**
     * 修改行政区域地州市信息
     * 
     * @param blSysCities 行政区域地州市信息
     * @return 结果
     */
    @Override
    public int updateBlSysCities(BlSysCities blSysCities)
    {
        return blSysCitiesMapper.updateBlSysCities(blSysCities);
    }

    /**
     * 批量删除行政区域地州市信息
     * 
     * @param ids 需要删除的行政区域地州市信息主键
     * @return 结果
     */
    @Override
    public int deleteBlSysCitiesByIds(Long[] ids)
    {
        return blSysCitiesMapper.deleteBlSysCitiesByIds(ids);
    }

    /**
     * 删除行政区域地州市信息信息
     * 
     * @param id 行政区域地州市信息主键
     * @return 结果
     */
    @Override
    public int deleteBlSysCitiesById(Long id)
    {
        return blSysCitiesMapper.deleteBlSysCitiesById(id);
    }
}
