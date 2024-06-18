package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.BusWithdrawals;
import com.ruoyi.system.service.IBusWithdrawalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 记录用户的提现信息Controller
 * 
 * @author ruoyi
 * @date 2024-06-18
 */
@RestController
@RequestMapping("/system/withdrawals")
public class BusWithdrawalsController extends BaseController
{
    @Autowired
    private IBusWithdrawalsService busWithdrawalsService;

    /**
     * 查询记录用户的提现信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:withdrawals:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusWithdrawals busWithdrawals)
    {
        startPage();
        List<BusWithdrawals> list = busWithdrawalsService.selectBusWithdrawalsList(busWithdrawals);
        return getDataTable(list);
    }

    /**
     * 导出记录用户的提现信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:withdrawals:export')")
    @Log(title = "记录用户的提现信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusWithdrawals busWithdrawals)
    {
        List<BusWithdrawals> list = busWithdrawalsService.selectBusWithdrawalsList(busWithdrawals);
        ExcelUtil<BusWithdrawals> util = new ExcelUtil<BusWithdrawals>(BusWithdrawals.class);
        util.exportExcel(response, list, "记录用户的提现信息数据");
    }

    /**
     * 获取记录用户的提现信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:withdrawals:query')")
    @GetMapping(value = "/{withdrawalId}")
    public AjaxResult getInfo(@PathVariable("withdrawalId") Long withdrawalId)
    {
        return success(busWithdrawalsService.selectBusWithdrawalsByWithdrawalId(withdrawalId));
    }

    /**
     * 新增记录用户的提现信息
     */
    @PreAuthorize("@ss.hasPermi('system:withdrawals:add')")
    @Log(title = "记录用户的提现信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusWithdrawals busWithdrawals)
    {
        return toAjax(busWithdrawalsService.insertBusWithdrawals(busWithdrawals));
    }

    /**
     * 修改记录用户的提现信息
     */
    @PreAuthorize("@ss.hasPermi('system:withdrawals:edit')")
    @Log(title = "记录用户的提现信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusWithdrawals busWithdrawals)
    {
        return toAjax(busWithdrawalsService.updateBusWithdrawals(busWithdrawals));
    }

    /**
     * 删除记录用户的提现信息
     */
    @PreAuthorize("@ss.hasPermi('system:withdrawals:remove')")
    @Log(title = "记录用户的提现信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{withdrawalIds}")
    public AjaxResult remove(@PathVariable Long[] withdrawalIds)
    {
        return toAjax(busWithdrawalsService.deleteBusWithdrawalsByWithdrawalIds(withdrawalIds));
    }
}
