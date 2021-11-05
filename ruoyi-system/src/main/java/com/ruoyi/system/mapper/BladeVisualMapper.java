package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.BladeVisual;

/**
 * 可视化Mapper接口
 *
 * @author yangsj
 * @date 2021-10-25
 */
public interface BladeVisualMapper
{
    /**
     * 查询可视化
     *
     * @param id 可视化主键
     * @return 可视化
     */
    public BladeVisual selectBladeVisualById(Long id);

    /**
     * 通过分类查找大屏
     * @param category 分类
     * @return 大屏列表
     */
    List<BladeVisual> selectVisualByCategory(Long category);

    /**
     * 查询可视化列表
     *
     * @param bladeVisual 可视化
     * @return 可视化集合
     */
    public List<BladeVisual> selectBladeVisualList(BladeVisual bladeVisual);

    /**
     * 新增可视化
     *
     * @param bladeVisual 可视化
     * @return 结果
     */
    public int insertBladeVisual(BladeVisual bladeVisual);

    /**
     * 修改可视化
     *
     * @param bladeVisual 可视化
     * @return 结果
     */
    public int updateBladeVisual(BladeVisual bladeVisual);

    /**
     * 删除可视化
     *
     * @param id 可视化主键
     * @return 结果
     */
    public int deleteBladeVisualById(Long id);

    /**
     * 批量删除可视化
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBladeVisualByIds(Long[] ids);


}
