package com.gox.basic.service;

import com.gox.basic.domain.form.RegListItem;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author gox
 * @date 2021-02-02
 */
public interface IRegListItemService {
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public RegListItem selectRegListItemById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param regListItem 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<RegListItem> selectRegListItemList(RegListItem regListItem);

    /**
     * 新增【请填写功能名称】
     *
     * @param regListItem 【请填写功能名称】
     * @return 结果
     */
    public int insertRegListItem(RegListItem regListItem);

    int insertRegListItems(Iterable<RegListItem> regListItems, Long configId);

    /**
     * 修改【请填写功能名称】
     *
     * @param regListItem 【请填写功能名称】
     * @return 结果
     */
    public int updateRegListItem(RegListItem regListItem);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    public int deleteRegListItemByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteRegListItemById(Long id);
}
