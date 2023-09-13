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
import com.ruoyi.user.domain.UserWalletRecord;
import com.ruoyi.user.service.IUserWalletRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户钱包记录Controller
 *
 * @author Lam
 * @date 2023-09-13
 */
@Api(tags = "admin-用户钱包记录")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/userWalletRecord")
public class UserWalletRecordController extends BaseController {

    private final IUserWalletRecordService userWalletRecordService;


    @ApiOperation("查询用户钱包记录列表")
    @PreAuthorize("@ss.hasPermi('user:userWalletRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserWalletRecord userWalletRecord) {
        startPage();
        List<UserWalletRecord> list = userWalletRecordService.selectUserWalletRecordList(userWalletRecord);
        return getDataTable(list);
    }


    @ApiOperation("导出用户钱包记录列表")
    @PreAuthorize("@ss.hasPermi('user:userWalletRecord:export')")
    @Log(title = "用户钱包记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserWalletRecord userWalletRecord) {
        List<UserWalletRecord> list = userWalletRecordService.selectUserWalletRecordList(userWalletRecord);
        ExcelUtil<UserWalletRecord> util = new ExcelUtil<UserWalletRecord>(UserWalletRecord.class);
        util.exportExcel(response, list, "用户钱包记录数据");
    }


    @ApiOperation("获取用户钱包记录详细信息")
    @PreAuthorize("@ss.hasPermi('user:userWalletRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(userWalletRecordService.selectUserWalletRecordById(id));
    }


    @ApiOperation("新增用户钱包记录")
    @PreAuthorize("@ss.hasPermi('user:userWalletRecord:add')")
    @Log(title = "用户钱包记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserWalletRecord userWalletRecord) {
        return toAjax(userWalletRecordService.insertUserWalletRecord(userWalletRecord));
    }


    @ApiOperation("修改用户钱包记录")
    @PreAuthorize("@ss.hasPermi('user:userWalletRecord:edit')")
    @Log(title = "用户钱包记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserWalletRecord userWalletRecord) {
        return toAjax(userWalletRecordService.updateUserWalletRecord(userWalletRecord));
    }


    @ApiOperation("删除用户钱包记录")
    @PreAuthorize("@ss.hasPermi('user:userWalletRecord:remove')")
    @Log(title = "用户钱包记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(userWalletRecordService.deleteUserWalletRecordByIds(ids));
    }
}
