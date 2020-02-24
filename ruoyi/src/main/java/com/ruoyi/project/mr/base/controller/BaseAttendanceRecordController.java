package com.ruoyi.project.mr.base.controller;

import com.ruoyi.common.utils.IdUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.mr.base.domain.BaseAttendanceRecord;
import com.ruoyi.project.mr.base.service.IBaseAttendanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 考勤记录Controller
 *
 * @author mr
 * @date 2020-02-07
 */
@RestController
@RequestMapping("/base/record")
public class BaseAttendanceRecordController extends BaseController
{
    @Autowired
    private IBaseAttendanceRecordService baseAttendanceRecordService;

    /**
     * 查询考勤记录列表
     */
    @PreAuthorize("@ss.hasPermi('base:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaseAttendanceRecord baseAttendanceRecord)
    {
        startPage();
        List<BaseAttendanceRecord> list = baseAttendanceRecordService.selectBaseAttendanceRecordList(baseAttendanceRecord);
        return getDataTable(list);
    }

    /**
     * 导出考勤记录列表
     */
    @PreAuthorize("@ss.hasPermi('base:record:export')")
    @Log(title = "考勤记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BaseAttendanceRecord baseAttendanceRecord)
    {
        List<BaseAttendanceRecord> list = baseAttendanceRecordService.selectBaseAttendanceRecordList(baseAttendanceRecord);
        ExcelUtil<BaseAttendanceRecord> util = new ExcelUtil<BaseAttendanceRecord>(BaseAttendanceRecord.class);
        return util.exportExcel(list, "record");
    }

    /**
     * 获取考勤记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(baseAttendanceRecordService.selectBaseAttendanceRecordById(id));
    }

    /**
     * 新增考勤记录
     */
    @PreAuthorize("@ss.hasPermi('base:record:add')")
    @Log(title = "考勤记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaseAttendanceRecord baseAttendanceRecord)
    {
        baseAttendanceRecord.setId(IdUtils.fastUUID());
        return toAjax(baseAttendanceRecordService.insertBaseAttendanceRecord(baseAttendanceRecord));
    }

    /**
     * 修改考勤记录
     */
    @PreAuthorize("@ss.hasPermi('base:record:edit')")
    @Log(title = "考勤记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaseAttendanceRecord baseAttendanceRecord)
    {
        return toAjax(baseAttendanceRecordService.updateBaseAttendanceRecord(baseAttendanceRecord));
    }

    /**
     * 删除考勤记录
     */
    @PreAuthorize("@ss.hasPermi('base:record:remove')")
    @Log(title = "考勤记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(baseAttendanceRecordService.deleteBaseAttendanceRecordByIds(ids));
    }
}
