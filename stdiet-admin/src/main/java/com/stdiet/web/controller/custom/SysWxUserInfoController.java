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
import com.stdiet.custom.domain.SysWxUserInfo;
import com.stdiet.custom.service.ISysWxUserInfoService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 微信用户Controller
 *
 * @author wonder
 * @date 2020-11-28
 */
@RestController
@RequestMapping("/custom/wxUserInfo")
public class SysWxUserInfoController extends BaseController {
    @Autowired
    private ISysWxUserInfoService sysWxUserInfoService;

    /**
     * 查询微信用户列表
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysWxUserInfo sysWxUserInfo) {
        startPage();
        List<SysWxUserInfo> list = sysWxUserInfoService.selectSysWxUserInfoList(sysWxUserInfo);
        return getDataTable(list);
    }

    /**
     * 导出微信用户列表
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserInfo:export')")
    @Log(title = "微信用户", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysWxUserInfo sysWxUserInfo) {
        List<SysWxUserInfo> list = sysWxUserInfoService.selectSysWxUserInfoList(sysWxUserInfo);
        ExcelUtil<SysWxUserInfo> util = new ExcelUtil<SysWxUserInfo>(SysWxUserInfo.class);
        return util.exportExcel(list, "wxUserInfo");
    }

    /**
     * 获取微信用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserInfo:query')")
    @GetMapping(value = "/{openid}")
    public AjaxResult getInfo(@PathVariable("openid") String openid) {
        return AjaxResult.success(sysWxUserInfoService.selectSysWxUserInfoById(openid));
    }

    /**
     * 新增微信用户
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserInfo:add')")
    @Log(title = "微信用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysWxUserInfo sysWxUserInfo) {
        return toAjax(sysWxUserInfoService.insertSysWxUserInfo(sysWxUserInfo));
    }

    /**
     * 修改微信用户
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserInfo:edit')")
    @Log(title = "微信用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysWxUserInfo sysWxUserInfo) {
        return toAjax(sysWxUserInfoService.updateSysWxUserInfo(sysWxUserInfo));
    }

    /**
     * 删除微信用户
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserInfo:remove')")
    @Log(title = "微信用户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{openids}")
    public AjaxResult remove(@PathVariable String[] openids) {
        return toAjax(sysWxUserInfoService.deleteSysWxUserInfoByIds(openids));
    }

    @PostMapping("/wx/adduser")
    public AjaxResult addUser(@RequestBody SysWxUserInfo sysWxUserInfo) {

        SysWxUserInfo userInfo = sysWxUserInfoService.selectSysWxUserInfoById(sysWxUserInfo.getOpenid());
        int resultCode = 0;
        if (userInfo == null) {
            resultCode = sysWxUserInfoService.insertSysWxUserInfo(sysWxUserInfo);
        } else {
            sysWxUserInfo.setPhone(userInfo.getPhone());
            resultCode = sysWxUserInfoService.updateSysWxUserInfo(sysWxUserInfo);
        }
        return toAjax(resultCode);
    }
}