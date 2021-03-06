package com.gox.basic.service;

import com.gox.basic.domain.form.Config;

import java.util.List;

/**
 *   Service接口
 *
 * @author gox
 * @date 2021-02-02
 */
public interface IConfigService {
    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    public Config selectConfigById(Long id);

    /**
     * 查询  列表
     *
     * @param config
     * @return   集合
     */
    public List<Config> selectConfigList(Config config);

    /**
     * 新增
     *
     * @param config
     * @return 结果
     */
    public int insertConfig(Config config);

    /**
     * 修改
     *
     * @param config
     * @return 结果
     */
    public int updateConfig(Config config);

    /**
     * 批量删除
     *
     * @param ids 需要删除的  ID
     * @return 结果
     */
    public int deleteConfigByIds(Long[] ids);

    /**
     * 删除  信息
     *
     * @param id   ID
     * @return 结果
     */
    public int deleteConfigById(Long id);
}
