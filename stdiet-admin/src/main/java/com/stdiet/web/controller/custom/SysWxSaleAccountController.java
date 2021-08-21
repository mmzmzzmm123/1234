package com.stdiet.web.controller.custom;

import java.util.List;

import com.stdiet.common.utils.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.custom.domain.SysWxSaleAccount;
import com.stdiet.custom.service.ISysWxSaleAccountService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 微信账号Controller
 *
 * @author xiezhijun
 * @date 2021-02-03
 */
@RestController
@RequestMapping("/custom/wxAccount")
public class SysWxSaleAccountController extends BaseController
{
    @Autowired
    private ISysWxSaleAccountService sysWxSaleAccountService;

    /**
     * 查询微信账号列表
     */
    @PreAuthorize("@ss.hasPermi('custom:wxAccount:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysWxSaleAccount sysWxSaleAccount, @RequestParam(value = "pageFlag", required = false, defaultValue = "true")boolean pageFlag)
    {
        if(pageFlag){
            startPage();
        }
        List<SysWxSaleAccount> list = sysWxSaleAccountService.selectSysWxSaleAccountList(sysWxSaleAccount);
        return getDataTable(list);
    }

    /**
     * 导出微信账号列表
     */
    @PreAuthorize("@ss.hasPermi('custom:wxAccount:export')")
    @Log(title = "微信账号", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysWxSaleAccount sysWxSaleAccount)
    {
        List<SysWxSaleAccount> list = sysWxSaleAccountService.selectSysWxSaleAccountList(sysWxSaleAccount);
        ExcelUtil<SysWxSaleAccount> util = new ExcelUtil<SysWxSaleAccount>(SysWxSaleAccount.class);
        return util.exportExcel(list, "wxAccount");
    }

    /**
     * 获取微信账号详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:wxAccount:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysWxSaleAccountService.selectSysWxSaleAccountById(id));
    }

    /**
     * 新增微信账号
     */
    @PreAuthorize("@ss.hasPermi('custom:wxAccount:add')")
    @Log(title = "微信账号", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysWxSaleAccount sysWxSaleAccount)
    {
        if(StringUtils.isNotEmpty(sysWxSaleAccount.getWxAccount())){
            if(sysWxSaleAccountService.selectWxAccountByAccountOrPhone(sysWxSaleAccount.getWxAccount(), 0) != null){
                return AjaxResult.error("微信号已存在，无法重复添加");
            }
        }
        if(StringUtils.isNotEmpty(sysWxSaleAccount.getWxPhone())){
            if(sysWxSaleAccountService.selectWxAccountByAccountOrPhone(sysWxSaleAccount.getWxPhone(), 1) != null){
                return AjaxResult.error("手机号已存在，无法重复添加");
            }
        }
        return toAjax(sysWxSaleAccountService.insertSysWxSaleAccount(sysWxSaleAccount));
    }

    /**
     * 修改微信账号
     */
    @PreAuthorize("@ss.hasPermi('custom:wxAccount:edit')")
    @Log(title = "微信账号", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysWxSaleAccount sysWxSaleAccount)
    {
        if(StringUtils.isNotEmpty(sysWxSaleAccount.getWxAccount())){
            SysWxSaleAccount accountWx = sysWxSaleAccountService.selectWxAccountByAccountOrPhone(sysWxSaleAccount.getWxAccount(), 0);
            if(accountWx != null && accountWx.getId().intValue() != sysWxSaleAccount.getId().intValue()){
                return AjaxResult.error("微信号已存在，无法修改");
            }
        }
        if(StringUtils.isNotEmpty(sysWxSaleAccount.getWxPhone())){
            SysWxSaleAccount accountWx = sysWxSaleAccountService.selectWxAccountByAccountOrPhone(sysWxSaleAccount.getWxPhone(), 1);
            if(accountWx != null && accountWx.getId().intValue() != sysWxSaleAccount.getId().intValue()){
                return AjaxResult.error("手机号已存在，无法修改");
            }
        }
        return toAjax(sysWxSaleAccountService.updateSysWxSaleAccount(sysWxSaleAccount));
    }

    /**
     * 删除微信账号
     */
    @PreAuthorize("@ss.hasPermi('custom:wxAccount:remove')")
    @Log(title = "微信账号", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysWxSaleAccountService.deleteSysWxSaleAccountByIds(ids));
    }

    /**
     * 查询所有微信账号
     */
    @GetMapping("/getAllWxAccount")
    public AjaxResult getAllWxAccount() {
        List<SysWxSaleAccount> list = sysWxSaleAccountService.selectSysWxSaleAccountList(new SysWxSaleAccount());
        return AjaxResult.success(list);
    }
}