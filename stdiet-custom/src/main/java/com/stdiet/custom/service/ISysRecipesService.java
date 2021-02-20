package com.stdiet.custom.service;

import com.stdiet.custom.domain.SysDishes;
import com.stdiet.custom.domain.SysRecipes;

import java.util.List;

public interface ISysRecipesService {
    public List<SysRecipes> selectSysRecipesByRecipesId(Long id);

    public int updateDishesDetail(SysDishes sysDishes);

    public int insertDishes(SysDishes sysDishes);

    public int deleteDishes(Long cId);
}
