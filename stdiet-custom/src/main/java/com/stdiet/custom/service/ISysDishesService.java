package com.stdiet.custom.service;

import java.util.List;
import java.util.Map;

import com.stdiet.custom.domain.SysDishes;
import com.stdiet.custom.domain.SysDishesIngredient;
import com.stdiet.custom.domain.SysIngredient;
import com.stdiet.custom.domain.SysPhysicalSignsObj;

/**
 * 菜品Service接口
 * 
 * @author wonder
 * @date 2020-12-28
 */
public interface ISysDishesService 
{
    /**
     * 查询菜品
     * 
     * @param id 菜品ID
     * @return 菜品
     */
    public SysDishes selectSysDishesById(Long id);

    public List<SysDishesIngredient> selectSysIngreditentsById(Long id);

    /**
     * 查询菜品列表
     * 
     * @param sysDishes 菜品
     * @return 菜品集合
     */
    public List<SysDishes> selectSysDishesList(SysDishes sysDishes);

    /**
     * 新增菜品
     * 
     * @param sysDishes 菜品
     * @return 结果
     */
    public int insertSysDishes(SysDishes sysDishes);

    /**
     * 修改菜品
     * 
     * @param sysDishes 菜品
     * @return 结果
     */
    public int updateSysDishes(SysDishes sysDishes);

    /**
     * 批量删除菜品
     * 
     * @param ids 需要删除的菜品ID
     * @return 结果
     */
//    public int deleteSysDishesByIds(Long[] ids);

    /**
     * 删除菜品信息
     * 
     * @param id 菜品ID
     * @return 结果
     */
    public int deleteSysDishesById(Long id);

    public String getDishesMenuTypeById(Long id);

    /**
     * 根据菜品ID查询大类小类名称
     * @param dishId
     * @return
     */
    String getDishClassNameById(Long dishId);

}