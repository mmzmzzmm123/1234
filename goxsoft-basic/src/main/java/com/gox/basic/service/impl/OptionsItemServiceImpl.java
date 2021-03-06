package com.gox.basic.service.impl;

import com.gox.basic.domain.form.OptionsItem;
import com.gox.basic.mapper.OptionsItemMapper;
import com.gox.basic.service.IOptionsItemService;
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
public class OptionsItemServiceImpl implements IOptionsItemService {
    @Autowired
    private OptionsItemMapper optionsItemMapper;

    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    @Override
    public OptionsItem selectOptionsItemById(Long id) {
        return optionsItemMapper.selectOptionsItemById(id);
    }

    /**
     * 查询  列表
     *
     * @param optionsItem
     * @return
     */
    @Override
    public List<OptionsItem> selectOptionsItemList(OptionsItem optionsItem) {
        return optionsItemMapper.selectOptionsItemList(optionsItem);
    }

    /**
     * 新增
     *
     * @param optionsItem
     * @return 结果
     */
    @Override
    public int insertOptionsItem(OptionsItem optionsItem) {
        return optionsItemMapper.insertOptionsItem(optionsItem);
    }

    @Override
    public int insertOptionsItems(Iterable<OptionsItem> optionsItems, Long itemId) {
        int c = 0;
        for (OptionsItem optionsItem : optionsItems) {
            optionsItem.setItemId(itemId);
            c += insertOptionsItem(optionsItem);
        }
        return c;
    }

    @Override
    public int insertOptionsItemsSlot(Iterable<OptionsItem> optionsItems, Long slotId) {
        int c = 0;
        for (OptionsItem optionsItem : optionsItems) {
            optionsItem.setSlotId(slotId);
            c += insertOptionsItem(optionsItem);
        }
        return c;
    }

    /**
     * 修改
     *
     * @param optionsItem
     * @return 结果
     */
    @Override
    public int updateOptionsItem(OptionsItem optionsItem) {
        return optionsItemMapper.updateOptionsItem(optionsItem);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的  ID
     * @return 结果
     */
    @Override
    public int deleteOptionsItemByIds(Long[] ids) {
        return optionsItemMapper.deleteOptionsItemByIds(ids);
    }

    /**
     * 删除  信息
     *
     * @param id   ID
     * @return 结果
     */
    @Override
    public int deleteOptionsItemById(Long id) {
        return optionsItemMapper.deleteOptionsItemById(id);
    }
}
