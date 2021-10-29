package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BladeVisualCategoryMapper;
import com.ruoyi.system.domain.BladeVisualCategory;
import com.ruoyi.system.service.IBladeVisualCategoryService;

/**
 * 可视化分类Service业务层处理
 * 
 * @author yangsj
 * @date 2021-10-25
 */
@Service
public class BladeVisualCategoryServiceImpl implements IBladeVisualCategoryService 
{
    @Autowired
    private BladeVisualCategoryMapper bladeVisualCategoryMapper;

    /**
     * 查询可视化分类
     * 
     * @param id 可视化分类主键
     * @return 可视化分类
     */
    @Override
    public BladeVisualCategory selectBladeVisualCategoryById(Long id)
    {
        return bladeVisualCategoryMapper.selectBladeVisualCategoryById(id);
    }

    /**
     * 查询可视化分类列表
     * 
     * @param bladeVisualCategory 可视化分类
     * @return 可视化分类
     */
    @Override
    public List<BladeVisualCategory> selectBladeVisualCategoryList(BladeVisualCategory bladeVisualCategory)
    {
        return bladeVisualCategoryMapper.selectBladeVisualCategoryList(bladeVisualCategory);
    }

    /**
     * 新增可视化分类
     * 
     * @param bladeVisualCategory 可视化分类
     * @return 结果
     */
    @Override
    public int insertBladeVisualCategory(BladeVisualCategory bladeVisualCategory)
    {
        return bladeVisualCategoryMapper.insertBladeVisualCategory(bladeVisualCategory);
    }

    /**
     * 修改可视化分类
     * 
     * @param bladeVisualCategory 可视化分类
     * @return 结果
     */
    @Override
    public int updateBladeVisualCategory(BladeVisualCategory bladeVisualCategory)
    {
        return bladeVisualCategoryMapper.updateBladeVisualCategory(bladeVisualCategory);
    }

    /**
     * 批量删除可视化分类
     * 
     * @param ids 需要删除的可视化分类主键
     * @return 结果
     */
    @Override
    public int deleteBladeVisualCategoryByIds(Long[] ids)
    {
        return bladeVisualCategoryMapper.deleteBladeVisualCategoryByIds(ids);
    }

    /**
     * 删除可视化分类信息
     * 
     * @param id 可视化分类主键
     * @return 结果
     */
    @Override
    public int deleteBladeVisualCategoryById(Long id)
    {
        return bladeVisualCategoryMapper.deleteBladeVisualCategoryById(id);
    }
}
