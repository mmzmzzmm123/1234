package com.stdiet.web.controller.custom;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.custom.domain.SysRecipesPlan;
import com.stdiet.custom.service.ISysRecipesPlanService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 食谱计划Controller
 *
 * @author xzj
 * @date 2021-01-15
 */
@RestController
@RequestMapping("/recipes/recipesPlan")
public class SysRecipesPlanController extends BaseController
{
    @Autowired
    private ISysRecipesPlanService sysRecipesPlanService;

    /**
     * 查询食谱计划列表
     */
    @PreAuthorize("@ss.hasPermi('recipes:recipesPlan:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysRecipesPlan sysRecipesPlan)
    {
        startPage();
        List<SysRecipesPlan> list = sysRecipesPlanService.selectPlanListByCondition(sysRecipesPlan);
        return getDataTable(list);
    }

    /**
     * 根据订单查询完整食谱计划列表
     */
    @PreAuthorize("@ss.hasPermi('recipes:recipesPlan:list')")
    @GetMapping("/getAllPlanByOrderId")
    public TableDataInfo getAllPlanByOrderId(SysRecipesPlan sysRecipesPlan)
    {
        startPage();
        List<SysRecipesPlan> list = sysRecipesPlanService.selectPlanListByOrderId(sysRecipesPlan);
        return getDataTable(list);
    }

    /**
     * 获取食谱计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('recipes:recipesPlan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysRecipesPlanService.selectSysRecipesPlanById(id));
    }

    /**
     * 修改食谱计划
     */
    @PreAuthorize("@ss.hasPermi('recipes:recipesPlan:edit')")
    @Log(title = "食谱计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysRecipesPlan sysRecipesPlan)
    {
        return toAjax(sysRecipesPlanService.updateSysRecipesPlan(sysRecipesPlan));
    }

    /**
     * 导出食谱计划列表
     */
    @PreAuthorize("@ss.hasPermi('recipes:recipesPlan:export')")
    @Log(title = "食谱计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysRecipesPlan sysRecipesPlan)
    {
        List<SysRecipesPlan> list = sysRecipesPlanService.selectPlanListByCondition(sysRecipesPlan);
        ExcelUtil<SysRecipesPlan> util = new ExcelUtil<SysRecipesPlan>(SysRecipesPlan.class);
        return util.exportExcel(list, "recipesPlan");
    }
}