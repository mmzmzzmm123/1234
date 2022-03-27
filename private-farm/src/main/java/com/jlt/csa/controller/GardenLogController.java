package com.jlt.csa.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.jlt.csa.domain.GardenLog;
import com.jlt.csa.service.IGardenLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 菜地日志Controller
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
@RestController
@RequestMapping("/csa/log")
public class GardenLogController extends BaseController
{
    @Autowired
    private IGardenLogService gardenLogService;

    /**
     * 查询菜地日志列表
     */
    @PreAuthorize("@ss.hasPermi('csa:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(GardenLog gardenLog)
    {
        startPage();
        List<GardenLog> list = gardenLogService.selectGardenLogList(gardenLog);
        return getDataTable(list);
    }

    /**
     * 导出菜地日志列表
     */
    @PreAuthorize("@ss.hasPermi('csa:log:export')")
    @Log(title = "菜地日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GardenLog gardenLog)
    {
        List<GardenLog> list = gardenLogService.selectGardenLogList(gardenLog);
        ExcelUtil<GardenLog> util = new ExcelUtil<GardenLog>(GardenLog.class);
        util.exportExcel(response, list, "菜地日志数据");
    }

    /**
     * 获取菜地日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('csa:log:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(gardenLogService.selectGardenLogById(id));
    }

    /**
     * 新增菜地日志
     */
    @PreAuthorize("@ss.hasPermi('csa:log:add')")
    @Log(title = "菜地日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GardenLog gardenLog)
    {
        return toAjax(gardenLogService.insertGardenLog(gardenLog));
    }

    /**
     * 修改菜地日志
     */
    @PreAuthorize("@ss.hasPermi('csa:log:edit')")
    @Log(title = "菜地日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GardenLog gardenLog)
    {
        return toAjax(gardenLogService.updateGardenLog(gardenLog));
    }

    /**
     * 删除菜地日志
     */
    @PreAuthorize("@ss.hasPermi('csa:log:remove')")
    @Log(title = "菜地日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(gardenLogService.deleteGardenLogByIds(ids));
    }
}
