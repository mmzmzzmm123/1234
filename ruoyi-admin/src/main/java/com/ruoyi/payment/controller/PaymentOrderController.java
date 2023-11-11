package com.ruoyi.payment.controller;

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
import com.ruoyi.payment.domain.PaymentOrder;
import com.ruoyi.payment.service.IPaymentOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 支付订单Controller
 *
 * @author Lam
 * @date 2023-09-15
 */
@Api(tags = "admin-支付订单")
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment/paymentOrder")
public class PaymentOrderController extends BaseController {

    private final IPaymentOrderService paymentOrderService;


    @ApiOperation("查询支付订单列表")
    @PreAuthorize("@ss.hasPermi('payment:paymentOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(PaymentOrder paymentOrder) {
        startPage();
        List<PaymentOrder> list = paymentOrderService.selectPaymentOrderList(paymentOrder);
        return getDataTable(list);
    }


    @ApiOperation("导出支付订单列表")
    @PreAuthorize("@ss.hasPermi('payment:paymentOrder:export')")
    @Log(title = "支付订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PaymentOrder paymentOrder) {
        List<PaymentOrder> list = paymentOrderService.selectPaymentOrderList(paymentOrder);
        ExcelUtil<PaymentOrder> util = new ExcelUtil<PaymentOrder>(PaymentOrder.class);
        util.exportExcel(response, list, "支付订单数据");
    }


    @ApiOperation("获取支付订单详细信息")
    @PreAuthorize("@ss.hasPermi('payment:paymentOrder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(paymentOrderService.selectPaymentOrderById(id));
    }


    @ApiOperation("新增支付订单")
    @PreAuthorize("@ss.hasPermi('payment:paymentOrder:add')")
    @Log(title = "支付订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PaymentOrder paymentOrder) {
        return toAjax(paymentOrderService.insertPaymentOrder(paymentOrder));
    }


    @ApiOperation("修改支付订单")
    @PreAuthorize("@ss.hasPermi('payment:paymentOrder:edit')")
    @Log(title = "支付订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PaymentOrder paymentOrder) {
        return toAjax(paymentOrderService.updatePaymentOrder(paymentOrder));
    }


    @ApiOperation("删除支付订单")
    @PreAuthorize("@ss.hasPermi('payment:paymentOrder:remove')")
    @Log(title = "支付订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(paymentOrderService.deletePaymentOrderByIds(ids));
    }
}
