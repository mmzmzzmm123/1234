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
import com.ruoyi.user.domain.UserSocialAccounts;
import com.ruoyi.user.service.IUserSocialAccountsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户社交账号管理Controller
 *
 * @author Lam
 * @date 2023-09-13
 */
@Api(tags = "admin-用户社交账号管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/userSocialAccounts")
public class UserSocialAccountsController extends BaseController {

    private final IUserSocialAccountsService userSocialAccountsService;


    @ApiOperation("查询用户社交账号管理列表")
    @PreAuthorize("@ss.hasPermi('user:userSocialAccounts:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserSocialAccounts userSocialAccounts) {
        startPage();
        List<UserSocialAccounts> list = userSocialAccountsService.selectUserSocialAccountsList(userSocialAccounts);
        return getDataTable(list);
    }


    @ApiOperation("导出用户社交账号管理列表")
    @PreAuthorize("@ss.hasPermi('user:userSocialAccounts:export')")
    @Log(title = "用户社交账号管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserSocialAccounts userSocialAccounts) {
        List<UserSocialAccounts> list = userSocialAccountsService.selectUserSocialAccountsList(userSocialAccounts);
        ExcelUtil<UserSocialAccounts> util = new ExcelUtil<UserSocialAccounts>(UserSocialAccounts.class);
        util.exportExcel(response, list, "用户社交账号管理数据");
    }


    @ApiOperation("获取用户社交账号管理详细信息")
    @PreAuthorize("@ss.hasPermi('user:userSocialAccounts:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(userSocialAccountsService.selectUserSocialAccountsById(id));
    }


    @ApiOperation("新增用户社交账号管理")
    @PreAuthorize("@ss.hasPermi('user:userSocialAccounts:add')")
    @Log(title = "用户社交账号管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserSocialAccounts userSocialAccounts) {
        return toAjax(userSocialAccountsService.insertUserSocialAccounts(userSocialAccounts));
    }


    @ApiOperation("修改用户社交账号管理")
    @PreAuthorize("@ss.hasPermi('user:userSocialAccounts:edit')")
    @Log(title = "用户社交账号管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserSocialAccounts userSocialAccounts) {
        return toAjax(userSocialAccountsService.updateUserSocialAccounts(userSocialAccounts));
    }


    @ApiOperation("删除用户社交账号管理")
    @PreAuthorize("@ss.hasPermi('user:userSocialAccounts:remove')")
    @Log(title = "用户社交账号管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(userSocialAccountsService.deleteUserSocialAccountsByIds(ids));
    }
}
