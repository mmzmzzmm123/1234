package com.baoli.sysmanage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.sysmanage.mapper.BlSysProvincesMapper;
import com.baoli.sysmanage.domain.BlSysProvinces;
import com.baoli.sysmanage.service.IBlSysProvincesService;

/**
 * 省份信息Service业务层处理
 * 
 * @author niujs
 * @date 2024-04-07
 */
@Service
public class BlSysProvincesServiceImpl implements IBlSysProvincesService 
{
    @Autowired
    private BlSysProvincesMapper blSysProvincesMapper;

    /**
     * 查询省份信息
     * 
     * @param id 省份信息主键
     * @return 省份信息
     */
    @Override
    public BlSysProvinces selectBlSysProvincesById(Long id)
    {
        return blSysProvincesMapper.selectBlSysProvincesById(id);
    }

    /**
     * 查询省份信息列表
     * 
     * @param blSysProvinces 省份信息
     * @return 省份信息
     */
    @Override
    public List<BlSysProvinces> selectBlSysProvincesList(BlSysProvinces blSysProvinces)
    {
        return blSysProvincesMapper.selectBlSysProvincesList(blSysProvinces);
    }

    /**
     * 新增省份信息
     * 
     * @param blSysProvinces 省份信息
     * @return 结果
     */
    @Override
    public int insertBlSysProvinces(BlSysProvinces blSysProvinces)
    {
        return blSysProvincesMapper.insertBlSysProvinces(blSysProvinces);
    }

    /**
     * 修改省份信息
     * 
     * @param blSysProvinces 省份信息
     * @return 结果
     */
    @Override
    public int updateBlSysProvinces(BlSysProvinces blSysProvinces)
    {
        return blSysProvincesMapper.updateBlSysProvinces(blSysProvinces);
    }

    /**
     * 批量删除省份信息
     * 
     * @param ids 需要删除的省份信息主键
     * @return 结果
     */
    @Override
    public int deleteBlSysProvincesByIds(Long[] ids)
    {
        return blSysProvincesMapper.deleteBlSysProvincesByIds(ids);
    }

    /**
     * 删除省份信息信息
     * 
     * @param id 省份信息主键
     * @return 结果
     */
    @Override
    public int deleteBlSysProvincesById(Long id)
    {
        return blSysProvincesMapper.deleteBlSysProvincesById(id);
    }
}
