package com.gox.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gox.system.mapper.OptionsItemMapper;
import com.gox.system.domain.form.OptionsItem;
import com.gox.system.service.IOptionsItemService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author gox
 * @date 2021-02-02
 */
@Service
public class OptionsItemServiceImpl implements IOptionsItemService 
{
    @Autowired
    private OptionsItemMapper optionsItemMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public OptionsItem selectOptionsItemById(Long id)
    {
        return optionsItemMapper.selectOptionsItemById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param optionsItem 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<OptionsItem> selectOptionsItemList(OptionsItem optionsItem)
    {
        return optionsItemMapper.selectOptionsItemList(optionsItem);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param optionsItem 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertOptionsItem(OptionsItem optionsItem)
    {
        return optionsItemMapper.insertOptionsItem(optionsItem);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param optionsItem 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateOptionsItem(OptionsItem optionsItem)
    {
        return optionsItemMapper.updateOptionsItem(optionsItem);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteOptionsItemByIds(Long[] ids)
    {
        return optionsItemMapper.deleteOptionsItemByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteOptionsItemById(Long id)
    {
        return optionsItemMapper.deleteOptionsItemById(id);
    }
}
