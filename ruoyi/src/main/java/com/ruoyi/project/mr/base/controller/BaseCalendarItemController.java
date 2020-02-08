package com.ruoyi.project.mr.base.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.mr.base.domain.BaseCalendarItem;
import com.ruoyi.project.mr.base.service.IBaseCalendarItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 考勤日历明细Controller
 *
 * @author mr
 * @date 2020-01-07
 */
@RestController
@RequestMapping("/base/calendarItem")
public class BaseCalendarItemController extends BaseController
{
    @Autowired
    private IBaseCalendarItemService baseCalendarItemService;

    /**
     * 查询考勤日历明细列表
     */
    @PreAuthorize("@ss.hasPermi('base:calendarItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaseCalendarItem baseCalendarItem)
    {
        startPage();
        List<BaseCalendarItem> list = baseCalendarItemService.selectBaseCalendarItemList(baseCalendarItem);
        return getDataTable(list);
    }

    /**
     * 导出考勤日历明细列表
     */
    @PreAuthorize("@ss.hasPermi('base:calendarItem:export')")
    @Log(title = "考勤日历明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BaseCalendarItem baseCalendarItem)
    {
        List<BaseCalendarItem> list = baseCalendarItemService.selectBaseCalendarItemList(baseCalendarItem);
        ExcelUtil<BaseCalendarItem> util = new ExcelUtil<BaseCalendarItem>(BaseCalendarItem.class);
        return util.exportExcel(list, "calendarItem");
    }

    /**
     * 获取考勤日历明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:calendarItem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(baseCalendarItemService.selectBaseCalendarItemById(id));
    }

    /**
     * 新增考勤日历明细
     */
    @PreAuthorize("@ss.hasPermi('base:calendarItem:add')")
    @Log(title = "考勤日历明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaseCalendarItem baseCalendarItem)
    {
        return toAjax(baseCalendarItemService.insertBaseCalendarItem(baseCalendarItem));
    }

    /**
     * 修改考勤日历明细
     */
    @PreAuthorize("@ss.hasPermi('base:calendarItem:edit')")
    @Log(title = "考勤日历明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaseCalendarItem baseCalendarItem)
    {
        return toAjax(baseCalendarItemService.updateBaseCalendarItem(baseCalendarItem));
    }

    /**
     * 删除考勤日历明细
     */
    @PreAuthorize("@ss.hasPermi('base:calendarItem:remove')")
    @Log(title = "考勤日历明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(baseCalendarItemService.deleteBaseCalendarItemByIds(ids));
    }
}
