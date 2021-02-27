package com.stdiet.web.controller.custom;

import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.core.page.TableDataInfo;
import com.stdiet.custom.domain.SysRecipesPlanModel;
import com.stdiet.custom.service.ISysRecipesPlanModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 食谱模板
 *
 * @author wonder
 * @date 2021-02-27
 */
@RestController
@RequestMapping("/recipes/model")
public class SysRecipesPlanModelController extends BaseController {
    @Autowired
    private ISysRecipesPlanModelService iSysRecipesPlanModelService;

    /**
     * 查询
     */
    @PreAuthorize("@ss.hasPermi('recipes:recipesModel:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysRecipesPlanModel sysRecipesPlanModel) {
        startPage();
        List<SysRecipesPlanModel> list = iSysRecipesPlanModelService.selectRecipesModelListByCondition(sysRecipesPlanModel);
        return getDataTable(list);
    }

    /**
     * 添加
     */
    @PreAuthorize("@ss.hasPermi('recipes:recipesModel:edit')")
    @PostMapping("/add")
    public AjaxResult add(SysRecipesPlanModel sysRecipesPlanModel) {
        return toAjax(iSysRecipesPlanModelService.insertRecipsesModel(sysRecipesPlanModel));
    }

    /**
     * 更新
     *
     * @param sysRecipesPlanModel
     * @return
     */
    @PreAuthorize("@ss.hasPermi('recipes:recipesPlan:list')")
    @PutMapping(value = "/update")
    public AjaxResult update(SysRecipesPlanModel sysRecipesPlanModel) {
        return toAjax(iSysRecipesPlanModelService.updateRecipesModel(sysRecipesPlanModel));
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('recipes:recipesPlan:query')")
    @DeleteMapping(value = "/delete/{id}")
    public AjaxResult delete(@PathVariable("id") Long id) {
        return AjaxResult.success(iSysRecipesPlanModelService.removeRecipesModel(id));
    }


}