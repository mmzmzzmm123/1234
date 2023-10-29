package com.ruoyi.order.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.order.service.IOrderInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单信息Controller
 *
 * @author Lam
 * @date 2023-09-15
 */
@Api(tags = "admin-订单信息")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order/orderInfo")
public class OrderInfoController extends BaseController {

    private final IOrderInfoService orderInfoService;


    @ApiOperation("查询订单信息列表")
    @PreAuthorize("@ss.hasPermi('order:orderInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrderInfo orderInfo) {
        startPage();
        List<OrderInfo> list = orderInfoService.selectOrderInfoList(orderInfo);
        return getDataTable(list);
    }


    @ApiOperation("导出订单信息列表")
    @PreAuthorize("@ss.hasPermi('order:orderInfo:export')")
    @Log(title = "订单信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderInfo orderInfo) {
        List<OrderInfo> list = orderInfoService.selectOrderInfoList(orderInfo);
        ExcelUtil<OrderInfo> util = new ExcelUtil<OrderInfo>(OrderInfo.class);
        util.exportExcel(response, list, "订单信息数据");
    }


    @ApiOperation("获取订单信息详细信息")
    @PreAuthorize("@ss.hasPermi('order:orderInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(orderInfoService.selectOrderInfoById(id));
    }


    @ApiOperation("新增订单信息")
    @PreAuthorize("@ss.hasPermi('order:orderInfo:add')")
    @Log(title = "订单信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderInfo orderInfo) {
        return toAjax(orderInfoService.insertOrderInfo(orderInfo));
    }


    @ApiOperation("修改订单信息")
    @PreAuthorize("@ss.hasPermi('order:orderInfo:edit')")
    @Log(title = "订单信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderInfo orderInfo) {
        return toAjax(orderInfoService.updateOrderInfo(orderInfo));
    }

    @ApiOperation("订单自动完成")
    @PreAuthorize("@ss.hasPermi('order:orderInfo:edit')")
    @Log(title = "订单信息", businessType = BusinessType.UPDATE)
    @PostMapping("/orderFinish/{id}")
    public AjaxResult orderFinish(@PathVariable("id") Long id){
        return orderInfoService.orderFinish(id);
    }


    @ApiOperation("删除订单信息")
    @PreAuthorize("@ss.hasPermi('order:orderInfo:remove')")
    @Log(title = "订单信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(orderInfoService.deleteOrderInfoByIds(ids));
    }
}
