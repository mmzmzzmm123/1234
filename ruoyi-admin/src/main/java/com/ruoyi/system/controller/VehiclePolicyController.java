package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
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
import com.ruoyi.system.domain.VehiclePolicy;
import com.ruoyi.system.service.IVehiclePolicyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 政策信息管理Controller
 *
 * @author carol
 * @date 2024-03-01
 */
@Api(tags = "相关政策接口")
@RestController
@RequestMapping("/api/policy")
public class VehiclePolicyController extends BaseController
{
    @Autowired
    private IVehiclePolicyService vehiclePolicyService;

    /**
     * 查询政策信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('api:policy:list')")
    @GetMapping("/list")
    public TableDataInfo list(VehiclePolicy vehiclePolicy)
    {
        startPage();
        List<VehiclePolicy> list = vehiclePolicyService.selectVehiclePolicyList(vehiclePolicy);
        return getDataTable(list);
    }

    /**
     * 导出政策信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('api:policy:export')")
    @Log(title = "政策信息管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VehiclePolicy vehiclePolicy)
    {
        List<VehiclePolicy> list = vehiclePolicyService.selectVehiclePolicyList(vehiclePolicy);
        ExcelUtil<VehiclePolicy> util = new ExcelUtil<VehiclePolicy>(VehiclePolicy.class);
        util.exportExcel(response, list, "政策信息管理数据");
    }

    /**
     * 获取政策信息管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('api:policy:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(vehiclePolicyService.selectVehiclePolicyById(id));
    }

    /**
     * 新增政策信息管理
     */
    @PreAuthorize("@ss.hasPermi('api:policy:add')")
    @Log(title = "政策信息管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VehiclePolicy vehiclePolicy)
    {
        return toAjax(vehiclePolicyService.insertVehiclePolicy(vehiclePolicy));
    }

    /**
     * 修改政策信息管理
     */
    @PreAuthorize("@ss.hasPermi('api:policy:edit')")
    @Log(title = "政策信息管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VehiclePolicy vehiclePolicy)
    {
        return toAjax(vehiclePolicyService.updateVehiclePolicy(vehiclePolicy));
    }

    /**
     * 删除政策信息管理
     */
    @PreAuthorize("@ss.hasPermi('api:policy:remove')")
    @Log(title = "政策信息管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(vehiclePolicyService.deleteVehiclePolicyByIds(ids));
    }
}
