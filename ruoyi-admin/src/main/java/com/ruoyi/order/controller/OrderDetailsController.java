package com.ruoyi.order.controller;

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
import com.ruoyi.order.domain.OrderDetails;
import com.ruoyi.order.service.IOrderDetailsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单明细Controller
 *
 * @author Lam
 * @date 2023-09-15
 */
@Api(tags = "admin-订单明细")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order/orderDetails")
public class OrderDetailsController extends BaseController {

    private final IOrderDetailsService orderDetailsService;


    @ApiOperation("查询订单明细列表")
    @PreAuthorize("@ss.hasPermi('order:orderDetails:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrderDetails orderDetails) {
        startPage();
        List<OrderDetails> list = orderDetailsService.selectOrderDetailsList(orderDetails);
        return getDataTable(list);
    }


    @ApiOperation("导出订单明细列表")
    @PreAuthorize("@ss.hasPermi('order:orderDetails:export')")
    @Log(title = "订单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderDetails orderDetails) {
        List<OrderDetails> list = orderDetailsService.selectOrderDetailsList(orderDetails);
        ExcelUtil<OrderDetails> util = new ExcelUtil<OrderDetails>(OrderDetails.class);
        util.exportExcel(response, list, "订单明细数据");
    }


    @ApiOperation("获取订单明细详细信息")
    @PreAuthorize("@ss.hasPermi('order:orderDetails:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(orderDetailsService.selectOrderDetailsById(id));
    }


    @ApiOperation("新增订单明细")
    @PreAuthorize("@ss.hasPermi('order:orderDetails:add')")
    @Log(title = "订单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderDetails orderDetails) {
        return toAjax(orderDetailsService.insertOrderDetails(orderDetails));
    }


    @ApiOperation("修改订单明细")
    @PreAuthorize("@ss.hasPermi('order:orderDetails:edit')")
    @Log(title = "订单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderDetails orderDetails) {
        return toAjax(orderDetailsService.updateOrderDetails(orderDetails));
    }


    @ApiOperation("删除订单明细")
    @PreAuthorize("@ss.hasPermi('order:orderDetails:remove')")
    @Log(title = "订单明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(orderDetailsService.deleteOrderDetailsByIds(ids));
    }
}
