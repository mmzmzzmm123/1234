package com.gox.basic.mapper;

import com.gox.basic.domain.form.RegListItem;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author gox
 * @date 2021-02-02
 */
public interface RegListItemMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public RegListItem selectRegListItemById(Long id);
    public RegListItem selectRegListItemByConfigId(Long configId);
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

    /**
     * 修改【请填写功能名称】
     * 
     * @param regListItem 【请填写功能名称】
     * @return 结果
     */
    public int updateRegListItem(RegListItem regListItem);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteRegListItemById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRegListItemByIds(Long[] ids);
}
