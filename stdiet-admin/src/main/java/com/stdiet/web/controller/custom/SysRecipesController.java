package com.stdiet.web.controller.custom;

import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.custom.domain.SysDishes;
import com.stdiet.custom.service.ISysRecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/custom/recipes")
public class SysRecipesController extends BaseController {

    @Autowired
    private ISysRecipesService sysRecipesService;

    @GetMapping(value = "/{resipesId}")
    public AjaxResult getInfo(@PathVariable("resipesId") Long resipesId) {
        return AjaxResult.success(sysRecipesService.selectSysRecipesByRecipesId(resipesId));
    }

    /**
     * 修改食谱菜品
     *
     * @return
     */
    @Log(title = "食谱", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysDishes sysDishes) {
        return toAjax(sysRecipesService.updateDishesDetail(sysDishes));
    }

    @Log(title = "食谱", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysDishes sysDishes) {
        int rows = sysRecipesService.insertDishes(sysDishes);
        if (rows > 0) {
            return AjaxResult.success(sysDishes.getId());
        } else {
            return AjaxResult.error();
        }
    }

    @Log(title = "销售订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/dishes/{cId}")
    public AjaxResult delete(@PathVariable Long cId) {
        return toAjax(sysRecipesService.deleteDishes(cId));
    }
}
