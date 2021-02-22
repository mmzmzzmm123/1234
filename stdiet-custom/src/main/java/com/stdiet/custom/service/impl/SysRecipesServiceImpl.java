package com.stdiet.custom.service.impl;

import com.stdiet.custom.domain.SysRecipes;
import com.stdiet.custom.domain.SysRecipesDaily;
import com.stdiet.custom.domain.SysRecipesDailyDishes;
import com.stdiet.custom.domain.SysRecipesPlan;
import com.stdiet.custom.mapper.SysRecipesMapper;
import com.stdiet.custom.mapper.SysRecipesPlanMapper;
import com.stdiet.custom.service.ISysRecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SysRecipesServiceImpl implements ISysRecipesService {

    @Autowired
    private SysRecipesMapper sysRecipesMapper;

    @Autowired
    private SysRecipesPlanMapper sysRecipesPlanMapper;


    @Override
    public int addRecipes(SysRecipes sysRecipes) {
        int rows = sysRecipesMapper.addRecipes(sysRecipes);
        if (rows > 0) {
            int count = sysRecipesMapper.getNumDayByCusId(sysRecipes.getCusId());
            List<SysRecipesDaily> menus = sysRecipes.getMenus();
            List<SysRecipesDailyDishes> dishes = new ArrayList<>();
            int size = menus.size();
            for (int i = 0; i < size; i++) {
                SysRecipesDaily tarMenu = menus.get(i);
                // 计算menuId
                long dailyId = sysRecipes.getId() + new Date().getTime() + i;
                tarMenu.setId(dailyId);
                // 插入recipiesId
                tarMenu.setRecipesId(sysRecipes.getId());
                // 插入numDay
                tarMenu.setNumDay(count + i + 1);
                for (SysRecipesDailyDishes tmpDishes : tarMenu.getDishes()) {
                    // 让菜品插入menuId
                    tmpDishes.setMenuId(dailyId);
                    dishes.add(tmpDishes);
                }
            }
            // 插入每天食谱
            sysRecipesMapper.bashAddMenus(menus);
            // 插入每天菜品
            sysRecipesMapper.bashAddDishes(dishes);
            // 更新食谱计划
            SysRecipesPlan sysRecipesPlan = new SysRecipesPlan();
            sysRecipesPlan.setId(sysRecipes.getPlanId());
            sysRecipesPlan.setRecipesId(sysRecipes.getId());
            sysRecipesPlanMapper.updateSysRecipesPlan(sysRecipesPlan);
        }

        return rows;
    }

    @Override
    public List<SysRecipes> selectSysRecipesByRecipesId(Long id) {
        return sysRecipesMapper.selectSysRecipesByRecipesId(id);
    }

    @Override
    public int updateDishesDetail(SysRecipesDailyDishes sysRecipesDailyDishes) {
        return sysRecipesMapper.updateDishesDetail(sysRecipesDailyDishes);
    }

    @Override
    public int addDishes(SysRecipesDailyDishes sysRecipesDailyDishes) {
        return sysRecipesMapper.addDishes(sysRecipesDailyDishes);
    }

    @Override
    public int deleteDishes(Long id) {
        return sysRecipesMapper.deleteDishes(id);
    }
}
