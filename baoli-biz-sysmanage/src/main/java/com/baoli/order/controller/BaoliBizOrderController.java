package com.baoli.order.controller;

import com.baoli.order.domain.BaoliBizOrder;
import com.baoli.order.service.IBaoliBizOrderService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 订单Controller
 *
 * @author niujs
 * @date 2024-04-07
 */
@RestController
@RequestMapping("/order/orderManage")
public class BaoliBizOrderController extends BaseController
{
    @Autowired
    private IBaoliBizOrderService baoliBizOrderService;

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('order:orderManage:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizOrder baoliBizOrder)
    {
        startPage();
        List<BaoliBizOrder> list = baoliBizOrderService.selectBaoliBizOrderList(baoliBizOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('order:orderManage:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizOrder baoliBizOrder)
    {
        List<BaoliBizOrder> list = baoliBizOrderService.selectBaoliBizOrderList(baoliBizOrder);
        ExcelUtil<BaoliBizOrder> util = new ExcelUtil<BaoliBizOrder>(BaoliBizOrder.class);
        util.exportExcel(response, list, "订单数据");
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:orderManage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizOrderService.selectBaoliBizOrderById(id));
    }

    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermi('order:orderManage:add')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizOrder baoliBizOrder)
    {
        baoliBizOrder.setApplicantId(getUserId());
        return toAjax(baoliBizOrderService.insertBaoliBizOrder(baoliBizOrder));
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('order:orderManage:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizOrder baoliBizOrder)
    {
        return toAjax(baoliBizOrderService.updateBaoliBizOrder(baoliBizOrder));
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('order:orderManage:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizOrderService.deleteBaoliBizOrderByIds(ids));
    }
}
