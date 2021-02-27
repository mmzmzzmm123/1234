package com.stdiet.custom.mapper;

import com.stdiet.custom.domain.SysRecipes;
import com.stdiet.custom.domain.SysRecipesDaily;
import com.stdiet.custom.domain.SysRecipesDailyDishes;

import java.util.List;

public interface SysRecipesMapper {


    public int addRecipes(SysRecipes sysRecipes);

    public int bashAddDishes(List<SysRecipesDailyDishes> dishes);

    public int bashAddMenus(List<SysRecipesDaily> menus);


    public int getNumDayByCusId(Long id);

    public List<SysRecipes> selectSysRecipesByRecipesId(Long id);

    public int updateDishesDetail(SysRecipesDailyDishes sysRecipesDaily);

    public int addDishes(SysRecipesDailyDishes sysRecipesDaily);

    public int deleteDishes(Long id);

    public List<SysRecipesDailyDishes> selectDishesByMenuId(Long id);
}
