package com.stdiet.custom.controller;

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
import com.stdiet.custom.domain.SysCustomerHealthy;
import com.stdiet.custom.service.ISysCustomerHealthyService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 客户健康Controller
 *
 * @author xzj
 * @date 2021-01-23
 */
@RestController
@RequestMapping("/customer/healthy")
public class SysCustomerHealthyController extends BaseController
{
    @Autowired
    private ISysCustomerHealthyService sysCustomerHealthyService;

    /**
     * 查询客户健康列表
     */
    @PreAuthorize("@ss.hasPermi('customer:healthy:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysCustomerHealthy sysCustomerHealthy)
    {
        startPage();
        List<SysCustomerHealthy> list = sysCustomerHealthyService.selectSysCustomerHealthyList(sysCustomerHealthy);
        return getDataTable(list);
    }

    /**
     * 导出客户健康列表
     */
    @PreAuthorize("@ss.hasPermi('customer:healthy:export')")
    @Log(title = "客户健康", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysCustomerHealthy sysCustomerHealthy)
    {
        List<SysCustomerHealthy> list = sysCustomerHealthyService.selectSysCustomerHealthyList(sysCustomerHealthy);
        ExcelUtil<SysCustomerHealthy> util = new ExcelUtil<SysCustomerHealthy>(SysCustomerHealthy.class);
        return util.exportExcel(list, "healthy");
    }

    /**
     * 获取客户健康详细信息
     */
    @PreAuthorize("@ss.hasPermi('customer:healthy:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysCustomerHealthyService.selectSysCustomerHealthyById(id));
    }

    /**
     * 新增客户健康
     */
    @PreAuthorize("@ss.hasPermi('customer:healthy:add')")
    @Log(title = "客户健康", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysCustomerHealthy sysCustomerHealthy)
    {
        return toAjax(sysCustomerHealthyService.insertSysCustomerHealthy(sysCustomerHealthy));
    }

    /**
     * 修改客户健康
     */
    @PreAuthorize("@ss.hasPermi('customer:healthy:edit')")
    @Log(title = "客户健康", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysCustomerHealthy sysCustomerHealthy)
    {
        return toAjax(sysCustomerHealthyService.updateSysCustomerHealthy(sysCustomerHealthy));
    }

    /**
     * 删除客户健康
     */
    @PreAuthorize("@ss.hasPermi('customer:healthy:remove')")
    @Log(title = "客户健康", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysCustomerHealthyService.deleteSysCustomerHealthyByIds(ids));
    }
}