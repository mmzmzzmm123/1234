package com.gox.web.controller.system;

import java.util.List;

import com.gox.common.utils.uuid.SnowflakesTools;
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
import com.gox.common.annotation.Log;
import com.gox.common.core.controller.BaseController;
import com.gox.common.core.domain.AjaxResult;
import com.gox.common.enums.BusinessType;
import com.gox.system.domain.ArchivalCodeSetting;
import com.gox.system.service.IArchivalCodeSettingService;
import com.gox.common.utils.poi.ExcelUtil;
import com.gox.common.core.page.TableDataInfo;

/**
 * 档号设置Controller
 * 
 * @author gox
 * @date 2021-01-23
 */
@RestController
@RequestMapping("/system/setting")
public class ArchivalCodeSettingController extends BaseController
{
    @Autowired
    private IArchivalCodeSettingService archivalCodeSettingService;

    /**
     * 查询档号设置列表
     */
    @PreAuthorize("@ss.hasPermi('system:setting:list')")
    @GetMapping("/list")
    public TableDataInfo list(ArchivalCodeSetting archivalCodeSetting)
    {
        startPage();
        List<ArchivalCodeSetting> list = archivalCodeSettingService.selectArchivalCodeSettingList(archivalCodeSetting);
        return getDataTable(list);
    }

    /**
     * 导出档号设置列表
     */
    @PreAuthorize("@ss.hasPermi('system:setting:export')")
    @Log(title = "档号设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ArchivalCodeSetting archivalCodeSetting)
    {
        List<ArchivalCodeSetting> list = archivalCodeSettingService.selectArchivalCodeSettingList(archivalCodeSetting);
        ExcelUtil<ArchivalCodeSetting> util = new ExcelUtil<ArchivalCodeSetting>(ArchivalCodeSetting.class);
        return util.exportExcel(list, "setting");
    }

    /**
     * 获取档号设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:setting:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(archivalCodeSettingService.selectArchivalCodeSettingById(id));
    }

    /**
     * 新增档号设置
     */
    @PreAuthorize("@ss.hasPermi('system:setting:add')")
    @Log(title = "档号设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ArchivalCodeSetting archivalCodeSetting)
    {
        archivalCodeSetting.setId(SnowflakesTools.WORKER.nextId());
        return toAjax(archivalCodeSettingService.insertArchivalCodeSetting(archivalCodeSetting));
    }

    /**
     * 修改档号设置
     */
    @PreAuthorize("@ss.hasPermi('system:setting:edit')")
    @Log(title = "档号设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ArchivalCodeSetting archivalCodeSetting)
    {
        return toAjax(archivalCodeSettingService.updateArchivalCodeSetting(archivalCodeSetting));
    }

    /**
     * 删除档号设置
     */
    @PreAuthorize("@ss.hasPermi('system:setting:remove')")
    @Log(title = "档号设置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(archivalCodeSettingService.deleteArchivalCodeSettingByIds(ids));
    }
}
