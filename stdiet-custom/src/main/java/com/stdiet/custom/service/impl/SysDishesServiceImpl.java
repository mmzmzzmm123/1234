package com.stdiet.custom.service.impl;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.*;
import com.stdiet.custom.mapper.SysDishesMapper;
import com.stdiet.custom.service.ISysDishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        insertRecommand(sysDishes);
        //
        insertNotRecommand(sysDishes);
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
        //
        sysDishesMapper.deleteDishesRecByDishesId(dishesId);
        insertRecommand(sysDishes);
        //
        sysDishesMapper.deleteDishesNotRecByDishesId(dishesId);
        insertNotRecommand(sysDishes);
        //
        sysDishesMapper.deleteIngredientById(dishesId);
        insertDishesIngredient(sysDishes);
        //
        return sysDishesMapper.updateSysDishes(sysDishes);
    }

    /**
     * 新增推荐标签
     *
     * @param sysDishes
     */
    public void insertRecommand(SysDishes sysDishes) {
        Long[] recIds = sysDishes.getRecIds();
        if (StringUtils.isNotNull(recIds)) {
            List<SysPhysicalSignsObj> list = new ArrayList<>();
            for (Long recId : recIds) {
                SysPhysicalSignsObj rec = new SysPhysicalSignsObj();
                rec.setTargetId(sysDishes.getId());
                rec.setPhysicalSignsId(recId);
                list.add(rec);
            }
            if (list.size() > 0) {
                sysDishesMapper.batchInsertDishesRec(list);
            }
        }
    }

    /**
     * 新增不推荐标签
     *
     * @param sysDishes
     */
    public void insertNotRecommand(SysDishes sysDishes) {
        Long[] notRecIds = sysDishes.getNotRecIds();
        if (StringUtils.isNotNull(notRecIds)) {
            List<SysPhysicalSignsObj> list = new ArrayList<>();
            for (Long recId : notRecIds) {
                SysPhysicalSignsObj notRec = new SysPhysicalSignsObj();
                notRec.setTargetId(sysDishes.getId());
                notRec.setPhysicalSignsId(recId);
                list.add(notRec);
            }
            if (list.size() > 0) {
                sysDishesMapper.batchInsertDishesNotRec(list);
            }
        }
    }

    /**
     * 批量删除菜品
     *
     * @param ids 需要删除的菜品ID
     * @return 结果
     */
//    @Override
//    public int deleteSysDishesByIds(Long[] ids) {
//        sysDishesMapper.deleteIngredientByIds(ids);
//        return sysDishesMapper.deleteSysDishesByIds(ids);
//    }

    /**
     * 删除菜品信息
     *
     * @param id 菜品ID
     * @return 结果
     */
    @Override
    public int deleteSysDishesById(Long id) {
//        sysDishesMapper.deleteIngredientById(id);
        return sysDishesMapper.deleteSysDishesById(id);
    }

    @Override
    public String getDishesMenuTypeById(Long id) {
        return sysDishesMapper.getDishesMenuTypeById(id);
    }


    /**
     * 根据菜品ID查询大类小类名称
     * @param dishId
     * @return
     */
    @Override
    public String getDishClassNameById(Long dishId){
        return sysDishesMapper.getDishClassNameById(dishId);
    }

}