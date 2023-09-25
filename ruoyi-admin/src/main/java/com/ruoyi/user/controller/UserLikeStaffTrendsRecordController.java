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
import com.ruoyi.user.domain.UserLikeStaffTrendsRecord;
import com.ruoyi.user.service.IUserLikeStaffTrendsRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 点赞动态记录Controller
 *
 * @author Lam
 * @date 2023-09-21
 */
@Api(tags = "admin-点赞动态记录")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/userLikeStaffTrendsRecord")
public class UserLikeStaffTrendsRecordController extends BaseController {

    private final IUserLikeStaffTrendsRecordService userLikeStaffTrendsRecordService;


    @ApiOperation("查询点赞动态记录列表")
    @PreAuthorize("@ss.hasPermi('user:userLikeStaffTrendsRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserLikeStaffTrendsRecord userLikeStaffTrendsRecord) {
        startPage();
        List<UserLikeStaffTrendsRecord> list = userLikeStaffTrendsRecordService.selectUserLikeStaffTrendsRecordList(userLikeStaffTrendsRecord);
        return getDataTable(list);
    }


    @ApiOperation("导出点赞动态记录列表")
    @PreAuthorize("@ss.hasPermi('user:userLikeStaffTrendsRecord:export')")
    @Log(title = "点赞动态记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserLikeStaffTrendsRecord userLikeStaffTrendsRecord) {
        List<UserLikeStaffTrendsRecord> list = userLikeStaffTrendsRecordService.selectUserLikeStaffTrendsRecordList(userLikeStaffTrendsRecord);
        ExcelUtil<UserLikeStaffTrendsRecord> util = new ExcelUtil<UserLikeStaffTrendsRecord>(UserLikeStaffTrendsRecord.class);
        util.exportExcel(response, list, "点赞动态记录数据");
    }


    @ApiOperation("获取点赞动态记录详细信息")
    @PreAuthorize("@ss.hasPermi('user:userLikeStaffTrendsRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(userLikeStaffTrendsRecordService.selectUserLikeStaffTrendsRecordById(id));
    }


    @ApiOperation("新增点赞动态记录")
    @PreAuthorize("@ss.hasPermi('user:userLikeStaffTrendsRecord:add')")
    @Log(title = "点赞动态记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserLikeStaffTrendsRecord userLikeStaffTrendsRecord) {
        return toAjax(userLikeStaffTrendsRecordService.insertUserLikeStaffTrendsRecord(userLikeStaffTrendsRecord));
    }


    @ApiOperation("修改点赞动态记录")
    @PreAuthorize("@ss.hasPermi('user:userLikeStaffTrendsRecord:edit')")
    @Log(title = "点赞动态记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserLikeStaffTrendsRecord userLikeStaffTrendsRecord) {
        return toAjax(userLikeStaffTrendsRecordService.updateUserLikeStaffTrendsRecord(userLikeStaffTrendsRecord));
    }


    @ApiOperation("删除点赞动态记录")
    @PreAuthorize("@ss.hasPermi('user:userLikeStaffTrendsRecord:remove')")
    @Log(title = "点赞动态记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(userLikeStaffTrendsRecordService.deleteUserLikeStaffTrendsRecordByIds(ids));
    }
}
