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
import com.stdiet.custom.domain.SysPhysicalSigns;
import com.stdiet.custom.service.ISysPhysicalSignsService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 体征Controller
 * 
 * @author wonder
 * @date 2020-12-23
 */
@RestController
@RequestMapping("/custom/physicalSigns")
public class SysPhysicalSignsController extends BaseController
{
    @Autowired
    private ISysPhysicalSignsService sysPhysicalSignsService;

    /**
     * 查询体征列表
     */
    @PreAuthorize("@ss.hasPermi('custom:physicalSigns:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPhysicalSigns sysPhysicalSigns)
    {
        startPage();
        List<SysPhysicalSigns> list = sysPhysicalSignsService.selectSysPhysicalSignsList(sysPhysicalSigns);
        return getDataTable(list);
    }

    /**
     * 导出体征列表
     */
    @PreAuthorize("@ss.hasPermi('custom:physicalSigns:export')")
    @Log(title = "体征", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysPhysicalSigns sysPhysicalSigns)
    {
        List<SysPhysicalSigns> list = sysPhysicalSignsService.selectSysPhysicalSignsList(sysPhysicalSigns);
        ExcelUtil<SysPhysicalSigns> util = new ExcelUtil<SysPhysicalSigns>(SysPhysicalSigns.class);
        return util.exportExcel(list, "physicalSigns");
    }

    /**
     * 获取体征详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:physicalSigns:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysPhysicalSignsService.selectSysPhysicalSignsById(id));
    }

    /**
     * 新增体征
     */
    @PreAuthorize("@ss.hasPermi('custom:physicalSigns:add')")
    @Log(title = "体征", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysPhysicalSigns sysPhysicalSigns)
    {
        return toAjax(sysPhysicalSignsService.insertSysPhysicalSigns(sysPhysicalSigns));
    }

    /**
     * 修改体征
     */
    @PreAuthorize("@ss.hasPermi('custom:physicalSigns:edit')")
    @Log(title = "体征", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysPhysicalSigns sysPhysicalSigns)
    {
        return toAjax(sysPhysicalSignsService.updateSysPhysicalSigns(sysPhysicalSigns));
    }

    /**
     * 删除体征
     */
    @PreAuthorize("@ss.hasPermi('custom:physicalSigns:remove')")
    @Log(title = "体征", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysPhysicalSignsService.deleteSysPhysicalSignsByIds(ids));
    }
}