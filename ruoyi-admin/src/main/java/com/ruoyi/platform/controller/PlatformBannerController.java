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
import com.ruoyi.platform.domain.PlatformBanner;
import com.ruoyi.platform.service.IPlatformBannerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 平台广告图Controller
 *
 * @author Lam
 * @date 2023-09-13
 */
@Api(tags = "admin-平台广告图")
@RestController
@RequiredArgsConstructor
@RequestMapping("/platform/platformBanner")
public class PlatformBannerController extends BaseController {

    private final IPlatformBannerService platformBannerService;


    @ApiOperation("查询平台广告图列表")
    @PreAuthorize("@ss.hasPermi('platform:platformBanner:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlatformBanner platformBanner) {
        startPage();
        List<PlatformBanner> list = platformBannerService.selectPlatformBannerList(platformBanner);
        return getDataTable(list);
    }


    @ApiOperation("导出平台广告图列表")
    @PreAuthorize("@ss.hasPermi('platform:platformBanner:export')")
    @Log(title = "平台广告图", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlatformBanner platformBanner) {
        List<PlatformBanner> list = platformBannerService.selectPlatformBannerList(platformBanner);
        ExcelUtil<PlatformBanner> util = new ExcelUtil<PlatformBanner>(PlatformBanner.class);
        util.exportExcel(response, list, "平台广告图数据");
    }


    @ApiOperation("获取平台广告图详细信息")
    @PreAuthorize("@ss.hasPermi('platform:platformBanner:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(platformBannerService.selectPlatformBannerById(id));
    }


    @ApiOperation("新增平台广告图")
    @PreAuthorize("@ss.hasPermi('platform:platformBanner:add')")
    @Log(title = "平台广告图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlatformBanner platformBanner) {
        return toAjax(platformBannerService.insertPlatformBanner(platformBanner));
    }


    @ApiOperation("修改平台广告图")
    @PreAuthorize("@ss.hasPermi('platform:platformBanner:edit')")
    @Log(title = "平台广告图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlatformBanner platformBanner) {
        return toAjax(platformBannerService.updatePlatformBanner(platformBanner));
    }


    @ApiOperation("删除平台广告图")
    @PreAuthorize("@ss.hasPermi('platform:platformBanner:remove')")
    @Log(title = "平台广告图", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(platformBannerService.deletePlatformBannerByIds(ids));
    }
}
