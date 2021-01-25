package com.gox.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gox.system.mapper.ArchivalCodeSettingMapper;
import com.gox.system.domain.ArchivalCodeSetting;
import com.gox.system.service.IArchivalCodeSettingService;

/**
 * 档号设置Service业务层处理
 * 
 * @author gox
 * @date 2021-01-23
 */
@Service
public class ArchivalCodeSettingServiceImpl implements IArchivalCodeSettingService 
{
    @Autowired
    private ArchivalCodeSettingMapper archivalCodeSettingMapper;

    /**
     * 查询档号设置
     * 
     * @param id 档号设置ID
     * @return 档号设置
     */
    @Override
    public ArchivalCodeSetting selectArchivalCodeSettingById(Long id)
    {
        return archivalCodeSettingMapper.selectArchivalCodeSettingById(id);
    }

    /**
     * 查询档号设置列表
     * 
     * @param archivalCodeSetting 档号设置
     * @return 档号设置
     */
    @Override
    public List<ArchivalCodeSetting> selectArchivalCodeSettingList(ArchivalCodeSetting archivalCodeSetting)
    {
        return archivalCodeSettingMapper.selectArchivalCodeSettingList(archivalCodeSetting);
    }

    /**
     * 新增档号设置
     * 
     * @param archivalCodeSetting 档号设置
     * @return 结果
     */
    @Override
    public int insertArchivalCodeSetting(ArchivalCodeSetting archivalCodeSetting)
    {
        return archivalCodeSettingMapper.insertArchivalCodeSetting(archivalCodeSetting);
    }

    /**
     * 修改档号设置
     * 
     * @param archivalCodeSetting 档号设置
     * @return 结果
     */
    @Override
    public int updateArchivalCodeSetting(ArchivalCodeSetting archivalCodeSetting)
    {
        return archivalCodeSettingMapper.updateArchivalCodeSetting(archivalCodeSetting);
    }

    /**
     * 批量删除档号设置
     * 
     * @param ids 需要删除的档号设置ID
     * @return 结果
     */
    @Override
    public int deleteArchivalCodeSettingByIds(Long[] ids)
    {
        return archivalCodeSettingMapper.deleteArchivalCodeSettingByIds(ids);
    }

    /**
     * 删除档号设置信息
     * 
     * @param id 档号设置ID
     * @return 结果
     */
    @Override
    public int deleteArchivalCodeSettingById(Long id)
    {
        return archivalCodeSettingMapper.deleteArchivalCodeSettingById(id);
    }
}
