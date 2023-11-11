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
import com.ruoyi.staff.domain.StaffLevelConfig;
import com.ruoyi.staff.service.IStaffLevelConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 员工等级配置Controller
 *
 * @author Lam
 * @date 2023-09-14
 */
@Api(tags = "admin-员工等级配置")
@RestController
@RequiredArgsConstructor
@RequestMapping("/staff/staffLevelConfig")
public class StaffLevelConfigController extends BaseController {

    private final IStaffLevelConfigService staffLevelConfigService;


    @ApiOperation("查询员工等级配置列表")
    @PreAuthorize("@ss.hasPermi('staff:staffLevelConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(StaffLevelConfig staffLevelConfig) {
        startPage();
        List<StaffLevelConfig> list = staffLevelConfigService.selectStaffLevelConfigList(staffLevelConfig);
        return getDataTable(list);
    }


    @ApiOperation("导出员工等级配置列表")
    @PreAuthorize("@ss.hasPermi('staff:staffLevelConfig:export')")
    @Log(title = "员工等级配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StaffLevelConfig staffLevelConfig) {
        List<StaffLevelConfig> list = staffLevelConfigService.selectStaffLevelConfigList(staffLevelConfig);
        ExcelUtil<StaffLevelConfig> util = new ExcelUtil<StaffLevelConfig>(StaffLevelConfig.class);
        util.exportExcel(response, list, "员工等级配置数据");
    }


    @ApiOperation("获取员工等级配置详细信息")
    @PreAuthorize("@ss.hasPermi('staff:staffLevelConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(staffLevelConfigService.selectStaffLevelConfigById(id));
    }


    @ApiOperation("新增员工等级配置")
    @PreAuthorize("@ss.hasPermi('staff:staffLevelConfig:add')")
    @Log(title = "员工等级配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StaffLevelConfig staffLevelConfig) {
        return toAjax(staffLevelConfigService.insertStaffLevelConfig(staffLevelConfig));
    }


    @ApiOperation("修改员工等级配置")
    @PreAuthorize("@ss.hasPermi('staff:staffLevelConfig:edit')")
    @Log(title = "员工等级配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StaffLevelConfig staffLevelConfig) {
        return toAjax(staffLevelConfigService.updateStaffLevelConfig(staffLevelConfig));
    }


    @ApiOperation("删除员工等级配置")
    @PreAuthorize("@ss.hasPermi('staff:staffLevelConfig:remove')")
    @Log(title = "员工等级配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(staffLevelConfigService.deleteStaffLevelConfigByIds(ids));
    }
}
