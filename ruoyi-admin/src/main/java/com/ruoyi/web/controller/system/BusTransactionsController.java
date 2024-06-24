package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.BusTransactions;
import com.ruoyi.system.service.IBusTransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 记录用户的充值、提现等交易信息Controller
 * 
 * @author ruoyi
 * @date 2024-06-18
 */
@RestController
@RequestMapping("/system/transactions")
public class BusTransactionsController extends BaseController
{
    @Autowired
    private IBusTransactionsService busTransactionsService;

    /**
     * 查询记录用户的充值、提现等交易信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:transactions:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusTransactions busTransactions)
    {
        startPage();
        List<BusTransactions> list = busTransactionsService.selectBusTransactionsList(busTransactions);
        return getDataTable(list);
    }

    /**
     * 导出记录用户的充值、提现等交易信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:transactions:export')")
    @Log(title = "记录用户的充值、提现等交易信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusTransactions busTransactions)
    {
        List<BusTransactions> list = busTransactionsService.selectBusTransactionsList(busTransactions);
        ExcelUtil<BusTransactions> util = new ExcelUtil<BusTransactions>(BusTransactions.class);
        util.exportExcel(response, list, "记录用户的充值、提现等交易信息数据");
    }

    /**
     * 获取记录用户的充值、提现等交易信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:transactions:query')")
    @GetMapping(value = "/{transactionId}")
    public AjaxResult getInfo(@PathVariable("transactionId") Long transactionId)
    {
        return success(busTransactionsService.selectBusTransactionsByTransactionId(transactionId));
    }

    /**
     * 新增记录用户的充值、提现等交易信息
     */
    @PreAuthorize("@ss.hasPermi('system:transactions:add')")
    @Log(title = "记录用户的充值、提现等交易信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusTransactions busTransactions)
    {
        return toAjax(busTransactionsService.insertBusTransactions(busTransactions));
    }

    /**
     * 修改记录用户的充值、提现等交易信息
     */
    @PreAuthorize("@ss.hasPermi('system:transactions:edit')")
    @Log(title = "记录用户的充值、提现等交易信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusTransactions busTransactions)
    {
        return toAjax(busTransactionsService.updateBusTransactions(busTransactions));
    }

    /**
     * 删除记录用户的充值、提现等交易信息
     */
    @PreAuthorize("@ss.hasPermi('system:transactions:remove')")
    @Log(title = "记录用户的充值、提现等交易信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{transactionIds}")
    public AjaxResult remove(@PathVariable Long[] transactionIds)
    {
        return toAjax(busTransactionsService.deleteBusTransactionsByTransactionIds(transactionIds));
    }
}
