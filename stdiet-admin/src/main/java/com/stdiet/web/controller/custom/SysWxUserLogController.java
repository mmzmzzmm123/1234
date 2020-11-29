package com.stdiet.web.controller.custom;

import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.core.page.TableDataInfo;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.custom.domain.SysOrder;
import com.stdiet.custom.domain.SysWxUserInfo;
import com.stdiet.custom.domain.SysWxUserLog;
import com.stdiet.custom.page.WxLogInfo;
import com.stdiet.custom.service.ISysOrderService;
import com.stdiet.custom.service.ISysWxUserInfoService;
import com.stdiet.custom.service.ISysWxUserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 微信用户记录Controller
 *
 * @author wonder
 * @date 2020-11-28
 */
@RestController
@RequestMapping("/custom/wxUserLog")
public class SysWxUserLogController extends BaseController {
    @Autowired
    private ISysWxUserLogService sysWxUserLogService;

    @Autowired
    private ISysWxUserInfoService sysWxUserInfoService;

    private ISysOrderService sysOrderService;

    /**
     * 查询微信用户记录列表
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysWxUserLog sysWxUserLog) {
        startPage();
        List<SysWxUserLog> list = sysWxUserLogService.selectSysWxUserLogList(sysWxUserLog);
        return getDataTable(list);
    }

    /**
     * 导出微信用户记录列表
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:export')")
    @Log(title = "微信用户记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysWxUserLog sysWxUserLog) {
        List<SysWxUserLog> list = sysWxUserLogService.selectSysWxUserLogList(sysWxUserLog);
        ExcelUtil<SysWxUserLog> util = new ExcelUtil<SysWxUserLog>(SysWxUserLog.class);
        return util.exportExcel(list, "wxUserLog");
    }

    /**
     * 获取微信用户记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:query')")
    @GetMapping(value = "/{openid}")
    public AjaxResult getInfo(@PathVariable("openid") String openid) {
        return AjaxResult.success(sysWxUserLogService.selectSysWxUserLogById(openid));
    }

    /**
     * 新增微信用户记录
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:add')")
    @Log(title = "微信用户记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysWxUserLog sysWxUserLog) {
        return toAjax(sysWxUserLogService.insertSysWxUserLog(sysWxUserLog));
    }

    /**
     * 修改微信用户记录
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:edit')")
    @Log(title = "微信用户记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysWxUserLog sysWxUserLog) {
        return toAjax(sysWxUserLogService.updateSysWxUserLog(sysWxUserLog));
    }

    /**
     * 删除微信用户记录
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:remove')")
    @Log(title = "微信用户记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{openids}")
    public AjaxResult remove(@PathVariable String[] openids) {
        return toAjax(sysWxUserLogService.deleteSysWxUserLogByIds(openids));
    }

    @GetMapping(value = "/wx/logs/list")
    public AjaxResult getLogs(SysWxUserLog sysWxUserLog ) {
        List<WxLogInfo> list = sysWxUserLogService.selectWxLogInfoList(sysWxUserLog);
        return AjaxResult.success(list);
    }

    @PostMapping(value = "/wx/logs/add")
    public AjaxResult addLog(@RequestBody SysWxUserLog sysWxUserLog) {
        // 查询微信用户
        SysWxUserInfo userInfo = sysWxUserInfoService.selectSysWxUserInfoById(sysWxUserLog.getOpenid());
        if(StringUtils.isNull(userInfo)) {
            return AjaxResult.error(5003, "没有用户信息");
        }
        // 提取有用值
        sysWxUserLog.setAvatarUrl(userInfo.getAvatarUrl());
        sysWxUserLog.setPhone(userInfo.getPhone());
        return add(sysWxUserLog);
    }
}