package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

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
import com.ruoyi.system.domain.VehicleBrand;
import com.ruoyi.system.service.IVehicleBrandService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 品牌管理Controller
 *
 * @author carol
 * @date 2024-03-01
 */
@Api(tags = "车辆品牌接口")
@RestController
@RequestMapping("/api/brand")
public class VehicleBrandController extends BaseController
{
    @Autowired
    private IVehicleBrandService vehicleBrandService;

    /**
     * 查询品牌管理列表
     */
    @PreAuthorize("@ss.hasPermi('api:brand:list')")
    @GetMapping("/list")
    @ApiOperation("查询所有品牌")
    public TableDataInfo list(VehicleBrand vehicleBrand)
    {
        startPage();
        List<VehicleBrand> list = vehicleBrandService.selectVehicleBrandList(vehicleBrand);
        return getDataTable(list);
    }

    /**
     * 导出品牌管理列表
     */
    @PreAuthorize("@ss.hasPermi('api:brand:export')")
    @Log(title = "品牌管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VehicleBrand vehicleBrand)
    {
        List<VehicleBrand> list = vehicleBrandService.selectVehicleBrandList(vehicleBrand);
        ExcelUtil<VehicleBrand> util = new ExcelUtil<VehicleBrand>(VehicleBrand.class);
        util.exportExcel(response, list, "品牌管理数据");
    }

    /**
     * 获取品牌管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('api:brand:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(vehicleBrandService.selectVehicleBrandById(id));
    }

    /**
     * 新增品牌管理
     */
    @PreAuthorize("@ss.hasPermi('api:brand:add')")
    @Log(title = "品牌管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VehicleBrand vehicleBrand)
    {
        return toAjax(vehicleBrandService.insertVehicleBrand(vehicleBrand));
    }

    /**
     * 修改品牌管理
     */
    @PreAuthorize("@ss.hasPermi('api:brand:edit')")
    @Log(title = "品牌管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VehicleBrand vehicleBrand)
    {
        return toAjax(vehicleBrandService.updateVehicleBrand(vehicleBrand));
    }

    /**
     * 删除品牌管理
     */
    @PreAuthorize("@ss.hasPermi('api:brand:remove')")
    @Log(title = "品牌管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(vehicleBrandService.deleteVehicleBrandByIds(ids));
    }
}
