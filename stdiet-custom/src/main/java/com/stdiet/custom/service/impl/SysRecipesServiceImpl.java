package com.stdiet.custom.service.impl;

import com.stdiet.custom.domain.SysDishes;
import com.stdiet.custom.domain.SysRecipes;
import com.stdiet.custom.mapper.SysRecipesMapper;
import com.stdiet.custom.service.ISysRecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysRecipesServiceImpl implements ISysRecipesService {

    @Autowired
    private SysRecipesMapper sysRecipesMapper;

    @Override
    public List<SysRecipes> selectSysRecipesByRecipesId(Long id) {
        return sysRecipesMapper.selectSysRecipesByRecipesId(id);
    }

    @Override
    public int updateDishesDetail(SysDishes sysDishes) {
        return sysRecipesMapper.updateDishesDetail(sysDishes);
    }

    @Override
    public int insertDishes(SysDishes sysDishes) {
        return sysRecipesMapper.insertDishes(sysDishes);
    }

    @Override
    public int deleteDishes(Long cId) {
        return sysRecipesMapper.deleteDishes(cId);
    }
}
