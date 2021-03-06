package com.gox.basic.service.impl;

import com.gox.basic.domain.form.RegListItem;
import com.gox.basic.mapper.RegListItemMapper;
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
public class RegListItemServiceImpl implements IRegListItemService {
    @Autowired
    private RegListItemMapper regListItemMapper;

    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    @Override
    public RegListItem selectRegListItemById(Long id) {
        return regListItemMapper.selectRegListItemById(id);
    }

    /**
     * 查询  列表
     *
     * @param regListItem
     * @return
     */
    @Override
    public List<RegListItem> selectRegListItemList(RegListItem regListItem) {
        return regListItemMapper.selectRegListItemList(regListItem);
    }

    /**
     * 新增
     *
     * @param regListItem
     * @return 结果
     */
    @Override
    public int insertRegListItem(RegListItem regListItem) {
        return regListItemMapper.insertRegListItem(regListItem);
    }

    @Override
    public int insertRegListItems(Iterable<RegListItem> regListItems, Long configId) {
        int c = 0;
        for (RegListItem regListItem : regListItems) {
            regListItem.setConfigId(configId);
            c += insertRegListItem(regListItem);
        }
        return c;
    }

    /**
     * 修改
     *
     * @param regListItem
     * @return 结果
     */
    @Override
    public int updateRegListItem(RegListItem regListItem) {
        return regListItemMapper.updateRegListItem(regListItem);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的  ID
     * @return 结果
     */
    @Override
    public int deleteRegListItemByIds(Long[] ids) {
        return regListItemMapper.deleteRegListItemByIds(ids);
    }

    /**
     * 删除  信息
     *
     * @param id   ID
     * @return 结果
     */
    @Override
    public int deleteRegListItemById(Long id) {
        return regListItemMapper.deleteRegListItemById(id);
    }
}
