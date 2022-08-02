package com.ruoyi.flyway.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.flyway.domain.FlywaySchemaHistory;
import com.ruoyi.flyway.service.IFlywaySchemaHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * flyway管理Controller
 * 
 * @author yangwb
 * @date 2022-07-07
 */
@RestController
@RequestMapping("/system/history")
public class FlywaySchemaHistoryController extends BaseController
{
    @Autowired
    private IFlywaySchemaHistoryService flywaySchemaHistoryService;

    /**
     * 查询flyway管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:history:list')")
    @GetMapping("/list")
    public TableDataInfo list(FlywaySchemaHistory flywaySchemaHistory)
    {
        startPage();
        List<FlywaySchemaHistory> list = flywaySchemaHistoryService.selectFlywaySchemaHistoryList(flywaySchemaHistory);
        return getDataTable(list);
    }

    /**
     * 导出flyway管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:history:export')")
    @Log(title = "flyway管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FlywaySchemaHistory flywaySchemaHistory)
    {
        List<FlywaySchemaHistory> list = flywaySchemaHistoryService.selectFlywaySchemaHistoryList(flywaySchemaHistory);
        ExcelUtil<FlywaySchemaHistory> util = new ExcelUtil<FlywaySchemaHistory>(FlywaySchemaHistory.class);
        util.exportExcel(response, list, "flyway管理数据");
    }

    /**
     * 获取flyway管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:history:query')")
    @GetMapping(value = "/{installedRank}")
    public AjaxResult getInfo(@PathVariable("installedRank") Long installedRank)
    {
        return AjaxResult.success(flywaySchemaHistoryService.selectFlywaySchemaHistoryByInstalledRank(installedRank));
    }

    /**
     * 新增flyway管理
     */
    @PreAuthorize("@ss.hasPermi('system:history:add')")
    @Log(title = "flyway管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FlywaySchemaHistory flywaySchemaHistory)
    {
        return toAjax(flywaySchemaHistoryService.insertFlywaySchemaHistory(flywaySchemaHistory));
    }

    /**
     * 修改flyway管理
     */
    @PreAuthorize("@ss.hasPermi('system:history:edit')")
    @Log(title = "flyway管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FlywaySchemaHistory flywaySchemaHistory)
    {
        return toAjax(flywaySchemaHistoryService.updateFlywaySchemaHistory(flywaySchemaHistory));
    }

    /**
     * 删除flyway管理
     */
    @PreAuthorize("@ss.hasPermi('system:history:remove')")
    @Log(title = "flyway管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{installedRanks}")
    public AjaxResult remove(@PathVariable Long[] installedRanks)
    {
        return toAjax(flywaySchemaHistoryService.deleteFlywaySchemaHistoryByInstalledRanks(installedRanks));
    }
}
