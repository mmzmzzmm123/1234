package com.gox.basic.service.impl;

import com.gox.common.utils.uuid.SnowflakesTools;
import com.gox.basic.domain.form.Config;
import com.gox.basic.domain.form.RegListItem;
import com.gox.basic.mapper.ConfigMapper;
import com.gox.basic.service.IConfigService;
import com.gox.basic.service.IRegListItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *   Service业务层处理
 *
 * @author gox
 * @date 2021-02-02
 */
@Service
public class ConfigServiceImpl implements IConfigService {
    @Autowired
    private ConfigMapper configMapper;
    @Autowired
    private IRegListItemService regListItemService;

    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    @Override
    public Config selectConfigById(Long id) {
        return configMapper.selectConfigById(id);
    }

    /**
     * 查询  列表
     *
     * @param config
     * @return
     */
    @Override
    public List<Config> selectConfigList(Config config) {
        return configMapper.selectConfigList(config);
    }

    /**
     * 新增
     *
     * @param config
     * @return 结果
     */
    @Override
    public int insertConfig(Config config) {
        Long id = SnowflakesTools.WORKER.nextId();
        List<RegListItem> reg = config.getRegList();
        regListItemService.insertRegListItems(reg, id);
        return configMapper.insertConfig(config);
    }

    /**
     * 修改
     *
     * @param config
     * @return 结果
     */
    @Override
    public int updateConfig(Config config) {
        return configMapper.updateConfig(config);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的  ID
     * @return 结果
     */
    @Override
    public int deleteConfigByIds(Long[] ids) {
        return configMapper.deleteConfigByIds(ids);
    }

    /**
     * 删除  信息
     *
     * @param id   ID
     * @return 结果
     */
    @Override
    public int deleteConfigById(Long id) {
        return configMapper.deleteConfigById(id);
    }
}
