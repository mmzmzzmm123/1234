package com.stdiet.custom.mapper;

import com.stdiet.custom.domain.SysRecipes;
import com.stdiet.custom.domain.SysRecipesDaily;
import com.stdiet.custom.domain.SysRecipesDailyDishes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRecipesMapper {


    public int addRecipes(SysRecipes sysRecipes);

    public int bashAddDishes(List<SysRecipesDailyDishes> dishes);

    public int bashAddMenus(List<SysRecipesDaily> menus);


    public int getNumDayByCusId(Long id);

    public List<SysRecipes> selectSysRecipesByRecipesId(Long id);

    public List<SysRecipes> selectSysRecipesByRecipesIdShow(Long id);

    public int updateDishesDetail(SysRecipesDailyDishes sysRecipesDaily);

    public int addDishes(SysRecipesDailyDishes sysRecipesDaily);

    public int deleteDishes(Long id);

    public int deleteMenu(Long id);

    public List<SysRecipesDailyDishes> selectDishesByMenuId(Long id);

    public List<SysRecipesDailyDishes> selectDishesByMenuIdShow(Long id);

    List<SysRecipesDaily> getRecipesListByRecipesId(@Param("recipesId")Long id);

    int updateRecipesById(SysRecipesDaily sysRecipesDaily);
}
