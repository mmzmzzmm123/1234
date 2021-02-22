package com.stdiet.web.controller.custom;

import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.custom.domain.SysRecipes;
import com.stdiet.custom.domain.SysRecipesDailyDishes;
import com.stdiet.custom.service.ISysRecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/custom/recipes")
public class SysRecipesController extends BaseController {

    @Autowired
    private ISysRecipesService sysRecipesService;

    /**
     * 获取食谱详情
     *
     * @param resipesId
     * @return
     */
    @GetMapping(value = "/{resipesId}")
    public AjaxResult getInfo(@PathVariable("resipesId") Long resipesId) {
        return AjaxResult.success(sysRecipesService.selectSysRecipesByRecipesId(resipesId));
    }

    @Log(title = "食谱", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult addRecipes(@RequestBody SysRecipes sysRecipes) {
        int rows = sysRecipesService.addRecipes(sysRecipes);
        if (rows > 0) {
            return AjaxResult.success(sysRecipes.getId());
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 修改食谱菜品
     *
     * @param sysRecipesDailyDishes
     * @return
     */
    @Log(title = "食谱菜品", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/dishes")
    public AjaxResult edit(@RequestBody SysRecipesDailyDishes sysRecipesDailyDishes) {
        return toAjax(sysRecipesService.updateDishesDetail(sysRecipesDailyDishes));
    }

    /**
     * 添加菜品
     *
     * @param sysRecipesDailyDishes
     * @return
     */
    @Log(title = "食谱菜品", businessType = BusinessType.INSERT)
    @PostMapping(value = "/dishes")
    public AjaxResult add(@RequestBody SysRecipesDailyDishes sysRecipesDailyDishes) {
        int rows = sysRecipesService.addDishes(sysRecipesDailyDishes);
        if (rows > 0) {
            return AjaxResult.success(sysRecipesDailyDishes.getId());
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 删除菜品
     *
     * @param id
     * @return
     */
    @Log(title = "食谱菜品", businessType = BusinessType.DELETE)
    @DeleteMapping("/dishes/{id}")
    public AjaxResult delete(@PathVariable Long id) {
        return toAjax(sysRecipesService.deleteDishes(id));
    }
}
