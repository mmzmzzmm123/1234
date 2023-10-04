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
import com.ruoyi.platform.domain.PlatformRechargeConfig;
import com.ruoyi.platform.service.IPlatformRechargeConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 平台充值配置Controller
 *
 * @author Lam
 * @date 2023-10-04
 */
@Api(tags = "admin-平台充值配置")
@RestController
@RequiredArgsConstructor
@RequestMapping("/platform/platformRechargeConfig")
public class PlatformRechargeConfigController extends BaseController {

    private final IPlatformRechargeConfigService platformRechargeConfigService;


    @ApiOperation("查询平台充值配置列表")
    @PreAuthorize("@ss.hasPermi('platform:platformRechargeConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlatformRechargeConfig platformRechargeConfig) {
        startPage();
        List<PlatformRechargeConfig> list = platformRechargeConfigService.selectPlatformRechargeConfigList(platformRechargeConfig);
        return getDataTable(list);
    }


    @ApiOperation("导出平台充值配置列表")
    @PreAuthorize("@ss.hasPermi('platform:platformRechargeConfig:export')")
    @Log(title = "平台充值配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlatformRechargeConfig platformRechargeConfig) {
        List<PlatformRechargeConfig> list = platformRechargeConfigService.selectPlatformRechargeConfigList(platformRechargeConfig);
        ExcelUtil<PlatformRechargeConfig> util = new ExcelUtil<PlatformRechargeConfig>(PlatformRechargeConfig.class);
        util.exportExcel(response, list, "平台充值配置数据");
    }


    @ApiOperation("获取平台充值配置详细信息")
    @PreAuthorize("@ss.hasPermi('platform:platformRechargeConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(platformRechargeConfigService.selectPlatformRechargeConfigById(id));
    }


    @ApiOperation("新增平台充值配置")
    @PreAuthorize("@ss.hasPermi('platform:platformRechargeConfig:add')")
    @Log(title = "平台充值配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlatformRechargeConfig platformRechargeConfig) {
        return toAjax(platformRechargeConfigService.insertPlatformRechargeConfig(platformRechargeConfig));
    }


    @ApiOperation("修改平台充值配置")
    @PreAuthorize("@ss.hasPermi('platform:platformRechargeConfig:edit')")
    @Log(title = "平台充值配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlatformRechargeConfig platformRechargeConfig) {
        return toAjax(platformRechargeConfigService.updatePlatformRechargeConfig(platformRechargeConfig));
    }


    @ApiOperation("删除平台充值配置")
    @PreAuthorize("@ss.hasPermi('platform:platformRechargeConfig:remove')")
    @Log(title = "平台充值配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(platformRechargeConfigService.deletePlatformRechargeConfigByIds(ids));
    }
}
