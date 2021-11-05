package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.BladeVisualCategory;

/**
 * 可视化分类Mapper接口
 * 
 * @author yangsj
 * @date 2021-10-25
 */
public interface BladeVisualCategoryMapper 
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
     * 删除可视化分类
     * 
     * @param id 可视化分类主键
     * @return 结果
     */
    public int deleteBladeVisualCategoryById(Long id);

    /**
     * 批量删除可视化分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBladeVisualCategoryByIds(Long[] ids);
}
