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
import com.ruoyi.user.domain.UserRechargeRecord;
import com.ruoyi.user.service.IUserRechargeRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户充值记录Controller
 *
 * @author Lam
 * @date 2023-09-13
 */
@Api(tags = "admin-用户充值记录")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/userRechargeRecord")
public class UserRechargeRecordController extends BaseController {

    private final IUserRechargeRecordService userRechargeRecordService;


    @ApiOperation("查询用户充值记录列表")
    @PreAuthorize("@ss.hasPermi('user:userRechargeRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserRechargeRecord userRechargeRecord) {
        startPage();
        List<UserRechargeRecord> list = userRechargeRecordService.selectUserRechargeRecordList(userRechargeRecord);
        return getDataTable(list);
    }


    @ApiOperation("导出用户充值记录列表")
    @PreAuthorize("@ss.hasPermi('user:userRechargeRecord:export')")
    @Log(title = "用户充值记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserRechargeRecord userRechargeRecord) {
        List<UserRechargeRecord> list = userRechargeRecordService.selectUserRechargeRecordList(userRechargeRecord);
        ExcelUtil<UserRechargeRecord> util = new ExcelUtil<UserRechargeRecord>(UserRechargeRecord.class);
        util.exportExcel(response, list, "用户充值记录数据");
    }


    @ApiOperation("获取用户充值记录详细信息")
    @PreAuthorize("@ss.hasPermi('user:userRechargeRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(userRechargeRecordService.selectUserRechargeRecordById(id));
    }


    @ApiOperation("新增用户充值记录")
    @PreAuthorize("@ss.hasPermi('user:userRechargeRecord:add')")
    @Log(title = "用户充值记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserRechargeRecord userRechargeRecord) {
        return toAjax(userRechargeRecordService.insertUserRechargeRecord(userRechargeRecord));
    }


    @ApiOperation("修改用户充值记录")
    @PreAuthorize("@ss.hasPermi('user:userRechargeRecord:edit')")
    @Log(title = "用户充值记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserRechargeRecord userRechargeRecord) {
        return toAjax(userRechargeRecordService.updateUserRechargeRecord(userRechargeRecord));
    }


    @ApiOperation("删除用户充值记录")
    @PreAuthorize("@ss.hasPermi('user:userRechargeRecord:remove')")
    @Log(title = "用户充值记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(userRechargeRecordService.deleteUserRechargeRecordByIds(ids));
    }
}
