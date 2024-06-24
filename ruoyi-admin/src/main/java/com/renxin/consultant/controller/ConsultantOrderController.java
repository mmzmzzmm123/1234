package com.renxin.consultant.controller;

import com.renxin.common.annotation.RateLimiter;
import com.renxin.common.core.controller.BaseController;
import com.renxin.common.core.domain.AjaxResult;
import com.renxin.psychology.domain.PsyConsultOrder;
import com.renxin.psychology.service.IPsyConsultOrderService;
import com.renxin.psychology.vo.PsyConsultOrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 心理咨询师Controller
 * 
 * @author renxin
 * @date 2022-08-26
 */
@RestController
@RequestMapping("/consultant/order")
@Api(value = "ConsultantCourseController" ,tags = {"咨询师订单Api"})
public class ConsultantOrderController extends BaseController
{
    @Resource
    private IPsyConsultOrderService psyConsultOrderService;

    @ApiOperation(value = "查询订单信息")
    @GetMapping(value = "/getOrderInfo/{id}")
    @RateLimiter
    public AjaxResult getOrderInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultOrderService.getOne(id));
    }

    @ApiOperation(value = "查询订单详情")
    @GetMapping(value = "/getOrderDetail/{id}")
    @RateLimiter
    public AjaxResult getOrderDetail(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultOrderService.getOrderDetail(id));
    }

    @ApiOperation(value = "订单ID查询详情")
    @GetMapping(value = "/getOrderDetailByNo/{orderNo}")
    @RateLimiter
    public AjaxResult getOrderDetailByNo(@PathVariable("orderNo") String orderNo)
    {
        return AjaxResult.success(psyConsultOrderService.getOrderDetailByNo(orderNo));
    }

    @ApiOperation(value = "获取订单列表")
    @PostMapping(value = "/getOrderList")
    @RateLimiter
    public AjaxResult getOrderList(@RequestBody PsyConsultOrderVO req)
    {
        return AjaxResult.success(psyConsultOrderService.getOrderList(req));
    }

    @ApiOperation(value = "咨询")
    @PostMapping(value = "/doConsult/{id}/{workId}/{time}")
    @RateLimiter
    public AjaxResult doConsult(@PathVariable("id") Long id, @PathVariable("workId") Long workId, @PathVariable("time") Integer time)
    {
        return AjaxResult.success(psyConsultOrderService.doConsult(id, workId, time));
    }

    @ApiOperation(value = "取消")
    @GetMapping(value = "/cancel/{id}")
    @RateLimiter
    public AjaxResult cancel(@PathVariable("id") Long id)
    {
        PsyConsultOrder order = psyConsultOrderService.getOrderById(id);
        psyConsultOrderService.cancel(order, order.getNickName());
        return AjaxResult.success();
    }

}
