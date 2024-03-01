package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Dynamic;

/**
 * 动态信息管理Mapper接口
 * 
 * @author carol
 * @date 2024-03-01
 */
public interface DynamicMapper 
{
    /**
     * 查询动态信息管理
     * 
     * @param id 动态信息管理主键
     * @return 动态信息管理
     */
    public Dynamic selectDynamicById(Long id);

    /**
     * 查询动态信息管理列表
     * 
     * @param dynamic 动态信息管理
     * @return 动态信息管理集合
     */
    public List<Dynamic> selectDynamicList(Dynamic dynamic);

    /**
     * 新增动态信息管理
     * 
     * @param dynamic 动态信息管理
     * @return 结果
     */
    public int insertDynamic(Dynamic dynamic);

    /**
     * 修改动态信息管理
     * 
     * @param dynamic 动态信息管理
     * @return 结果
     */
    public int updateDynamic(Dynamic dynamic);

    /**
     * 删除动态信息管理
     * 
     * @param id 动态信息管理主键
     * @return 结果
     */
    public int deleteDynamicById(Long id);

    /**
     * 批量删除动态信息管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDynamicByIds(Long[] ids);
}
