package com.ruoyi.project.mr.base.controller;

import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.mr.base.domain.BaseCalendar;
import com.ruoyi.project.mr.base.service.IBaseCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 考勤日历Controller
 *
 * @author mr
 * @date 2020-01-07
 */
@RestController
@RequestMapping("/base/calendar")
public class BaseCalendarController extends BaseController
{
    @Autowired
    private IBaseCalendarService baseCalendarService;

    /**
     * 查询考勤日历列表
     */
    @PreAuthorize("@ss.hasPermi('base:calendar:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaseCalendar baseCalendar)
    {
        startPage();
        List<BaseCalendar> list = baseCalendarService.selectBaseCalendarList(baseCalendar);
        return getDataTable(list);
    }

    /**
     * 导出考勤日历列表
     */
    @PreAuthorize("@ss.hasPermi('base:calendar:export')")
    @Log(title = "考勤日历", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BaseCalendar baseCalendar)
    {
        List<BaseCalendar> list = baseCalendarService.selectBaseCalendarList(baseCalendar);
        ExcelUtil<BaseCalendar> util = new ExcelUtil<BaseCalendar>(BaseCalendar.class);
        return util.exportExcel(list, "calendar");
    }

    /**
     * 获取考勤日历详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:calendar:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(baseCalendarService.selectBaseCalendarById(id));
    }

    /**
     * 新增考勤日历
     */
    @PreAuthorize("@ss.hasPermi('base:calendar:add')")
    @Log(title = "考勤日历", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaseCalendar baseCalendar)
    {
        baseCalendar.setId(IdUtils.fastUUID());
        return toAjax(baseCalendarService.insertBaseCalendar(baseCalendar));
    }

    /**
     * 修改考勤日历
     */
    @PreAuthorize("@ss.hasPermi('base:calendar:edit')")
    @Log(title = "考勤日历", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaseCalendar baseCalendar)
    {
        return toAjax(baseCalendarService.updateBaseCalendar(baseCalendar));
    }

    /**
     * 删除考勤日历
     */
    @PreAuthorize("@ss.hasPermi('base:calendar:remove')")
    @Log(title = "考勤日历", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(baseCalendarService.deleteBaseCalendarByIds(ids));
    }
}
