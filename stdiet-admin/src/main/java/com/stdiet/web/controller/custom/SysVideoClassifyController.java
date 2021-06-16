package com.stdiet.web.controller.custom;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.custom.domain.SysVideoClassify;
import com.stdiet.custom.service.ISysVideoClassifyService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 视频分类Controller
 *
 * @author xzj
 * @date 2021-05-05
 */
@RestController
@RequestMapping("/custom/videoClassify")
public class SysVideoClassifyController extends BaseController
{
    @Autowired
    private ISysVideoClassifyService sysVideoClassifyService;

    /**
     * 查询视频分类列表
     */
    @PreAuthorize("@ss.hasPermi('custom:videoClassify:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysVideoClassify sysVideoClassify)
    {
        //startPage();
        //sysVideoClassify.setParentId(0L);
        List<SysVideoClassify> list = sysVideoClassifyService.selectSysVideoClassifyList(sysVideoClassify);
        return getDataTable(list);
    }

    /**
     * 导出视频分类列表
     */
    @PreAuthorize("@ss.hasPermi('custom:videoClassify:export')")
    @Log(title = "视频分类", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysVideoClassify sysVideoClassify)
    {
        List<SysVideoClassify> list = sysVideoClassifyService.selectSysVideoClassifyList(sysVideoClassify);
        ExcelUtil<SysVideoClassify> util = new ExcelUtil<SysVideoClassify>(SysVideoClassify.class);
        return util.exportExcel(list, "videoClassify");
    }

    /**
     * 获取视频分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:videoClassify:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysVideoClassifyService.selectSysVideoClassifyById(id));
    }

    /**
     * 新增视频分类
     */
    @PreAuthorize("@ss.hasPermi('custom:videoClassify:add')")
    @Log(title = "视频分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysVideoClassify sysVideoClassify)
    {
        return toAjax(sysVideoClassifyService.insertSysVideoClassify(sysVideoClassify));
    }

    /**
     * 修改视频分类
     */
    @PreAuthorize("@ss.hasPermi('custom:videoClassify:edit')")
    @Log(title = "视频分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysVideoClassify sysVideoClassify)
    {
        return toAjax(sysVideoClassifyService.updateSysVideoClassify(sysVideoClassify));
    }

    /**
     * 删除视频分类
     */
    @PreAuthorize("@ss.hasPermi('custom:videoClassify:remove')")
    @Log(title = "视频分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysVideoClassifyService.deleteSysVideoClassifyByIds(ids));
    }

    /**
     * 查询视频分类列表
     */
    @GetMapping("/getAllClassify")
    public AjaxResult getAllClassify(SysVideoClassify sysVideoClassify)
    {
        List<SysVideoClassify> list = sysVideoClassifyService.getAllClassify(sysVideoClassify);
        return AjaxResult.success(list);
    }
}