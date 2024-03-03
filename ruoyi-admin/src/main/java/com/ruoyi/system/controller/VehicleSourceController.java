package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.VehicleBrand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import com.ruoyi.system.domain.VehicleSource;
import com.ruoyi.system.service.IVehicleSourceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 车源管理Controller
 *
 * @author carol
 * @date 2024-03-01
 */
@Api(tags ="车源接口")
@RestController
@RequestMapping("/api/source")
public class VehicleSourceController extends BaseController
{
    @Autowired
    private IVehicleSourceService vehicleSourceService;

    /**
     * 查询车源管理列表
     */
    @PreAuthorize("@ss.hasPermi('api:source:list')")
    @GetMapping("/list")
    public TableDataInfo list(VehicleSource vehicleSource)
    {
        startPage();
        List<VehicleSource> list = vehicleSourceService.selectVehicleSourceList(vehicleSource);
        return getDataTable(list);
    }

    /**
     * 导出车源管理列表
     */
    @PreAuthorize("@ss.hasPermi('api:source:export')")
    @Log(title = "车源管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VehicleSource vehicleSource)
    {
        List<VehicleSource> list = vehicleSourceService.selectVehicleSourceList(vehicleSource);
        ExcelUtil<VehicleSource> util = new ExcelUtil<VehicleSource>(VehicleSource.class);
        util.exportExcel(response, list, "车源管理数据");
    }

    /**
     * 获取车源管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('api:source:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(vehicleSourceService.selectVehicleSourceById(id));
    }

    /**
     * 新增车源管理
     */
    @PreAuthorize("@ss.hasPermi('api:source:add')")
    @Log(title = "车源管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VehicleSource vehicleSource)
    {
        return toAjax(vehicleSourceService.insertVehicleSource(vehicleSource));
    }

    /**
     * 修改车源管理
     */
    @PreAuthorize("@ss.hasPermi('api:source:edit')")
    @Log(title = "车源管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VehicleSource vehicleSource)
    {
        return toAjax(vehicleSourceService.updateVehicleSource(vehicleSource));
    }

    /**
     * 删除车源管理
     */
    @PreAuthorize("@ss.hasPermi('api:source:remove')")
    @Log(title = "车源管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(vehicleSourceService.deleteVehicleSourceByIds(ids));
    }

    @ApiOperation("查询现有品牌")
    @GetMapping("/brands")
    public R<List<VehicleBrand>> getBrands(){
        List<VehicleBrand> brands =  vehicleSourceService.listGroupByBrandId();
        R<List<VehicleBrand>> r = new R<>();
        r.setData(brands);
        return r;
    }

    @ApiOperation("根据车辆id查询具体的车辆信息")
    @GetMapping("/getinfo/{id}")
    public R getInfoById(@PathVariable("id")Long id){
        return vehicleSourceService.getInfoById(id);
    }
}
