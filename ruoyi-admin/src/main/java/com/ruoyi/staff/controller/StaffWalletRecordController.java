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
import com.ruoyi.staff.domain.StaffWalletRecord;
import com.ruoyi.staff.service.IStaffWalletRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 钱包佣金记录Controller
 *
 * @author Lam
 * @date 2023-10-10
 */
@Api(tags = "admin-钱包佣金记录")
@RestController
@RequiredArgsConstructor
@RequestMapping("/staff/staffWalletRecord")
public class StaffWalletRecordController extends BaseController {

    private final IStaffWalletRecordService staffWalletRecordService;


    @ApiOperation("查询钱包佣金记录列表")
    @PreAuthorize("@ss.hasPermi('staff:staffWalletRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(StaffWalletRecord staffWalletRecord) {
        startPage();
        List<StaffWalletRecord> list = staffWalletRecordService.selectStaffWalletRecordList(staffWalletRecord);
        return getDataTable(list);
    }


    @ApiOperation("导出钱包佣金记录列表")
    @PreAuthorize("@ss.hasPermi('staff:staffWalletRecord:export')")
    @Log(title = "钱包佣金记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StaffWalletRecord staffWalletRecord) {
        List<StaffWalletRecord> list = staffWalletRecordService.selectStaffWalletRecordList(staffWalletRecord);
        ExcelUtil<StaffWalletRecord> util = new ExcelUtil<StaffWalletRecord>(StaffWalletRecord.class);
        util.exportExcel(response, list, "钱包佣金记录数据");
    }


    @ApiOperation("获取钱包佣金记录详细信息")
    @PreAuthorize("@ss.hasPermi('staff:staffWalletRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(staffWalletRecordService.selectStaffWalletRecordById(id));
    }


    @ApiOperation("新增钱包佣金记录")
    @PreAuthorize("@ss.hasPermi('staff:staffWalletRecord:add')")
    @Log(title = "钱包佣金记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StaffWalletRecord staffWalletRecord) {
        return toAjax(staffWalletRecordService.insertStaffWalletRecord(staffWalletRecord));
    }


    @ApiOperation("修改钱包佣金记录")
    @PreAuthorize("@ss.hasPermi('staff:staffWalletRecord:edit')")
    @Log(title = "钱包佣金记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StaffWalletRecord staffWalletRecord) {
        return toAjax(staffWalletRecordService.updateStaffWalletRecord(staffWalletRecord));
    }


    @ApiOperation("删除钱包佣金记录")
    @PreAuthorize("@ss.hasPermi('staff:staffWalletRecord:remove')")
    @Log(title = "钱包佣金记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(staffWalletRecordService.deleteStaffWalletRecordByIds(ids));
    }
}
