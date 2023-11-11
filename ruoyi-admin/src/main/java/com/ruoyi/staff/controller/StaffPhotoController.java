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
import com.ruoyi.staff.domain.StaffPhoto;
import com.ruoyi.staff.service.IStaffPhotoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 员工相册Controller
 *
 * @author Lam
 * @date 2023-09-14
 */
@Api(tags = "admin-员工相册")
@RestController
@RequiredArgsConstructor
@RequestMapping("/staff/staffPhoto")
public class StaffPhotoController extends BaseController {

    private final IStaffPhotoService staffPhotoService;


    @ApiOperation("查询员工相册列表")
    @PreAuthorize("@ss.hasPermi('staff:staffPhoto:list')")
    @GetMapping("/list")
    public TableDataInfo list(StaffPhoto staffPhoto) {
        startPage();
        List<StaffPhoto> list = staffPhotoService.selectStaffPhotoList(staffPhoto);
        return getDataTable(list);
    }


    @ApiOperation("导出员工相册列表")
    @PreAuthorize("@ss.hasPermi('staff:staffPhoto:export')")
    @Log(title = "员工相册", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StaffPhoto staffPhoto) {
        List<StaffPhoto> list = staffPhotoService.selectStaffPhotoList(staffPhoto);
        ExcelUtil<StaffPhoto> util = new ExcelUtil<StaffPhoto>(StaffPhoto.class);
        util.exportExcel(response, list, "员工相册数据");
    }


    @ApiOperation("获取员工相册详细信息")
    @PreAuthorize("@ss.hasPermi('staff:staffPhoto:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(staffPhotoService.selectStaffPhotoById(id));
    }


    @ApiOperation("新增员工相册")
    @PreAuthorize("@ss.hasPermi('staff:staffPhoto:add')")
    @Log(title = "员工相册", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StaffPhoto staffPhoto) {
        return toAjax(staffPhotoService.insertStaffPhoto(staffPhoto));
    }


    @ApiOperation("修改员工相册")
    @PreAuthorize("@ss.hasPermi('staff:staffPhoto:edit')")
    @Log(title = "员工相册", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StaffPhoto staffPhoto) {
        return toAjax(staffPhotoService.updateStaffPhoto(staffPhoto));
    }


    @ApiOperation("删除员工相册")
    @PreAuthorize("@ss.hasPermi('staff:staffPhoto:remove')")
    @Log(title = "员工相册", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(staffPhotoService.deleteStaffPhotoByIds(ids));
    }
}
