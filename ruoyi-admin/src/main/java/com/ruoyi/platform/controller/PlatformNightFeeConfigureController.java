package com.ruoyi.platform.controller;

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
import com.ruoyi.platform.domain.PlatformNightFeeConfigure;
import com.ruoyi.platform.service.IPlatformNightFeeConfigureService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 夜间费用配置Controller
 *
 * @author Lam
 * @date 2023-09-13
 */
@Api(tags = "admin-夜间费用配置")
@RestController
@RequiredArgsConstructor
@RequestMapping("/platform/platformNightFeeConfigure")
public class PlatformNightFeeConfigureController extends BaseController {

    private final IPlatformNightFeeConfigureService platformNightFeeConfigureService;


    @ApiOperation("查询夜间费用配置列表")
    @PreAuthorize("@ss.hasPermi('platform:platformNightFeeConfigure:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlatformNightFeeConfigure platformNightFeeConfigure) {
        startPage();
        List<PlatformNightFeeConfigure> list = platformNightFeeConfigureService.selectPlatformNightFeeConfigureList(platformNightFeeConfigure);
        return getDataTable(list);
    }


    @ApiOperation("导出夜间费用配置列表")
    @PreAuthorize("@ss.hasPermi('platform:platformNightFeeConfigure:export')")
    @Log(title = "夜间费用配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlatformNightFeeConfigure platformNightFeeConfigure) {
        List<PlatformNightFeeConfigure> list = platformNightFeeConfigureService.selectPlatformNightFeeConfigureList(platformNightFeeConfigure);
        ExcelUtil<PlatformNightFeeConfigure> util = new ExcelUtil<PlatformNightFeeConfigure>(PlatformNightFeeConfigure.class);
        util.exportExcel(response, list, "夜间费用配置数据");
    }


    @ApiOperation("获取夜间费用配置详细信息")
    @PreAuthorize("@ss.hasPermi('platform:platformNightFeeConfigure:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(platformNightFeeConfigureService.selectPlatformNightFeeConfigureById(id));
    }


    @ApiOperation("新增夜间费用配置")
    @PreAuthorize("@ss.hasPermi('platform:platformNightFeeConfigure:add')")
    @Log(title = "夜间费用配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlatformNightFeeConfigure platformNightFeeConfigure) {
        return toAjax(platformNightFeeConfigureService.insertPlatformNightFeeConfigure(platformNightFeeConfigure));
    }


    @ApiOperation("修改夜间费用配置")
    @PreAuthorize("@ss.hasPermi('platform:platformNightFeeConfigure:edit')")
    @Log(title = "夜间费用配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlatformNightFeeConfigure platformNightFeeConfigure) {
        return toAjax(platformNightFeeConfigureService.updatePlatformNightFeeConfigure(platformNightFeeConfigure));
    }


    @ApiOperation("删除夜间费用配置")
    @PreAuthorize("@ss.hasPermi('platform:platformNightFeeConfigure:remove')")
    @Log(title = "夜间费用配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(platformNightFeeConfigureService.deletePlatformNightFeeConfigureByIds(ids));
    }
}
