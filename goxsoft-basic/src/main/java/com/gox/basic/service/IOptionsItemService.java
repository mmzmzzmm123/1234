package com.gox.basic.service;

import com.gox.basic.domain.form.OptionsItem;

import java.util.List;

/**
 *   Service接口
 *
 * @author gox
 * @date 2021-02-02
 */
public interface IOptionsItemService {
    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    public OptionsItem selectOptionsItemById(Long id);

    /**
     * 查询  列表
     *
     * @param optionsItem
     * @return   集合
     */
    public List<OptionsItem> selectOptionsItemList(OptionsItem optionsItem);

    /**
     * 新增
     *
     * @param optionsItem
     * @return 结果
     */
    public int insertOptionsItem(OptionsItem optionsItem);

    int insertOptionsItems(Iterable<OptionsItem> optionsItems, Long itemId);

    int insertOptionsItemsSlot(Iterable<OptionsItem> optionsItems, Long slotId);

    /**
     * 修改
     *
     * @param optionsItem
     * @return 结果
     */
    public int updateOptionsItem(OptionsItem optionsItem);

    /**
     * 批量删除
     *
     * @param ids 需要删除的  ID
     * @return 结果
     */
    public int deleteOptionsItemByIds(Long[] ids);

    /**
     * 删除  信息
     *
     * @param id   ID
     * @return 结果
     */
    public int deleteOptionsItemById(Long id);
}
