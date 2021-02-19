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
import com.stdiet.custom.domain.SysFoodHeatStatistics;
import com.stdiet.custom.service.ISysFoodHeatStatisticsService;
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
    private ISysFoodHeatStatisticsService sysFoodHeatStatisticsService;

    /**
     * 查询外食热量统计列表
     */
    @PreAuthorize("@ss.hasPermi('custom:foodHeatStatistics:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysFoodHeatStatistics sysFoodHeatStatistics)
    {
        startPage();
        List<SysFoodHeatStatistics> list = sysFoodHeatStatisticsService.selectSysFoodHeatStatisticsList(sysFoodHeatStatistics);
        return getDataTable(list);
    }

    /**
     * 导出外食热量统计列表
     */
    @PreAuthorize("@ss.hasPermi('custom:foodHeatStatistics:export')")
    @Log(title = "外食热量统计", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysFoodHeatStatistics sysFoodHeatStatistics)
    {
        List<SysFoodHeatStatistics> list = sysFoodHeatStatisticsService.selectSysFoodHeatStatisticsList(sysFoodHeatStatistics);
        ExcelUtil<SysFoodHeatStatistics> util = new ExcelUtil<SysFoodHeatStatistics>(SysFoodHeatStatistics.class);
        return util.exportExcel(list, "foodHeatStatistics");
    }

    /**
     * 获取外食热量统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:foodHeatStatistics:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysFoodHeatStatisticsService.selectSysFoodHeatStatisticsById(id));
    }

    /**
     * 新增外食热量统计
     */
    @PreAuthorize("@ss.hasPermi('custom:foodHeatStatistics:add')")
    @Log(title = "外食热量统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysFoodHeatStatistics sysFoodHeatStatistics)
    {
        return toAjax(sysFoodHeatStatisticsService.insertSysFoodHeatStatistics(sysFoodHeatStatistics));
    }

    /**
     * 修改外食热量统计
     */
    @PreAuthorize("@ss.hasPermi('custom:foodHeatStatistics:edit')")
    @Log(title = "外食热量统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysFoodHeatStatistics sysFoodHeatStatistics)
    {
        return toAjax(sysFoodHeatStatisticsService.updateSysFoodHeatStatistics(sysFoodHeatStatistics));
    }

    /**
     * 删除外食热量统计
     */
    @PreAuthorize("@ss.hasPermi('custom:foodHeatStatistics:remove')")
    @Log(title = "外食热量统计", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysFoodHeatStatisticsService.deleteSysFoodHeatStatisticsByIds(ids));
    }
}