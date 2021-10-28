package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.BladeVisualCategory;

/**
 * 可视化分类Service接口
 * 
 * @author yangsj
 * @date 2021-10-25
 */
public interface IBladeVisualCategoryService 
{
    /**
     * 查询可视化分类
     * 
     * @param id 可视化分类主键
     * @return 可视化分类
     */
    public BladeVisualCategory selectBladeVisualCategoryById(Long id);

    /**
     * 查询可视化分类列表
     * 
     * @param bladeVisualCategory 可视化分类
     * @return 可视化分类集合
     */
    public List<BladeVisualCategory> selectBladeVisualCategoryList(BladeVisualCategory bladeVisualCategory);

    /**
     * 新增可视化分类
     * 
     * @param bladeVisualCategory 可视化分类
     * @return 结果
     */
    public int insertBladeVisualCategory(BladeVisualCategory bladeVisualCategory);

    /**
     * 修改可视化分类
     * 
     * @param bladeVisualCategory 可视化分类
     * @return 结果
     */
    public int updateBladeVisualCategory(BladeVisualCategory bladeVisualCategory);

    /**
     * 批量删除可视化分类
     * 
     * @param ids 需要删除的可视化分类主键集合
     * @return 结果
     */
    public int deleteBladeVisualCategoryByIds(Long[] ids);

    /**
     * 删除可视化分类信息
     * 
     * @param id 可视化分类主键
     * @return 结果
     */
    public int deleteBladeVisualCategoryById(Long id);
}
