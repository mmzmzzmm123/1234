package com.stdiet.custom.service.impl;

import java.util.List;
import com.stdiet.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysDishesMapper;
import com.stdiet.custom.domain.SysDishes;
import com.stdiet.custom.service.ISysDishesService;

/**
 * 菜品Service业务层处理
 * 
 * @author wonder
 * @date 2020-12-28
 */
@Service
public class SysDishesServiceImpl implements ISysDishesService 
{
    @Autowired
    private SysDishesMapper sysDishesMapper;

    /**
     * 查询菜品
     * 
     * @param id 菜品ID
     * @return 菜品
     */
    @Override
    public SysDishes selectSysDishesById(Long id)
    {
        return sysDishesMapper.selectSysDishesById(id);
    }

    /**
     * 查询菜品列表
     * 
     * @param sysDishes 菜品
     * @return 菜品
     */
    @Override
    public List<SysDishes> selectSysDishesList(SysDishes sysDishes)
    {
        return sysDishesMapper.selectSysDishesList(sysDishes);
    }

    /**
     * 新增菜品
     * 
     * @param sysDishes 菜品
     * @return 结果
     */
    @Override
    public int insertSysDishes(SysDishes sysDishes)
    {
        sysDishes.setCreateTime(DateUtils.getNowDate());
        return sysDishesMapper.insertSysDishes(sysDishes);
    }

    /**
     * 修改菜品
     * 
     * @param sysDishes 菜品
     * @return 结果
     */
    @Override
    public int updateSysDishes(SysDishes sysDishes)
    {
        sysDishes.setUpdateTime(DateUtils.getNowDate());
        return sysDishesMapper.updateSysDishes(sysDishes);
    }

    /**
     * 批量删除菜品
     * 
     * @param ids 需要删除的菜品ID
     * @return 结果
     */
    @Override
    public int deleteSysDishesByIds(Long[] ids)
    {
        return sysDishesMapper.deleteSysDishesByIds(ids);
    }

    /**
     * 删除菜品信息
     * 
     * @param id 菜品ID
     * @return 结果
     */
    @Override
    public int deleteSysDishesById(Long id)
    {
        return sysDishesMapper.deleteSysDishesById(id);
    }
}