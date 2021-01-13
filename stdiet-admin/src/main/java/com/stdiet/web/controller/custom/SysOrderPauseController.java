package com.stdiet.web.controller.custom;

import java.text.SimpleDateFormat;
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
import com.stdiet.custom.domain.SysOrderPause;
import com.stdiet.custom.service.ISysOrderPauseService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 订单服务暂停Controller
 *
 * @author wonder
 * @date 2021-01-07
 */
@RestController
@RequestMapping("/orderPause/pause")
public class SysOrderPauseController extends BaseController
{
    @Autowired
    private ISysOrderPauseService sysOrderPauseService;

    /**
     * 查询订单服务暂停列表
     */
    @PreAuthorize("@ss.hasPermi('orderPause:pause:query')")
    @GetMapping("/list")
    public TableDataInfo list(SysOrderPause sysOrderPause)
    {
        startPage();
        List<SysOrderPause> list = sysOrderPauseService.selectSysOrderPauseList(sysOrderPause);
        return getDataTable(list);
    }

    /**
     * 导出订单服务暂停列表
     */
    @PreAuthorize("@ss.hasPermi('orderPause:pause:export')")
    @Log(title = "订单服务暂停", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysOrderPause sysOrderPause)
    {
        List<SysOrderPause> list = sysOrderPauseService.selectSysOrderPauseList(sysOrderPause);
        ExcelUtil<SysOrderPause> util = new ExcelUtil<SysOrderPause>(SysOrderPause.class);
        return util.exportExcel(list, "pause");
    }

    /**
     * 获取订单服务暂停详细信息
     */
    @PreAuthorize("@ss.hasPermi('orderPause:pause:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysOrderPauseService.selectSysOrderPauseById(id));
    }

    /**
     * 新增订单服务暂停
     */
    @PreAuthorize("@ss.hasPermi('orderPause:pause:add')")
    @Log(title = "订单服务暂停", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysOrderPause sysOrderPause)
    {
        return toAjax(sysOrderPauseService.insertSysOrderPause(sysOrderPause));
    }

    /**
     * 修改订单服务暂停
     */
    @PreAuthorize("@ss.hasPermi('orderPause:pause:edit')")
    @Log(title = "订单服务暂停", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysOrderPause sysOrderPause)
    {
        return toAjax(sysOrderPauseService.updateSysOrderPause(sysOrderPause));
    }

    /**
     * 删除订单服务暂停
     */
    @PreAuthorize("@ss.hasPermi('orderPause:pause:remove')")
    @Log(title = "订单服务暂停", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysOrderPauseService.deleteSysOrderPauseByIds(ids));
    }
}