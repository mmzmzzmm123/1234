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
import com.ruoyi.system.domain.VehicleUserinfo;
import com.ruoyi.system.service.IVehicleUserinfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 小程序用户信息管理Controller
 *
 * @author carol
 * @date 2024-03-01
 */
@RestController
@Api(tags = "微信小程序用户接口")
@RequestMapping("/api/userinfo")
public class VehicleUserinfoController extends BaseController
{
    @Autowired
    private IVehicleUserinfoService vehicleUserinfoService;

    /**
     * 查询小程序用户信息管理列表
     */
    @ApiOperation("展示小程序用户列表")
    @PreAuthorize("@ss.hasPermi('api:userinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(VehicleUserinfo vehicleUserinfo)
    {
        startPage();
        List<VehicleUserinfo> list = vehicleUserinfoService.selectVehicleUserinfoList(vehicleUserinfo);
        return getDataTable(list);
    }

    /**
     * 导出小程序用户信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('api:userinfo:export')")
    @Log(title = "小程序用户信息管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VehicleUserinfo vehicleUserinfo)
    {
        List<VehicleUserinfo> list = vehicleUserinfoService.selectVehicleUserinfoList(vehicleUserinfo);
        ExcelUtil<VehicleUserinfo> util = new ExcelUtil<VehicleUserinfo>(VehicleUserinfo.class);
        util.exportExcel(response, list, "小程序用户信息管理数据");
    }

    /**
     * 获取小程序用户信息管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('api:userinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(vehicleUserinfoService.selectVehicleUserinfoById(id));
    }

    /**
     * 新增小程序用户信息管理
     */
    @PreAuthorize("@ss.hasPermi('api:userinfo:add')")
    @Log(title = "小程序用户信息管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VehicleUserinfo vehicleUserinfo)
    {
        return toAjax(vehicleUserinfoService.insertVehicleUserinfo(vehicleUserinfo));
    }

    /**
     * 修改小程序用户信息管理
     */
    @PreAuthorize("@ss.hasPermi('api:userinfo:edit')")
    @Log(title = "小程序用户信息管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VehicleUserinfo vehicleUserinfo)
    {
        return toAjax(vehicleUserinfoService.updateVehicleUserinfo(vehicleUserinfo));
    }

    /**
     * 删除小程序用户信息管理
     */
    @PreAuthorize("@ss.hasPermi('api:userinfo:remove')")
    @Log(title = "小程序用户信息管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(vehicleUserinfoService.deleteVehicleUserinfoByIds(ids));
    }
}
