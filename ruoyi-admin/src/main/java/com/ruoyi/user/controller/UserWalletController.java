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
import com.ruoyi.user.domain.UserWallet;
import com.ruoyi.user.service.IUserWalletService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户钱包管理Controller
 *
 * @author Lam
 * @date 2023-09-13
 */
@Api(tags = "admin-用户钱包管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/userWallet")
public class UserWalletController extends BaseController {

    private final IUserWalletService userWalletService;


    @ApiOperation("查询用户钱包管理列表")
    @PreAuthorize("@ss.hasPermi('user:userWallet:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserWallet userWallet) {
        startPage();
        List<UserWallet> list = userWalletService.selectUserWalletList(userWallet);
        return getDataTable(list);
    }


    @ApiOperation("导出用户钱包管理列表")
    @PreAuthorize("@ss.hasPermi('user:userWallet:export')")
    @Log(title = "用户钱包管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserWallet userWallet) {
        List<UserWallet> list = userWalletService.selectUserWalletList(userWallet);
        ExcelUtil<UserWallet> util = new ExcelUtil<UserWallet>(UserWallet.class);
        util.exportExcel(response, list, "用户钱包管理数据");
    }


    @ApiOperation("获取用户钱包管理详细信息")
    @PreAuthorize("@ss.hasPermi('user:userWallet:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(userWalletService.selectUserWalletById(id));
    }


    @ApiOperation("新增用户钱包管理")
    @PreAuthorize("@ss.hasPermi('user:userWallet:add')")
    @Log(title = "用户钱包管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserWallet userWallet) {
        return toAjax(userWalletService.insertUserWallet(userWallet));
    }


    @ApiOperation("修改用户钱包管理")
    @PreAuthorize("@ss.hasPermi('user:userWallet:edit')")
    @Log(title = "用户钱包管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserWallet userWallet) {
        return toAjax(userWalletService.updateUserWallet(userWallet));
    }


    @ApiOperation("删除用户钱包管理")
    @PreAuthorize("@ss.hasPermi('user:userWallet:remove')")
    @Log(title = "用户钱包管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(userWalletService.deleteUserWalletByIds(ids));
    }
}
