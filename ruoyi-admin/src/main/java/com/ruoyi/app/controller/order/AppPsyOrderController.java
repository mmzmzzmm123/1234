package com.ruoyi.app.controller.order;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.RespMessageConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.service.AppTokenService;
import com.ruoyi.gauge.domain.PsyOrder;
import com.ruoyi.gauge.service.IPsyOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 心理测评订单信息Controller
 *
 * @author ruoyi
 * @date 2022-10-12
 */
@RestController
@RequestMapping("/app/gauge/order")
@Api(value = "AppPsyOrderController" ,tags = {"心理测评订单api"})
public class AppPsyOrderController extends BaseController {
    @Autowired
    private IPsyOrderService psyOrderService;

    @Autowired
    private AppTokenService appTokenService;

    /**
     * 查询心理测评订单信息列表
     */
//    @PreAuthorize("@ss.hasPermi('system:order:list')")
    @PostMapping("/list")
    @ApiOperation("获取订单分页列表")
    public TableDataInfo list(@RequestBody PsyOrder psyOrder) {
        Integer userId = null;
        if (psyOrder.getUserId() != null) {
            userId = Integer.parseInt(psyOrder.getUserId());
        }

        startPage();
        List<PsyOrder> list = psyOrderService.queryOrderInfo(psyOrder ,userId);
        return getDataTable(list);
    }

    /**
     * 导出心理测评订单信息列表
     */
//    @PreAuthorize("@ss.hasPermi('system:order:export')")
    @Log(title = "心理测评订单信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiIgnore
    public void export(HttpServletResponse response, PsyOrder psyOrder) {
        List<PsyOrder> list = psyOrderService.selectPsyOrderList(psyOrder);
        ExcelUtil<PsyOrder> util = new ExcelUtil<PsyOrder>(PsyOrder.class);
        util.exportExcel(response, list, "心理测评订单信息数据");
    }

    /**
     * 获取心理测评订单信息详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:order:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取订单详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(psyOrderService.selectPsyOrderById(id));
    }


    /**
     * 获取我的报告数量
     */
    @PostMapping(value = "/getMyReportNum")
    @ApiOperation("获取我的报告数量")
    public AjaxResult getMyReportNum(HttpServletRequest request) {
        LoginDTO loginUser = appTokenService.getLoginUser(request);
        return AjaxResult.success(RespMessageConstants.OPERATION_SUCCESS ,psyOrderService.getMyReportNum(loginUser));
    }

}
