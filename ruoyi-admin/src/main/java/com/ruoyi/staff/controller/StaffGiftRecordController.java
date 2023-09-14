package com.ruoyi.staff.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.staff.domain.StaffGiftRecord;
import com.ruoyi.staff.service.IStaffGiftRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 员工礼物记录Controller
 *
 * @author Lam
 * @date 2023-09-14
 */
@Api(tags = "admin-员工礼物记录")
@RestController
@RequiredArgsConstructor
@RequestMapping("/staff/staffGiftRecord")
public class StaffGiftRecordController extends BaseController {

    private final IStaffGiftRecordService staffGiftRecordService;


    @ApiOperation("查询员工礼物记录列表")
    @PreAuthorize("@ss.hasPermi('staff:staffGiftRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(StaffGiftRecord staffGiftRecord) {
        startPage();
        List<StaffGiftRecord> list = staffGiftRecordService.selectStaffGiftRecordList(staffGiftRecord);
        return getDataTable(list);
    }


    @ApiOperation("导出员工礼物记录列表")
    @PreAuthorize("@ss.hasPermi('staff:staffGiftRecord:export')")
    @Log(title = "员工礼物记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StaffGiftRecord staffGiftRecord) {
        List<StaffGiftRecord> list = staffGiftRecordService.selectStaffGiftRecordList(staffGiftRecord);
        ExcelUtil<StaffGiftRecord> util = new ExcelUtil<StaffGiftRecord>(StaffGiftRecord.class);
        util.exportExcel(response, list, "员工礼物记录数据");
    }


    @ApiOperation("获取员工礼物记录详细信息")
    @PreAuthorize("@ss.hasPermi('staff:staffGiftRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(staffGiftRecordService.selectStaffGiftRecordById(id));
    }


    @ApiOperation("新增员工礼物记录")
    @PreAuthorize("@ss.hasPermi('staff:staffGiftRecord:add')")
    @Log(title = "员工礼物记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StaffGiftRecord staffGiftRecord) {
        return toAjax(staffGiftRecordService.insertStaffGiftRecord(staffGiftRecord));
    }


    @ApiOperation("修改员工礼物记录")
    @PreAuthorize("@ss.hasPermi('staff:staffGiftRecord:edit')")
    @Log(title = "员工礼物记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StaffGiftRecord staffGiftRecord) {
        return toAjax(staffGiftRecordService.updateStaffGiftRecord(staffGiftRecord));
    }


    @ApiOperation("删除员工礼物记录")
    @PreAuthorize("@ss.hasPermi('staff:staffGiftRecord:remove')")
    @Log(title = "员工礼物记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(staffGiftRecordService.deleteStaffGiftRecordByIds(ids));
    }
}
