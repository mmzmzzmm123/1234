package com.gox.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gox.system.mapper.ConfigMapper;
import com.gox.system.domain.form.Config;
import com.gox.system.service.IConfigService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author gox
 * @date 2021-02-02
 */
@Service
public class ConfigServiceImpl implements IConfigService 
{
    @Autowired
    private ConfigMapper configMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public Config selectConfigById(Long id)
    {
        return configMapper.selectConfigById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param config 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Config> selectConfigList(Config config)
    {
        return configMapper.selectConfigList(config);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param config 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertConfig(Config config)
    {
        return configMapper.insertConfig(config);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param config 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateConfig(Config config)
    {
        return configMapper.updateConfig(config);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteConfigByIds(Long[] ids)
    {
        return configMapper.deleteConfigByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteConfigById(Long id)
    {
        return configMapper.deleteConfigById(id);
    }
}
