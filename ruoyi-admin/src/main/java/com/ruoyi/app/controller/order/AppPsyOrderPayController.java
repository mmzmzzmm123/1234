package com.ruoyi.app.controller.order;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.gauge.domain.PsyOrderPay;
import com.ruoyi.gauge.service.IPsyOrderPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 心理咨询订单支付信息Controller
 *
 * @author ruoyi
 * @date 2022-10-12
 */
@RestController
@RequestMapping("/app/gauge/order/pay")
@Api(value = "AppPsyOrderPayController" ,tags = {"心理测评支付信息api"})
public class AppPsyOrderPayController extends BaseController {
    @Autowired
    private IPsyOrderPayService psyOrderPayService;

    /**
     * 查询心理咨询订单支付信息列表
     */
//    @PreAuthorize("@ss.hasPermi('system:pay:list')")
    @GetMapping("/list")
    @ApiIgnore
    public TableDataInfo list(PsyOrderPay psyOrderPay) {
        startPage();
        List<PsyOrderPay> list = psyOrderPayService.selectPsyOrderPayList(psyOrderPay);
        return getDataTable(list);
    }

    /**
     * 导出心理咨询订单支付信息列表
     */
//    @PreAuthorize("@ss.hasPermi('system:pay:export')")
    @Log(title = "心理咨询订单支付信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiIgnore
    public void export(HttpServletResponse response, PsyOrderPay psyOrderPay) {
        List<PsyOrderPay> list = psyOrderPayService.selectPsyOrderPayList(psyOrderPay);
        ExcelUtil<PsyOrderPay> util = new ExcelUtil<PsyOrderPay>(PsyOrderPay.class);
        util.exportExcel(response, list, "心理咨询订单支付信息数据");
    }

    /**
     * 获取心理咨询订单支付信息详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:pay:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取支付信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(psyOrderPayService.selectPsyOrderPayById(id));
    }
}
