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
import com.ruoyi.payment.domain.PaymentRefund;
import com.ruoyi.payment.service.IPaymentRefundService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 支付单退款Controller
 *
 * @author Lam
 * @date 2023-09-15
 */
@Api(tags = "admin-支付单退款")
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment/paymentRefund")
public class PaymentRefundController extends BaseController {

    private final IPaymentRefundService paymentRefundService;

    @ApiOperation("查询支付单退款列表")
    @PreAuthorize("@ss.hasPermi('payment:paymentRefund:list')")
    @GetMapping("/list")
    public TableDataInfo list(PaymentRefund paymentRefund) {
        startPage();
        List<PaymentRefund> list = paymentRefundService.selectPaymentRefundList(paymentRefund);
        return getDataTable(list);
    }


    @ApiOperation("导出支付单退款列表")
    @PreAuthorize("@ss.hasPermi('payment:paymentRefund:export')")
    @Log(title = "支付单退款", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PaymentRefund paymentRefund) {
        List<PaymentRefund> list = paymentRefundService.selectPaymentRefundList(paymentRefund);
        ExcelUtil<PaymentRefund> util = new ExcelUtil<PaymentRefund>(PaymentRefund.class);
        util.exportExcel(response, list, "支付单退款数据");
    }


    @ApiOperation("获取支付单退款详细信息")
    @PreAuthorize("@ss.hasPermi('payment:paymentRefund:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(paymentRefundService.selectPaymentRefundById(id));
    }


    @ApiOperation("新增支付单退款")
    @PreAuthorize("@ss.hasPermi('payment:paymentRefund:add')")
    @Log(title = "支付单退款", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PaymentRefund paymentRefund) {
        return toAjax(paymentRefundService.insertPaymentRefund(paymentRefund));
    }


    @ApiOperation("修改支付单退款")
    @PreAuthorize("@ss.hasPermi('payment:paymentRefund:edit')")
    @Log(title = "支付单退款", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PaymentRefund paymentRefund) {
        return toAjax(paymentRefundService.updatePaymentRefund(paymentRefund));
    }


    @ApiOperation("删除支付单退款")
    @PreAuthorize("@ss.hasPermi('payment:paymentRefund:remove')")
    @Log(title = "支付单退款", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(paymentRefundService.deletePaymentRefundByIds(ids));
    }
}
