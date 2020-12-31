package com.stdiet.custom.mapper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import com.stdiet.custom.domain.SysDishes;
import com.stdiet.custom.domain.SysDishesIngredient;
import com.stdiet.custom.domain.SysIngredient;

/**
 * 菜品Mapper接口
 * 
 * @author wonder
 * @date 2020-12-28
 */
public interface SysDishesMapper 
{
    /**
     * 查询菜品
     * 
     * @param id 菜品ID
     * @return 菜品
     */
    public SysDishes selectSysDishesById(Long id);

    public ArrayList<SysDishesIngredient> selectSysIngreditentsById(Long id);

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
     * 删除菜品
     * 
     * @param id 菜品ID
     * @return 结果
     */
    public int deleteSysDishesById(Long id);

    /**
     * 批量删除菜品
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysDishesByIds(Long[] ids);

    public int deleteIngredientById(Long id);

    public int deleteIngredientByIds(Long[] ids);

    public int bashInsertDishesIngredent(List<SysDishesIngredient> sysDishesIngredients);
}