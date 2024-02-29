package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DynamicMapper;
import com.ruoyi.system.domain.Dynamic;
import com.ruoyi.system.service.IDynamicService;

/**
 * 动态管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-02-29
 */
@Service
public class DynamicServiceImpl implements IDynamicService 
{
    @Autowired
    private DynamicMapper dynamicMapper;

    /**
     * 查询动态管理
     * 
     * @param id 动态管理主键
     * @return 动态管理
     */
    @Override
    public Dynamic selectDynamicById(Long id)
    {
        return dynamicMapper.selectDynamicById(id);
    }

    /**
     * 查询动态管理列表
     * 
     * @param dynamic 动态管理
     * @return 动态管理
     */
    @Override
    public List<Dynamic> selectDynamicList(Dynamic dynamic)
    {
        return dynamicMapper.selectDynamicList(dynamic);
    }

    /**
     * 新增动态管理
     * 
     * @param dynamic 动态管理
     * @return 结果
     */
    @Override
    public int insertDynamic(Dynamic dynamic)
    {
        dynamic.setCreateTime(DateUtils.getNowDate());
        return dynamicMapper.insertDynamic(dynamic);
    }

    /**
     * 修改动态管理
     * 
     * @param dynamic 动态管理
     * @return 结果
     */
    @Override
    public int updateDynamic(Dynamic dynamic)
    {
        dynamic.setUpdateTime(DateUtils.getNowDate());
        return dynamicMapper.updateDynamic(dynamic);
    }

    /**
     * 批量删除动态管理
     * 
     * @param ids 需要删除的动态管理主键
     * @return 结果
     */
    @Override
    public int deleteDynamicByIds(Long[] ids)
    {
        return dynamicMapper.deleteDynamicByIds(ids);
    }

    /**
     * 删除动态管理信息
     * 
     * @param id 动态管理主键
     * @return 结果
     */
    @Override
    public int deleteDynamicById(Long id)
    {
        return dynamicMapper.deleteDynamicById(id);
    }
}
