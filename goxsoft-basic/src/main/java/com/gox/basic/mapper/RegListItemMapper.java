package com.gox.basic.mapper;

import com.gox.basic.domain.form.RegListItem;

import java.util.List;

/**
 *   Mapper接口
 *
 * @author gox
 * @date 2021-02-02
 */
public interface RegListItemMapper {
    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    public RegListItem selectRegListItemById(Long id);

    public RegListItem selectRegListItemByConfigId(Long configId);

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

    /**
     * 修改
     *
     * @param regListItem
     * @return 结果
     */
    public int updateRegListItem(RegListItem regListItem);

    /**
     * 删除
     *
     * @param id   ID
     * @return 结果
     */
    public int deleteRegListItemById(Long id);

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRegListItemByIds(Long[] ids);
}
