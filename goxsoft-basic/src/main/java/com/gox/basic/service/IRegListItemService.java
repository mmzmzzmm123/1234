package com.gox.basic.service;

import com.gox.basic.domain.form.RegListItem;

import java.util.List;

/**
 *   Service接口
 *
 * @author gox
 * @date 2021-02-02
 */
public interface IRegListItemService {
    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    public RegListItem selectRegListItemById(Long id);

    /**
     * 查询  列表
     *
     * @param regListItem
     * @return   集合
     */
    public List<RegListItem> selectRegListItemList(RegListItem regListItem);

    /**
     * 新增
     *
     * @param regListItem
     * @return 结果
     */
    public int insertRegListItem(RegListItem regListItem);

    int insertRegListItems(Iterable<RegListItem> regListItems, Long configId);

    /**
     * 修改
     *
     * @param regListItem
     * @return 结果
     */
    public int updateRegListItem(RegListItem regListItem);

    /**
     * 批量删除
     *
     * @param ids 需要删除的  ID
     * @return 结果
     */
    public int deleteRegListItemByIds(Long[] ids);

    /**
     * 删除  信息
     *
     * @param id   ID
     * @return 结果
     */
    public int deleteRegListItemById(Long id);
}
