package com.stdiet.web.controller.custom;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        List<SysRecipesPlan> list = sysRecipesPlanService.selectSysRecipesPlanList(sysRecipesPlan);
        return getDataTable(list);
    }

    /**
     * 导出食谱计划列表
     */
    @PreAuthorize("@ss.hasPermi('recipes:recipesPlan:export')")
    @Log(title = "食谱计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysRecipesPlan sysRecipesPlan)
    {
        List<SysRecipesPlan> list = sysRecipesPlanService.selectSysRecipesPlanList(sysRecipesPlan);
        ExcelUtil<SysRecipesPlan> util = new ExcelUtil<SysRecipesPlan>(SysRecipesPlan.class);
        return util.exportExcel(list, "recipesPlan");
    }
}