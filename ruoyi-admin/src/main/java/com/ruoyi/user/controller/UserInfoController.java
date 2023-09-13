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
import com.ruoyi.user.domain.UserInfo;
import com.ruoyi.user.service.IUserInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户信息管理Controller
 *
 * @author Lam
 * @date 2023-09-13
 */
@Api(tags = "admin-用户信息管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/userInfo")
public class UserInfoController extends BaseController {

    private final IUserInfoService userInfoService;


    @ApiOperation("查询用户信息管理列表")
    @PreAuthorize("@ss.hasPermi('user:userInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserInfo userInfo) {
        startPage();
        List<UserInfo> list = userInfoService.selectUserInfoList(userInfo);
        return getDataTable(list);
    }


    @ApiOperation("导出用户信息管理列表")
    @PreAuthorize("@ss.hasPermi('user:userInfo:export')")
    @Log(title = "用户信息管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserInfo userInfo) {
        List<UserInfo> list = userInfoService.selectUserInfoList(userInfo);
        ExcelUtil<UserInfo> util = new ExcelUtil<UserInfo>(UserInfo.class);
        util.exportExcel(response, list, "用户信息管理数据");
    }


    @ApiOperation("获取用户信息管理详细信息")
    @PreAuthorize("@ss.hasPermi('user:userInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(userInfoService.selectUserInfoById(id));
    }


    @ApiOperation("新增用户信息管理")
    @PreAuthorize("@ss.hasPermi('user:userInfo:add')")
    @Log(title = "用户信息管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserInfo userInfo) {
        return toAjax(userInfoService.insertUserInfo(userInfo));
    }


    @ApiOperation("修改用户信息管理")
    @PreAuthorize("@ss.hasPermi('user:userInfo:edit')")
    @Log(title = "用户信息管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserInfo userInfo) {
        return toAjax(userInfoService.updateUserInfo(userInfo));
    }


    @ApiOperation("删除用户信息管理")
    @PreAuthorize("@ss.hasPermi('user:userInfo:remove')")
    @Log(title = "用户信息管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(userInfoService.deleteUserInfoByIds(ids));
    }
}
