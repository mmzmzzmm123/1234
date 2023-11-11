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
import com.ruoyi.staff.domain.StaffInfo;
import com.ruoyi.staff.service.IStaffInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 员工信息Controller
 *
 * @author Lam
 * @date 2023-09-14
 */
@Api(tags = "admin-员工信息")
@RestController
@RequiredArgsConstructor
@RequestMapping("/staff/staffInfo")
public class StaffInfoController extends BaseController {

    private final IStaffInfoService staffInfoService;


    @ApiOperation("查询员工信息列表")
    @PreAuthorize("@ss.hasPermi('staff:staffInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(StaffInfo staffInfo) {
        startPage();
        List<StaffInfo> list = staffInfoService.selectStaffInfoList(staffInfo);
        return getDataTable(list);
    }


    @ApiOperation("导出员工信息列表")
    @PreAuthorize("@ss.hasPermi('staff:staffInfo:export')")
    @Log(title = "员工信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StaffInfo staffInfo) {
        List<StaffInfo> list = staffInfoService.selectStaffInfoList(staffInfo);
        ExcelUtil<StaffInfo> util = new ExcelUtil<StaffInfo>(StaffInfo.class);
        util.exportExcel(response, list, "员工信息数据");
    }


    @ApiOperation("获取员工信息详细信息")
    @PreAuthorize("@ss.hasPermi('staff:staffInfo:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId) {
        return success(staffInfoService.selectStaffInfoByUserId(userId));
    }


    @ApiOperation("新增员工信息")
    @PreAuthorize("@ss.hasPermi('staff:staffInfo:add')")
    @Log(title = "员工信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StaffInfo staffInfo) {
        return toAjax(staffInfoService.insertStaffInfo(staffInfo));
    }


    @ApiOperation("修改员工信息")
    @PreAuthorize("@ss.hasPermi('staff:staffInfo:edit')")
    @Log(title = "员工信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StaffInfo staffInfo) {
        return toAjax(staffInfoService.updateStaffInfo(staffInfo));
    }


    @ApiOperation("删除员工信息")
    @PreAuthorize("@ss.hasPermi('staff:staffInfo:remove')")
    @Log(title = "员工信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        return toAjax(staffInfoService.deleteStaffInfoByUserIds(userIds));
    }
}
