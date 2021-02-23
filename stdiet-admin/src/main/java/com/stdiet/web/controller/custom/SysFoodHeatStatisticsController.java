package com.stdiet.web.controller.custom;

import java.util.List;

import com.stdiet.custom.domain.SysCustomerHeatStatistics;
import com.stdiet.custom.service.ISysCustomerHeatStatisticsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 外食热量统计Controller
 *
 * @author xzj
 * @date 2021-02-19
 */
@RestController
@RequestMapping("/custom/foodHeatStatistics")
public class SysFoodHeatStatisticsController extends BaseController
{
    @Autowired
    private ISysCustomerHeatStatisticsService sysCustomerHeatStatisticsService;

    /**
     * 查询外食热量统计列表
     */
    @PreAuthorize("@ss.hasPermi('custom:foodHeatStatistics:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysCustomerHeatStatistics sysCustomerHeatStatistics)
    {
        startPage();
        List<SysCustomerHeatStatistics> list = sysCustomerHeatStatisticsService.selectSysCustomerHeatStatisticsList(sysCustomerHeatStatistics);
        return getDataTable(list);
    }

    /**
     * 导出外食热量统计列表
     */
    @PreAuthorize("@ss.hasPermi('custom:foodHeatStatistics:export')")
    @Log(title = "外食热量统计", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysCustomerHeatStatistics sysCustomerHeatStatistics)
    {
        List<SysCustomerHeatStatistics> list = sysCustomerHeatStatisticsService.selectSysCustomerHeatStatisticsList(sysCustomerHeatStatistics);
        ExcelUtil<SysCustomerHeatStatistics> util = new ExcelUtil<SysCustomerHeatStatistics>(SysCustomerHeatStatistics.class);
        return util.exportExcel(list, "customerHeatstatistics");
    }

    /**
     * 获取外食热量统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:foodHeatStatistics:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysCustomerHeatStatisticsService.selectSysCustomerHeatStatisticsById(id));
    }

    /**
     * 新增外食热量统计
     */
    @PreAuthorize("@ss.hasPermi('custom:foodHeatStatistics:add')")
    @Log(title = "外食热量统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysCustomerHeatStatistics sysCustomerHeatStatistics)
    {
        return toAjax(sysCustomerHeatStatisticsService.insertSysCustomerHeatStatistics(sysCustomerHeatStatistics));
    }

    /**
     * 修改外食热量统计
     */
    @PreAuthorize("@ss.hasPermi('custom:foodHeatStatistics:edit')")
    @Log(title = "外食热量统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysCustomerHeatStatistics sysCustomerHeatStatistics)
    {
        return toAjax(sysCustomerHeatStatisticsService.updateSysCustomerHeatStatistics(sysCustomerHeatStatistics));
    }

    /**
     * 删除外食热量统计
     */
    @PreAuthorize("@ss.hasPermi('custom:foodHeatStatistics:remove')")
    @Log(title = "外食热量统计", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysCustomerHeatStatisticsService.deleteSysCustomerHeatStatisticsByIds(ids));
    }

    /**
     * 修改食材热量并计算
     */
    @Log(title = "修改食材热量并计算", businessType = BusinessType.UPDATE)
    @RequestMapping("/addFoodHeatData")
    public AjaxResult addFoodHeatData(@RequestBody SysCustomerHeatStatistics sysCustomerHeatStatistics)
    {
        return toAjax(sysCustomerHeatStatisticsService.calculateCustomerHeat(sysCustomerHeatStatistics));
    }

    /**
     * 查看外食热量统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:foodHeatStatistics:query')")
    @GetMapping(value = "/getNutritionalCalories")
    public AjaxResult getNutritionalCalories(@RequestParam("id") Long id)
    {
        return AjaxResult.success(sysCustomerHeatStatisticsService.getNutritionalCaloriesByCustomerHeatId(id));
    }
}