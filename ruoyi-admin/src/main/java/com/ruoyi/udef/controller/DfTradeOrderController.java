package com.ruoyi.udef.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ruoyi.udef.domain.DfTradeOrder;
import com.ruoyi.udef.service.IDfTradeOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 成交订单Controller
 * 
 * @author ruoyi
 * @date 2022-06-08
 */
@RestController
@RequestMapping("/udef/trade")
public class DfTradeOrderController extends BaseController
{
    @Autowired
    private IDfTradeOrderService dfTradeOrderService;

    /**
     * 查询成交订单列表
     */
    @PreAuthorize("@ss.hasPermi('udef:trade:list')")
    @GetMapping("/list")
    public TableDataInfo list(DfTradeOrder dfTradeOrder)
    {
        startPage();
        List<DfTradeOrder> list = dfTradeOrderService.selectDfTradeOrderList(dfTradeOrder);
        return getDataTable(list);
    }

    /**
     * 导出成交订单列表
     */
    @PreAuthorize("@ss.hasPermi('udef:trade:export')")
    @Log(title = "成交订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DfTradeOrder dfTradeOrder)
    {
        List<DfTradeOrder> list = dfTradeOrderService.selectDfTradeOrderList(dfTradeOrder);
        ExcelUtil<DfTradeOrder> util = new ExcelUtil<DfTradeOrder>(DfTradeOrder.class);
        util.exportExcel(response, list, "成交订单数据");
    }

    /**
     * 获取成交订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('udef:trade:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(dfTradeOrderService.selectDfTradeOrderById(id));
    }

    /**
     * 新增成交订单
     */
    @PreAuthorize("@ss.hasPermi('udef:trade:add')")
    @Log(title = "成交订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DfTradeOrder dfTradeOrder)
    {
        return toAjax(dfTradeOrderService.insertDfTradeOrder(dfTradeOrder));
    }

    /**
     * 修改成交订单
     */
    @PreAuthorize("@ss.hasPermi('udef:trade:edit')")
    @Log(title = "成交订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DfTradeOrder dfTradeOrder)
    {
        return toAjax(dfTradeOrderService.updateDfTradeOrder(dfTradeOrder));
    }

    /**
     * 删除成交订单
     */
    @PreAuthorize("@ss.hasPermi('udef:trade:remove')")
    @Log(title = "成交订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(dfTradeOrderService.deleteDfTradeOrderByIds(ids));
    }
}
