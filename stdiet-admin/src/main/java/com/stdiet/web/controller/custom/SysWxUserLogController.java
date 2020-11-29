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
import com.stdiet.custom.domain.SysWxUserLog;
import com.stdiet.custom.service.ISysWxUserLogService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 微信用户记录Controller
 *
 * @author wonder
 * @date 2020-11-28
 */
@RestController
@RequestMapping("/custom/wxUserLog")
public class SysWxUserLogController extends BaseController
{
    @Autowired
    private ISysWxUserLogService sysWxUserLogService;

    /**
     * 查询微信用户记录列表
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysWxUserLog sysWxUserLog)
    {
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
    public AjaxResult export(SysWxUserLog sysWxUserLog)
    {
        List<SysWxUserLog> list = sysWxUserLogService.selectSysWxUserLogList(sysWxUserLog);
        ExcelUtil<SysWxUserLog> util = new ExcelUtil<SysWxUserLog>(SysWxUserLog.class);
        return util.exportExcel(list, "wxUserLog");
    }

    /**
     * 获取微信用户记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:query')")
    @GetMapping(value = "/{openid}")
    public AjaxResult getInfo(@PathVariable("openid") String openid)
    {
        return AjaxResult.success(sysWxUserLogService.selectSysWxUserLogById(openid));
    }

    /**
     * 新增微信用户记录
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:add')")
    @Log(title = "微信用户记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysWxUserLog sysWxUserLog)
    {
        return toAjax(sysWxUserLogService.insertSysWxUserLog(sysWxUserLog));
    }

    /**
     * 修改微信用户记录
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:edit')")
    @Log(title = "微信用户记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysWxUserLog sysWxUserLog)
    {
        return toAjax(sysWxUserLogService.updateSysWxUserLog(sysWxUserLog));
    }

    /**
     * 删除微信用户记录
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:remove')")
    @Log(title = "微信用户记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{openids}")
    public AjaxResult remove(@PathVariable String[] openids)
    {
        return toAjax(sysWxUserLogService.deleteSysWxUserLogByIds(openids));
    }

    @GetMapping(value = "/wx/logs/{openid}")
    public TableDataInfo getLogInfo(@PathVariable("openid") String openid)
    {
        SysWxUserLog sysWxUserLog = new SysWxUserLog();
        sysWxUserLog.setOpenid(openid);
        List<SysWxUserLog> list = sysWxUserLogService.selectSysWxUserLogList(sysWxUserLog);
        return getDataTable(list);
    }
}