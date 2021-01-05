package com.stdiet.custom.service.impl;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.SysDishes;
import com.stdiet.custom.domain.SysDishesIngredient;
import com.stdiet.custom.mapper.SysDishesMapper;
import com.stdiet.custom.service.ISysDishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜品Service业务层处理
 *
 * @author wonder
 * @date 2020-12-28
 */
@Service
public class SysDishesServiceImpl implements ISysDishesService {
    @Autowired
    private SysDishesMapper sysDishesMapper;

    /**
     * 查询菜品
     *
     * @param id 菜品ID
     * @return 菜品
     */
    @Override
    public SysDishes selectSysDishesById(Long id) {
        SysDishes dishes = sysDishesMapper.selectSysDishesById(id);
        if (StringUtils.isNotNull(dishes)) {
            List<SysDishesIngredient> ingredientArray = selectSysIngreditentsById(id);
            if (StringUtils.isNull(ingredientArray)) {
                dishes.setIgdList(new ArrayList<>());
            } else {
                dishes.setIgdList(ingredientArray);
            }

        }
        return dishes;
    }

    @Override
    public List<SysDishesIngredient> selectSysIngreditentsById(Long id) {
        return sysDishesMapper.selectSysIngreditentsById(id);
    }

    /**
     * 查询菜品列表
     *
     * @param sysDishes 菜品
     * @return 菜品
     */
    @Override
    public List<SysDishes> selectSysDishesList(SysDishes sysDishes) {
        return sysDishesMapper.selectSysDishesList(sysDishes);
    }

    /**
     * 新增菜品
     *
     * @param sysDishes 菜品
     * @return 结果
     */
    @Override
    public int insertSysDishes(SysDishes sysDishes) {
        sysDishes.setCreateTime(DateUtils.getNowDate());
        int rows = sysDishesMapper.insertSysDishes(sysDishes);
        //
        insertDishesIngredient(sysDishes);
        return rows;
    }

    public void insertDishesIngredient(SysDishes sysDishes) {
        if (StringUtils.isNotNull(sysDishes.getIgdList())) {
            List<SysDishesIngredient> list = sysDishes.getIgdList();
            for (SysDishesIngredient dishesIngredient : list) {
                dishesIngredient.setDishesId(sysDishes.getId());
                dishesIngredient.setIngredientId(dishesIngredient.getId());
            }
            sysDishesMapper.bashInsertDishesIngredent(list);
        }
    }

    /**
     * 修改菜品
     *
     * @param sysDishes 菜品
     * @return 结果
     */
    @Override
    public int updateSysDishes(SysDishes sysDishes) {
        sysDishes.setUpdateTime(DateUtils.getNowDate());
        Long dishesId = sysDishes.getId();
        sysDishesMapper.deleteIngredientById(dishesId);
        insertDishesIngredient(sysDishes);
        return sysDishesMapper.updateSysDishes(sysDishes);
    }

    /**
     * 批量删除菜品
     *
     * @param ids 需要删除的菜品ID
     * @return 结果
     */
    @Override
    public int deleteSysDishesByIds(Long[] ids) {
        sysDishesMapper.deleteIngredientByIds(ids);
        return sysDishesMapper.deleteSysDishesByIds(ids);
    }

    /**
     * 删除菜品信息
     *
     * @param id 菜品ID
     * @return 结果
     */
    @Override
    public int deleteSysDishesById(Long id) {
        sysDishesMapper.deleteIngredientById(id);
        return sysDishesMapper.deleteSysDishesById(id);
    }
}