package com.ruoyi.project.benyi.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.ruoyi.project.benyi.domain.ByCalendarShow;
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
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.benyi.domain.ByCalendar;
import com.ruoyi.project.benyi.service.IByCalendarService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 园历管理(本一)Controller
 * 
 * @author tsbz
 * @date 2020-05-10
 */
@RestController
@RequestMapping("/benyi/calendar")
public class ByCalendarController extends BaseController
{
    @Autowired
    private IByCalendarService byCalendarService;

    /**
     * 查询园历管理(本一)列表
     */
    @PreAuthorize("@ss.hasPermi('system:calendar:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByCalendar byCalendar)
    {
        startPage();
        List<ByCalendar> list = byCalendarService.selectByCalendarList(byCalendar);
        return getDataTable(list);
    }

    /**
     * 导出园历管理(本一)列表
     */
    @PreAuthorize("@ss.hasPermi('system:calendar:export')")
    @Log(title = "园历管理(本一)", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByCalendar byCalendar)
    {
        List<ByCalendar> list = byCalendarService.selectByCalendarList(byCalendar);
        ExcelUtil<ByCalendar> util = new ExcelUtil<ByCalendar>(ByCalendar.class);
        return util.exportExcel(list, "calendar");
    }

    /**
     * 获取园历管理(本一)详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:calendar:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(byCalendarService.selectByCalendarById(id));
    }

    /**
     * 新增园历管理(本一)
     */
    @PreAuthorize("@ss.hasPermi('system:calendar:add')")
    @Log(title = "园历管理(本一)", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByCalendar byCalendar)
    {
        return toAjax(byCalendarService.insertByCalendar(byCalendar));
    }

    /**
     * 修改园历管理(本一)
     */
    @PreAuthorize("@ss.hasPermi('system:calendar:edit')")
    @Log(title = "园历管理(本一)", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByCalendar byCalendar)
    {
        return toAjax(byCalendarService.updateByCalendar(byCalendar));
    }

    /**
     * 删除园历管理(本一)
     */
    @PreAuthorize("@ss.hasPermi('system:calendar:remove')")
    @Log(title = "园历管理(本一)", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(byCalendarService.deleteByCalendarByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('benyi:schoolcalendar:list')")
    @GetMapping("/getAllSchoolCalendars")
    public AjaxResult getAllSchoolCalendars(ByCalendar byCalendar) {


        List<ByCalendarShow> listvi= new ArrayList<>();
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //加载本一园历
        List<ByCalendar> list = byCalendarService.selectByCalendarList(byCalendar);
        for (ByCalendar calendar:list) {
            ByCalendarShow by = new ByCalendarShow();
            by.setId(calendar.getId());
            by.setTitle(calendar.getName());
            by.setStart(formatter.format(calendar.getActivitytime()));
            by.setEnd(formatter.format(calendar.getActivityendtime()));
            by.setColor(calendar.getStylecolor());
            listvi.add(by);
        }
        //加载幼儿园园历

        //接在班级园历



        AjaxResult ajax = AjaxResult.success();
        ajax.put("calendarData", listvi);
        return ajax;

    }
}
