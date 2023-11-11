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
import com.ruoyi.staff.domain.StaffTrends;
import com.ruoyi.staff.service.IStaffTrendsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 员工动态Controller
 *
 * @author Lam
 * @date 2023-09-14
 */
@Api(tags = "admin-员工动态")
@RestController
@RequiredArgsConstructor
@RequestMapping("/staff/staffTrends")
public class StaffTrendsController extends BaseController {

    private final IStaffTrendsService staffTrendsService;


    @ApiOperation("查询员工动态列表")
    @PreAuthorize("@ss.hasPermi('staff:staffTrends:list')")
    @GetMapping("/list")
    public TableDataInfo list(StaffTrends staffTrends) {
        startPage();
        List<StaffTrends> list = staffTrendsService.selectStaffTrendsList(staffTrends);
        return getDataTable(list);
    }


    @ApiOperation("导出员工动态列表")
    @PreAuthorize("@ss.hasPermi('staff:staffTrends:export')")
    @Log(title = "员工动态", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StaffTrends staffTrends) {
        List<StaffTrends> list = staffTrendsService.selectStaffTrendsList(staffTrends);
        ExcelUtil<StaffTrends> util = new ExcelUtil<StaffTrends>(StaffTrends.class);
        util.exportExcel(response, list, "员工动态数据");
    }


    @ApiOperation("获取员工动态详细信息")
    @PreAuthorize("@ss.hasPermi('staff:staffTrends:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(staffTrendsService.selectStaffTrendsById(id));
    }


    @ApiOperation("新增员工动态")
    @PreAuthorize("@ss.hasPermi('staff:staffTrends:add')")
    @Log(title = "员工动态", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StaffTrends staffTrends) {
        return toAjax(staffTrendsService.insertStaffTrends(staffTrends));
    }


    @ApiOperation("修改员工动态")
    @PreAuthorize("@ss.hasPermi('staff:staffTrends:edit')")
    @Log(title = "员工动态", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StaffTrends staffTrends) {
        return toAjax(staffTrendsService.updateStaffTrends(staffTrends));
    }


    @ApiOperation("删除员工动态")
    @PreAuthorize("@ss.hasPermi('staff:staffTrends:remove')")
    @Log(title = "员工动态", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(staffTrendsService.deleteStaffTrendsByIds(ids));
    }
}
