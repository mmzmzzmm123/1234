package com.gox.system.service;

import java.util.List;
import com.gox.system.domain.form.Config;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author gox
 * @date 2021-02-02
 */
public interface IConfigService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public Config selectConfigById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param config 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Config> selectConfigList(Config config);

    /**
     * 新增【请填写功能名称】
     * 
     * @param config 【请填写功能名称】
     * @return 结果
     */
    public int insertConfig(Config config);

    /**
     * 修改【请填写功能名称】
     * 
     * @param config 【请填写功能名称】
     * @return 结果
     */
    public int updateConfig(Config config);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    public int deleteConfigByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteConfigById(Long id);
}
