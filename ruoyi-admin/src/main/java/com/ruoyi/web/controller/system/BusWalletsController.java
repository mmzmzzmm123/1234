package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.BusWallets;
import com.ruoyi.system.service.IBusWalletsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 存储用户的账户余额信息Controller
 * 
 * @author ruoyi
 * @date 2024-06-18
 */
@RestController
@RequestMapping("/system/wallets")
public class BusWalletsController extends BaseController
{
    @Autowired
    private IBusWalletsService busWalletsService;

    /**
     * 查询存储用户的账户余额信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:wallets:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusWallets busWallets)
    {
        startPage();
        List<BusWallets> list = busWalletsService.selectBusWalletsList(busWallets);
        return getDataTable(list);
    }

    /**
     * 导出存储用户的账户余额信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:wallets:export')")
    @Log(title = "存储用户的账户余额信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusWallets busWallets)
    {
        List<BusWallets> list = busWalletsService.selectBusWalletsList(busWallets);
        ExcelUtil<BusWallets> util = new ExcelUtil<BusWallets>(BusWallets.class);
        util.exportExcel(response, list, "存储用户的账户余额信息数据");
    }

    /**
     * 获取存储用户的账户余额信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:wallets:query')")
    @GetMapping(value = "/{walletId}")
    public AjaxResult getInfo(@PathVariable("walletId") Long walletId)
    {
        return success(busWalletsService.selectBusWalletsByWalletId(walletId));
    }

    /**
     * 新增存储用户的账户余额信息
     */
    @PreAuthorize("@ss.hasPermi('system:wallets:add')")
    @Log(title = "存储用户的账户余额信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusWallets busWallets)
    {
        return toAjax(busWalletsService.insertBusWallets(busWallets));
    }

    /**
     * 修改存储用户的账户余额信息
     */
    @PreAuthorize("@ss.hasPermi('system:wallets:edit')")
    @Log(title = "存储用户的账户余额信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusWallets busWallets)
    {
        return toAjax(busWalletsService.updateBusWallets(busWallets));
    }

    /**
     * 删除存储用户的账户余额信息
     */
    @PreAuthorize("@ss.hasPermi('system:wallets:remove')")
    @Log(title = "存储用户的账户余额信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{walletIds}")
    public AjaxResult remove(@PathVariable Long[] walletIds)
    {
        return toAjax(busWalletsService.deleteBusWalletsByWalletIds(walletIds));
    }
}
