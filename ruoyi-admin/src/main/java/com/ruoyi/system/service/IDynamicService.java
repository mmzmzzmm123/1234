package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Dynamic;

/**
 * 动态管理Service接口
 * 
 * @author ruoyi
 * @date 2024-02-29
 */
public interface IDynamicService 
{
    /**
     * 查询动态管理
     * 
     * @param id 动态管理主键
     * @return 动态管理
     */
    public Dynamic selectDynamicById(Long id);

    /**
     * 查询动态管理列表
     * 
     * @param dynamic 动态管理
     * @return 动态管理集合
     */
    public List<Dynamic> selectDynamicList(Dynamic dynamic);

    /**
     * 新增动态管理
     * 
     * @param dynamic 动态管理
     * @return 结果
     */
    public int insertDynamic(Dynamic dynamic);

    /**
     * 修改动态管理
     * 
     * @param dynamic 动态管理
     * @return 结果
     */
    public int updateDynamic(Dynamic dynamic);

    /**
     * 批量删除动态管理
     * 
     * @param ids 需要删除的动态管理主键集合
     * @return 结果
     */
    public int deleteDynamicByIds(Long[] ids);

    /**
     * 删除动态管理信息
     * 
     * @param id 动态管理主键
     * @return 结果
     */
    public int deleteDynamicById(Long id);
}
