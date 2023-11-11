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
import com.ruoyi.user.domain.UserOfficialAccount;
import com.ruoyi.user.service.IUserOfficialAccountService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 微信公众号用户Controller
 *
 * @author Lam
 * @date 2023-10-14
 */
@Api(tags = "admin-微信公众号用户")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/userOfficialAccount")
public class UserOfficialAccountController extends BaseController {

    private final IUserOfficialAccountService userOfficialAccountService;

    @ApiOperation("查询微信公众号用户列表")
    @PreAuthorize("@ss.hasPermi('user:userOfficialAccount:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserOfficialAccount userOfficialAccount) {
        startPage();
        List<UserOfficialAccount> list = userOfficialAccountService.selectUserOfficialAccountList(userOfficialAccount);
        return getDataTable(list);
    }


    @ApiOperation("导出微信公众号用户列表")
    @PreAuthorize("@ss.hasPermi('user:userOfficialAccount:export')")
    @Log(title = "微信公众号用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserOfficialAccount userOfficialAccount) {
        List<UserOfficialAccount> list = userOfficialAccountService.selectUserOfficialAccountList(userOfficialAccount);
        ExcelUtil<UserOfficialAccount> util = new ExcelUtil<UserOfficialAccount>(UserOfficialAccount.class);
        util.exportExcel(response, list, "微信公众号用户数据");
    }


    @ApiOperation("获取微信公众号用户详细信息")
    @PreAuthorize("@ss.hasPermi('user:userOfficialAccount:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(userOfficialAccountService.selectUserOfficialAccountById(id));
    }


    @ApiOperation("新增微信公众号用户")
    @PreAuthorize("@ss.hasPermi('user:userOfficialAccount:add')")
    @Log(title = "微信公众号用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserOfficialAccount userOfficialAccount) {
        return toAjax(userOfficialAccountService.insertUserOfficialAccount(userOfficialAccount));
    }

    @ApiOperation("拉取微信公众号用户列表")
    @PreAuthorize("@ss.hasPermi('user:userOfficialAccount:add')")
    @Log(title = "微信公众号用户", businessType = BusinessType.INSERT)
    @GetMapping("/pullNewUserList")
    public AjaxResult pullNewUserList(){
        return userOfficialAccountService.pullNewUserList();
    }


    @ApiOperation("修改微信公众号用户")
    @PreAuthorize("@ss.hasPermi('user:userOfficialAccount:edit')")
    @Log(title = "微信公众号用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserOfficialAccount userOfficialAccount) {
        return toAjax(userOfficialAccountService.updateUserOfficialAccount(userOfficialAccount));
    }

    @ApiOperation("批量更新新用户基本信息")
    @PreAuthorize("@ss.hasPermi('user:userOfficialAccount:edit')")
    @Log(title = "微信公众号用户", businessType = BusinessType.UPDATE)
    @GetMapping("/updateNewUserInfo")
    public AjaxResult updateNewUserInfo(){
        return userOfficialAccountService.updateNewUserInfo();
    }


    @ApiOperation("删除微信公众号用户")
    @PreAuthorize("@ss.hasPermi('user:userOfficialAccount:remove')")
    @Log(title = "微信公众号用户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(userOfficialAccountService.deleteUserOfficialAccountByIds(ids));
    }
}
