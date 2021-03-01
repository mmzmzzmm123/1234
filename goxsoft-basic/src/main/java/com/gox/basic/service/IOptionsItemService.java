package com.gox.basic.service;

import com.gox.basic.domain.form.OptionsItem;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author gox
 * @date 2021-02-02
 */
public interface IOptionsItemService {
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public OptionsItem selectOptionsItemById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param optionsItem 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<OptionsItem> selectOptionsItemList(OptionsItem optionsItem);

    /**
     * 新增【请填写功能名称】
     *
     * @param optionsItem 【请填写功能名称】
     * @return 结果
     */
    public int insertOptionsItem(OptionsItem optionsItem);

    int insertOptionsItems(Iterable<OptionsItem> optionsItems, Long itemId);

    int insertOptionsItemsSlot(Iterable<OptionsItem> optionsItems, Long slotId);

    /**
     * 修改【请填写功能名称】
     *
     * @param optionsItem 【请填写功能名称】
     * @return 结果
     */
    public int updateOptionsItem(OptionsItem optionsItem);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    public int deleteOptionsItemByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteOptionsItemById(Long id);
}
