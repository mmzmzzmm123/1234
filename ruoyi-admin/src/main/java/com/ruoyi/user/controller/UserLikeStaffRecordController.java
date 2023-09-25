package com.ruoyi.user.controller;

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
import com.ruoyi.user.domain.UserLikeStaffRecord;
import com.ruoyi.user.service.IUserLikeStaffRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 收藏员工记录Controller
 *
 * @author Lam
 * @date 2023-09-21
 */
@Api(tags = "admin-收藏员工记录")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/userLikeStaffRecord")
public class UserLikeStaffRecordController extends BaseController {

    private final IUserLikeStaffRecordService userLikeStaffRecordService;


    @ApiOperation("查询收藏员工记录列表")
    @PreAuthorize("@ss.hasPermi('user:userLikeStaffRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserLikeStaffRecord userLikeStaffRecord) {
        startPage();
        List<UserLikeStaffRecord> list = userLikeStaffRecordService.selectUserLikeStaffRecordList(userLikeStaffRecord);
        return getDataTable(list);
    }


    @ApiOperation("导出收藏员工记录列表")
    @PreAuthorize("@ss.hasPermi('user:userLikeStaffRecord:export')")
    @Log(title = "收藏员工记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserLikeStaffRecord userLikeStaffRecord) {
        List<UserLikeStaffRecord> list = userLikeStaffRecordService.selectUserLikeStaffRecordList(userLikeStaffRecord);
        ExcelUtil<UserLikeStaffRecord> util = new ExcelUtil<UserLikeStaffRecord>(UserLikeStaffRecord.class);
        util.exportExcel(response, list, "收藏员工记录数据");
    }


    @ApiOperation("获取收藏员工记录详细信息")
    @PreAuthorize("@ss.hasPermi('user:userLikeStaffRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(userLikeStaffRecordService.selectUserLikeStaffRecordById(id));
    }


    @ApiOperation("新增收藏员工记录")
    @PreAuthorize("@ss.hasPermi('user:userLikeStaffRecord:add')")
    @Log(title = "收藏员工记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserLikeStaffRecord userLikeStaffRecord) {
        return toAjax(userLikeStaffRecordService.insertUserLikeStaffRecord(userLikeStaffRecord));
    }


    @ApiOperation("修改收藏员工记录")
    @PreAuthorize("@ss.hasPermi('user:userLikeStaffRecord:edit')")
    @Log(title = "收藏员工记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserLikeStaffRecord userLikeStaffRecord) {
        return toAjax(userLikeStaffRecordService.updateUserLikeStaffRecord(userLikeStaffRecord));
    }


    @ApiOperation("删除收藏员工记录")
    @PreAuthorize("@ss.hasPermi('user:userLikeStaffRecord:remove')")
    @Log(title = "收藏员工记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(userLikeStaffRecordService.deleteUserLikeStaffRecordByIds(ids));
    }
}
