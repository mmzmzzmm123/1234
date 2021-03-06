package com.gox.basic.mapper;

import com.gox.basic.domain.form.Config;

import java.util.List;

/**
 *   Mapper接口
 *
 * @author gox
 * @date 2021-02-02
 */
public interface ConfigMapper {
    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    public Config selectConfigById(Long id);

    public Config selectConfigByItemId(Long itemId);

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
     * 删除
     *
     * @param id   ID
     * @return 结果
     */
    public int deleteConfigById(Long id);

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteConfigByIds(Long[] ids);
}
