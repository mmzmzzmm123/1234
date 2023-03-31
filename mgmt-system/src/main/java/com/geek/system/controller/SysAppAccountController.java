package com.geek.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.geek.common.annotation.Log;
import com.geek.common.core.controller.BaseController;
import com.geek.common.core.domain.AjaxResult;
import com.geek.common.enums.BusinessType;
import com.geek.system.domain.SysAppAccount;
import com.geek.system.service.ISysAppAccountService;
import com.geek.common.utils.poi.ExcelUtil;
import com.geek.common.core.page.TableDataInfo;

/**
 * 系统APP账号Controller
 * 
 * @author xuek
 * @date 2023-03-29
 */
@RestController
@RequestMapping("/system/account")
public class SysAppAccountController extends BaseController{

    @Autowired
    private ISysAppAccountService sysAppAccountService;

    /**
     * 查询系统APP账号列表
     */
    @PreAuthorize("@ss.hasPermi('system:account:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysAppAccount sysAppAccount){

        startPage();
        List<SysAppAccount> list = sysAppAccountService.selectSysAppAccountList(sysAppAccount);
        return getDataTable(list);
    }

    /**
     * 导出系统APP账号列表
     */
    @PreAuthorize("@ss.hasPermi('system:account:export')")
    @Log(title = "系统APP账号", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysAppAccount sysAppAccount){

        List<SysAppAccount> list = sysAppAccountService.selectSysAppAccountList(sysAppAccount);
        ExcelUtil<SysAppAccount> util = new ExcelUtil<SysAppAccount>(SysAppAccount.class);
        util.exportExcel(response, list, "系统APP账号数据");
    }

    /**
     * 获取系统APP账号详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:account:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id){

        return success(sysAppAccountService.selectSysAppAccountById(id));
    }

    /**
     * 新增系统APP账号
     */
    @PreAuthorize("@ss.hasPermi('system:account:add')")
    @Log(title = "系统APP账号", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysAppAccount sysAppAccount){

        return toAjax(sysAppAccountService.insertSysAppAccount(sysAppAccount));
    }

    /**
     * 修改系统APP账号
     */
    @PreAuthorize("@ss.hasPermi('system:account:edit')")
    @Log(title = "系统APP账号", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysAppAccount sysAppAccount){

        return toAjax(sysAppAccountService.updateSysAppAccount(sysAppAccount));
    }

    /**
     * 删除系统APP账号
     */
    @PreAuthorize("@ss.hasPermi('system:account:remove')")
    @Log(title = "系统APP账号", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids){

        return toAjax(sysAppAccountService.deleteSysAppAccountByIds(ids));
    }
}
