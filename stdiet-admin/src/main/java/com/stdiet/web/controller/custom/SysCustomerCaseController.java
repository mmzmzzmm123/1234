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
import com.stdiet.custom.domain.SysCustomerCase;
import com.stdiet.custom.service.ISysCustomerCaseService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 客户案例管理Controller
 *
 * @author xiezhijun
 * @date 2021-03-04
 */
@RestController
@RequestMapping("/custom/customerCase")
public class SysCustomerCaseController extends BaseController
{
    @Autowired
    private ISysCustomerCaseService sysCustomerCaseService;

    /**
     * 查询客户案例管理列表
     */
    @PreAuthorize("@ss.hasPermi('custom:customerCase:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysCustomerCase sysCustomerCase)
    {
        startPage();
        List<SysCustomerCase> list = sysCustomerCaseService.selectSysCustomerCaseList(sysCustomerCase);
        return getDataTable(list);
    }

    /**
     * 导出客户案例管理列表
     */
    @PreAuthorize("@ss.hasPermi('custom:customerCase:export')")
    @Log(title = "客户案例管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysCustomerCase sysCustomerCase)
    {
        List<SysCustomerCase> list = sysCustomerCaseService.selectSysCustomerCaseList(sysCustomerCase);
        ExcelUtil<SysCustomerCase> util = new ExcelUtil<SysCustomerCase>(SysCustomerCase.class);
        return util.exportExcel(list, "customerCase");
    }

    /**
     * 获取客户案例管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:customerCase:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysCustomerCaseService.selectSysCustomerCaseById(id));
    }

    /**
     * 新增客户案例管理
     */
    @PreAuthorize("@ss.hasPermi('custom:customerCase:add')")
    @Log(title = "客户案例管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysCustomerCase sysCustomerCase)
    {
        return toAjax(sysCustomerCaseService.insertSysCustomerCase(sysCustomerCase));
    }

    /**
     * 修改客户案例管理
     */
    @PreAuthorize("@ss.hasPermi('custom:customerCase:edit')")
    @Log(title = "客户案例管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysCustomerCase sysCustomerCase)
    {
        return toAjax(sysCustomerCaseService.updateSysCustomerCase(sysCustomerCase));
    }

    /**
     * 删除客户案例管理
     */
    @PreAuthorize("@ss.hasPermi('custom:customerCase:remove')")
    @Log(title = "客户案例管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysCustomerCaseService.deleteSysCustomerCaseByIds(ids));
    }
}