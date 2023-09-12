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
import com.ruoyi.platform.domain.PlatformGift;
import com.ruoyi.platform.service.IPlatformGiftService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 平台礼物管理Controller
 *
 * @author Lam
 * @date 2023-09-12
 */
@Api(tags = "admin-平台礼物管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/platform/platformGift")
public class PlatformGiftController extends BaseController {

    private final IPlatformGiftService platformGiftService;


    @ApiOperation("查询平台礼物管理列表")
    @PreAuthorize("@ss.hasPermi('platform:platformGift:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlatformGift platformGift) {
        startPage();
        List<PlatformGift> list = platformGiftService.selectPlatformGiftList(platformGift);
        return getDataTable(list);
    }


    @ApiOperation("导出平台礼物管理列表")
    @PreAuthorize("@ss.hasPermi('platform:platformGift:export')")
    @Log(title = "平台礼物管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlatformGift platformGift) {
        List<PlatformGift> list = platformGiftService.selectPlatformGiftList(platformGift);
        ExcelUtil<PlatformGift> util = new ExcelUtil<PlatformGift>(PlatformGift.class);
        util.exportExcel(response, list, "平台礼物管理数据");
    }


    @ApiOperation("获取平台礼物管理详细信息")
    @PreAuthorize("@ss.hasPermi('platform:platformGift:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(platformGiftService.selectPlatformGiftById(id));
    }


    @ApiOperation("新增平台礼物管理")
    @PreAuthorize("@ss.hasPermi('platform:platformGift:add')")
    @Log(title = "平台礼物管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlatformGift platformGift) {
        return toAjax(platformGiftService.insertPlatformGift(platformGift));
    }


    @ApiOperation("修改平台礼物管理")
    @PreAuthorize("@ss.hasPermi('platform:platformGift:edit')")
    @Log(title = "平台礼物管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlatformGift platformGift) {
        return toAjax(platformGiftService.updatePlatformGift(platformGift));
    }


    @ApiOperation("删除平台礼物管理")
    @PreAuthorize("@ss.hasPermi('platform:platformGift:remove')")
    @Log(title = "平台礼物管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(platformGiftService.deletePlatformGiftByIds(ids));
    }
}
