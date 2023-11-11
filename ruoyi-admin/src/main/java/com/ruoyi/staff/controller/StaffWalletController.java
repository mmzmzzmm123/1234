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
import com.ruoyi.staff.domain.StaffWallet;
import com.ruoyi.staff.service.IStaffWalletService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 员工钱包Controller
 *
 * @author Lam
 * @date 2023-09-14
 */
@Api(tags = "admin-员工钱包")
@RestController
@RequiredArgsConstructor
@RequestMapping("/staff/staffWallet")
public class StaffWalletController extends BaseController {

    private final IStaffWalletService staffWalletService;


    @ApiOperation("查询员工钱包列表")
    @PreAuthorize("@ss.hasPermi('staff:staffWallet:list')")
    @GetMapping("/list")
    public TableDataInfo list(StaffWallet staffWallet) {
        startPage();
        List<StaffWallet> list = staffWalletService.selectStaffWalletList(staffWallet);
        return getDataTable(list);
    }


    @ApiOperation("导出员工钱包列表")
    @PreAuthorize("@ss.hasPermi('staff:staffWallet:export')")
    @Log(title = "员工钱包", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StaffWallet staffWallet) {
        List<StaffWallet> list = staffWalletService.selectStaffWalletList(staffWallet);
        ExcelUtil<StaffWallet> util = new ExcelUtil<StaffWallet>(StaffWallet.class);
        util.exportExcel(response, list, "员工钱包数据");
    }


    @ApiOperation("获取员工钱包详细信息")
    @PreAuthorize("@ss.hasPermi('staff:staffWallet:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(staffWalletService.selectStaffWalletById(id));
    }


    @ApiOperation("新增员工钱包")
    @PreAuthorize("@ss.hasPermi('staff:staffWallet:add')")
    @Log(title = "员工钱包", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StaffWallet staffWallet) {
        return toAjax(staffWalletService.insertStaffWallet(staffWallet));
    }


    @ApiOperation("修改员工钱包")
    @PreAuthorize("@ss.hasPermi('staff:staffWallet:edit')")
    @Log(title = "员工钱包", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StaffWallet staffWallet) {
        return toAjax(staffWalletService.updateStaffWallet(staffWallet));
    }


    @ApiOperation("删除员工钱包")
    @PreAuthorize("@ss.hasPermi('staff:staffWallet:remove')")
    @Log(title = "员工钱包", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(staffWalletService.deleteStaffWalletByIds(ids));
    }
}
