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
import com.ruoyi.staff.domain.StaffSettlementRecords;
import com.ruoyi.staff.service.IStaffSettlementRecordsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 员工结算记录Controller
 *
 * @author Lam
 * @date 2023-09-14
 */
@Api(tags = "admin-员工结算记录")
@RestController
@RequiredArgsConstructor
@RequestMapping("/staff/staffSettlementRecords")
public class StaffSettlementRecordsController extends BaseController {

    private final IStaffSettlementRecordsService staffSettlementRecordsService;


    @ApiOperation("查询员工结算记录列表")
    @PreAuthorize("@ss.hasPermi('staff:staffSettlementRecords:list')")
    @GetMapping("/list")
    public TableDataInfo list(StaffSettlementRecords staffSettlementRecords) {
        startPage();
        List<StaffSettlementRecords> list = staffSettlementRecordsService.selectStaffSettlementRecordsList(staffSettlementRecords);
        return getDataTable(list);
    }


    @ApiOperation("导出员工结算记录列表")
    @PreAuthorize("@ss.hasPermi('staff:staffSettlementRecords:export')")
    @Log(title = "员工结算记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StaffSettlementRecords staffSettlementRecords) {
        List<StaffSettlementRecords> list = staffSettlementRecordsService.selectStaffSettlementRecordsList(staffSettlementRecords);
        ExcelUtil<StaffSettlementRecords> util = new ExcelUtil<StaffSettlementRecords>(StaffSettlementRecords.class);
        util.exportExcel(response, list, "员工结算记录数据");
    }


    @ApiOperation("获取员工结算记录详细信息")
    @PreAuthorize("@ss.hasPermi('staff:staffSettlementRecords:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(staffSettlementRecordsService.selectStaffSettlementRecordsById(id));
    }


    @ApiOperation("新增员工结算记录")
    @PreAuthorize("@ss.hasPermi('staff:staffSettlementRecords:add')")
    @Log(title = "员工结算记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StaffSettlementRecords staffSettlementRecords) {
        return toAjax(staffSettlementRecordsService.insertStaffSettlementRecords(staffSettlementRecords));
    }


    @ApiOperation("修改员工结算记录")
    @PreAuthorize("@ss.hasPermi('staff:staffSettlementRecords:edit')")
    @Log(title = "员工结算记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StaffSettlementRecords staffSettlementRecords) {
        return toAjax(staffSettlementRecordsService.updateStaffSettlementRecords(staffSettlementRecords));
    }


    @ApiOperation("删除员工结算记录")
    @PreAuthorize("@ss.hasPermi('staff:staffSettlementRecords:remove')")
    @Log(title = "员工结算记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(staffSettlementRecordsService.deleteStaffSettlementRecordsByIds(ids));
    }
}
