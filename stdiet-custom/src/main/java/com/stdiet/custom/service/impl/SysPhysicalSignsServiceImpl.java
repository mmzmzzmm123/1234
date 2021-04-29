package com.stdiet.custom.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysPhysicalSignsMapper;
import com.stdiet.custom.domain.SysPhysicalSigns;
import com.stdiet.custom.service.ISysPhysicalSignsService;

/**
 * 体征Service业务层处理
 *
 * @author wonder
 * @date 2020-12-23
 */
@Service
public class SysPhysicalSignsServiceImpl implements ISysPhysicalSignsService
{
    @Autowired
    private SysPhysicalSignsMapper sysPhysicalSignsMapper;

    /**
     * 查询体征
     *
     * @param id 体征ID
     * @return 体征
     */
    @Override
    public SysPhysicalSigns selectSysPhysicalSignsById(Long id)
    {
        return sysPhysicalSignsMapper.selectSysPhysicalSignsById(id);
    }

    /**
     * 查询体征列表
     *
     * @param sysPhysicalSigns 体征
     * @return 体征
     */
    @Override
    public List<SysPhysicalSigns> selectSysPhysicalSignsList(SysPhysicalSigns sysPhysicalSigns)
    {
        return sysPhysicalSignsMapper.selectSysPhysicalSignsList(sysPhysicalSigns);
    }

    /**
     * 新增体征
     *
     * @param sysPhysicalSigns 体征
     * @return 结果
     */
    @Override
    public int insertSysPhysicalSigns(SysPhysicalSigns sysPhysicalSigns)
    {
        return sysPhysicalSignsMapper.insertSysPhysicalSigns(sysPhysicalSigns);
    }

    /**
     * 修改体征
     *
     * @param sysPhysicalSigns 体征
     * @return 结果
     */
    @Override
    public int updateSysPhysicalSigns(SysPhysicalSigns sysPhysicalSigns)
    {
        return sysPhysicalSignsMapper.updateSysPhysicalSigns(sysPhysicalSigns);
    }

    /**
     * 批量删除体征
     *
     * @param ids 需要删除的体征ID
     * @return 结果
     */
    @Override
    public int deleteSysPhysicalSignsByIds(Long[] ids)
    {
        return sysPhysicalSignsMapper.deleteSysPhysicalSignsByIds(ids);
    }

    /**
     * 删除体征信息
     *
     * @param id 体征ID
     * @return 结果
     */
    @Override
    public int deleteSysPhysicalSignsById(Long id)
    {
        return sysPhysicalSignsMapper.deleteSysPhysicalSignsById(id);
    }

    /**
     * 根据名称查询体征ID集合
     * @param name
     * @return
     */
    @Override
    public List<Long> getSignIdByName(String name){
        return sysPhysicalSignsMapper.getSignIdByName(name);
    }
}