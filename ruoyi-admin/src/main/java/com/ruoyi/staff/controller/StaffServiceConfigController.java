package com.ruoyi.staff.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.ruoyi.staff.domain.StaffServiceConfig;
import com.ruoyi.staff.service.IStaffServiceConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 员工服务配置Controller
 *
 * @author Lam
 * @date 2023-09-14
 */
@Api(tags = "admin-员工服务配置")
@RestController
@RequiredArgsConstructor
@RequestMapping("/staff/staffServiceConfig")
public class StaffServiceConfigController extends BaseController {

    private final IStaffServiceConfigService staffServiceConfigService;


    @ApiOperation("查询员工服务配置列表")
    @PreAuthorize("@ss.hasPermi('staff:staffServiceConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(StaffServiceConfig staffServiceConfig) {
        startPage();
        List<StaffServiceConfig> list = staffServiceConfigService.selectStaffServiceConfigList(staffServiceConfig);
        return getDataTable(list);
    }


    @ApiOperation("导出员工服务配置列表")
    @PreAuthorize("@ss.hasPermi('staff:staffServiceConfig:export')")
    @Log(title = "员工服务配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StaffServiceConfig staffServiceConfig) {
        List<StaffServiceConfig> list = staffServiceConfigService.selectStaffServiceConfigList(staffServiceConfig);
        ExcelUtil<StaffServiceConfig> util = new ExcelUtil<StaffServiceConfig>(StaffServiceConfig.class);
        util.exportExcel(response, list, "员工服务配置数据");
    }


    @ApiOperation("获取员工服务配置详细信息")
    @PreAuthorize("@ss.hasPermi('staff:staffServiceConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(staffServiceConfigService.selectStaffServiceConfigById(id));
    }


    @ApiOperation("新增员工服务配置")
    @PreAuthorize("@ss.hasPermi('staff:staffServiceConfig:add')")
    @Log(title = "员工服务配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StaffServiceConfig staffServiceConfig) {
        return toAjax(staffServiceConfigService.insertStaffServiceConfig(staffServiceConfig));
    }


    @ApiOperation("修改员工服务配置")
    @PreAuthorize("@ss.hasPermi('staff:staffServiceConfig:edit')")
    @Log(title = "员工服务配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StaffServiceConfig staffServiceConfig) {
        return toAjax(staffServiceConfigService.updateStaffServiceConfig(staffServiceConfig));
    }


    @ApiOperation("删除员工服务配置")
    @PreAuthorize("@ss.hasPermi('staff:staffServiceConfig:remove')")
    @Log(title = "员工服务配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(staffServiceConfigService.deleteStaffServiceConfigByIds(ids));
    }
}
