package com.gox.basic.mapper;

import com.gox.basic.domain.form.OptionsItem;

import java.util.List;

/**
 *   Mapper接口
 *
 * @author gox
 * @date 2021-02-02
 */
public interface OptionsItemMapper {
    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    public OptionsItem selectOptionsItemById(Long id);

    public OptionsItem selectOptionsItemBySlotId(Long slotId);

    public OptionsItem selectOptionsItemByItemId(Long itemId);

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

    /**
     * 修改
     *
     * @param optionsItem
     * @return 结果
     */
    public int updateOptionsItem(OptionsItem optionsItem);

    /**
     * 删除
     *
     * @param id   ID
     * @return 结果
     */
    public int deleteOptionsItemById(Long id);

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOptionsItemByIds(Long[] ids);
}
