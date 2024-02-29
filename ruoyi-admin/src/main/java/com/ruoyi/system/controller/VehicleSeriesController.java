package com.ruoyi.system.controller;

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
import com.ruoyi.system.domain.VehicleSeries;
import com.ruoyi.system.service.IVehicleSeriesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 车系管理Controller
 * 
 * @author carol
 * @date 2024-02-29
 */
@RestController
@RequestMapping("/system/series")
public class VehicleSeriesController extends BaseController
{
    @Autowired
    private IVehicleSeriesService vehicleSeriesService;

    /**
     * 查询车系管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:series:list')")
    @GetMapping("/list")
    public TableDataInfo list(VehicleSeries vehicleSeries)
    {
        startPage();
        List<VehicleSeries> list = vehicleSeriesService.selectVehicleSeriesList(vehicleSeries);
        return getDataTable(list);
    }

    /**
     * 导出车系管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:series:export')")
    @Log(title = "车系管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VehicleSeries vehicleSeries)
    {
        List<VehicleSeries> list = vehicleSeriesService.selectVehicleSeriesList(vehicleSeries);
        ExcelUtil<VehicleSeries> util = new ExcelUtil<VehicleSeries>(VehicleSeries.class);
        util.exportExcel(response, list, "车系管理数据");
    }

    /**
     * 获取车系管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:series:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(vehicleSeriesService.selectVehicleSeriesById(id));
    }

    /**
     * 新增车系管理
     */
    @PreAuthorize("@ss.hasPermi('system:series:add')")
    @Log(title = "车系管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VehicleSeries vehicleSeries)
    {
        return toAjax(vehicleSeriesService.insertVehicleSeries(vehicleSeries));
    }

    /**
     * 修改车系管理
     */
    @PreAuthorize("@ss.hasPermi('system:series:edit')")
    @Log(title = "车系管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VehicleSeries vehicleSeries)
    {
        return toAjax(vehicleSeriesService.updateVehicleSeries(vehicleSeries));
    }

    /**
     * 删除车系管理
     */
    @PreAuthorize("@ss.hasPermi('system:series:remove')")
    @Log(title = "车系管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(vehicleSeriesService.deleteVehicleSeriesByIds(ids));
    }
}
