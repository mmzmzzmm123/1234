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
            sysRecipesPlan.setReviewStatus(sysRecipes.getReviewStatus());// 设置制作中
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
    public List<SysRecipesDailyDishes> selectDishesByMenuId(Long id) {
        return sysRecipesMapper.selectDishesByMenuId(id);
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

    @Override
    public Long[] replaceDishes(List<SysRecipesDailyDishes> sysRecipesDailyDishes) {
        // 删除原有
        sysRecipesMapper.deleteMenu(sysRecipesDailyDishes.get(0).getMenuId());
        // 插入新的
        int row = sysRecipesMapper.bashAddDishes(sysRecipesDailyDishes);
        if (row > 0) {
            Long[] ids = new Long[sysRecipesDailyDishes.size()];
            for (int i = 0; i < sysRecipesDailyDishes.size(); i++) {
                ids[i] = sysRecipesDailyDishes.get(i).getId();
            }
            return ids;
        }
        return null;
    }
}
