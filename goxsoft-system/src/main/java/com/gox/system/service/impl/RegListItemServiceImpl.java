package com.gox.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gox.system.mapper.RegListItemMapper;
import com.gox.system.domain.form.RegListItem;
import com.gox.system.service.IRegListItemService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author gox
 * @date 2021-02-02
 */
@Service
public class RegListItemServiceImpl implements IRegListItemService 
{
    @Autowired
    private RegListItemMapper regListItemMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public RegListItem selectRegListItemById(Long id)
    {
        return regListItemMapper.selectRegListItemById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param regListItem 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<RegListItem> selectRegListItemList(RegListItem regListItem)
    {
        return regListItemMapper.selectRegListItemList(regListItem);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param regListItem 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertRegListItem(RegListItem regListItem)
    {
        return regListItemMapper.insertRegListItem(regListItem);
    }
    @Override
    public int insertRegListItems(Iterable<RegListItem> regListItems,Long configId){
        int c=0;
        for (RegListItem regListItem : regListItems) {
            regListItem.setConfigId(configId);
            c+=insertRegListItem(regListItem);
        }
        return c;
    }
    /**
     * 修改【请填写功能名称】
     * 
     * @param regListItem 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateRegListItem(RegListItem regListItem)
    {
        return regListItemMapper.updateRegListItem(regListItem);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteRegListItemByIds(Long[] ids)
    {
        return regListItemMapper.deleteRegListItemByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteRegListItemById(Long id)
    {
        return regListItemMapper.deleteRegListItemById(id);
    }
}
