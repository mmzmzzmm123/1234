package com.stdiet.web.controller.custom;

import java.util.List;
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
import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.custom.domain.SysNutritionalVideo;
import com.stdiet.custom.service.ISysNutritionalVideoService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 营养视频Controller
 *
 * @author xzj
 * @date 2021-04-29
 */
@RestController
@RequestMapping("/custom/nutritionalVideo")
public class SysNutritionalVideoController extends BaseController
{
    @Autowired
    private ISysNutritionalVideoService sysNutritionalVideoService;

    /**
     * 查询营养视频列表
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionalVideo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysNutritionalVideo sysNutritionalVideo)
    {
        startPage();
        List<SysNutritionalVideo> list = sysNutritionalVideoService.selectSysNutritionalVideoList(sysNutritionalVideo);
        return getDataTable(list);
    }

    /**
     * 导出营养视频列表
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionalVideo:export')")
    @Log(title = "营养视频", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysNutritionalVideo sysNutritionalVideo)
    {
        List<SysNutritionalVideo> list = sysNutritionalVideoService.selectSysNutritionalVideoList(sysNutritionalVideo);
        ExcelUtil<SysNutritionalVideo> util = new ExcelUtil<SysNutritionalVideo>(SysNutritionalVideo.class);
        return util.exportExcel(list, "nutritionalVideo");
    }

    /**
     * 获取营养视频详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionalVideo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysNutritionalVideoService.selectSysNutritionalVideoById(id));
    }

    /**
     * 新增营养视频
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionalVideo:add')")
    @Log(title = "营养视频", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysNutritionalVideo sysNutritionalVideo)
    {
        return toAjax(sysNutritionalVideoService.insertSysNutritionalVideo(sysNutritionalVideo));
    }

    /**
     * 修改营养视频
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionalVideo:edit')")
    @Log(title = "营养视频", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysNutritionalVideo sysNutritionalVideo)
    {
        return toAjax(sysNutritionalVideoService.updateSysNutritionalVideo(sysNutritionalVideo));
    }

    /**
     * 删除营养视频
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionalVideo:remove')")
    @Log(title = "营养视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysNutritionalVideoService.deleteSysNutritionalVideoByIds(ids));
    }
}