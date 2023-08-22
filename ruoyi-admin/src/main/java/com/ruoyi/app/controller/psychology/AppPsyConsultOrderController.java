package com.ruoyi.app.controller.psychology;

import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.psychology.service.IPsyConsultOrderService;
import com.ruoyi.psychology.service.IPsyConsultService;
import com.ruoyi.psychology.vo.PsyConsultOrderVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 心理咨询师Controller
 * 
 * @author ruoyi
 * @date 2022-08-26
 */
@RestController
@RequestMapping("/app/consult/order")
public class AppPsyConsultOrderController extends BaseController
{
    @Resource
    private IPsyConsultService psyConsultService;

    @Resource
    private IPsyConsultOrderService psyConsultOrderService;

    @GetMapping(value = "/getOrderInfo/{id}")
    @RateLimiter
    public AjaxResult getOrderInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultOrderService.getOne(id));
    }

    @GetMapping(value = "/getOrderDetail/{id}")
    @RateLimiter
    public AjaxResult getOrderDetail(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultOrderService.getOrderDetail(id));
    }

    @GetMapping(value = "/getOrderDetailByNo/{orderNo}")
    @RateLimiter
    public AjaxResult getOrderDetailByNo(@PathVariable("orderNo") String orderNo)
    {
        return AjaxResult.success(psyConsultOrderService.getOrderDetailByNo(orderNo));
    }

    /**
     * 获取订单列表
     */
    @PostMapping(value = "/getOrderList")
    @RateLimiter
    public AjaxResult getOrderList(@RequestBody PsyConsultOrderVO req)
    {
        return AjaxResult.success(psyConsultOrderService.getOrderList(req));
    }

    /**
     * 咨询
     */
    @PostMapping(value = "/doConsult/{id}/{workId}/{time}")
    @RateLimiter
    public AjaxResult doConsult(@PathVariable("id") Long id, @PathVariable("workId") Long workId, @PathVariable("time") Integer time)
    {
        return AjaxResult.success(psyConsultOrderService.doConsult(id, workId, time));
    }

    /**
     * 取消
     */
    @GetMapping(value = "/cancel/{id}")
    @RateLimiter
    public AjaxResult cancel(@PathVariable("id") Long id)
    {
        psyConsultOrderService.cancel(psyConsultOrderService.getOrderById(id));
        return AjaxResult.success();
    }

    /**
     * 获取心理咨询师详细信息
     */
    @GetMapping(value = "/getConsultInfoByServe/{cId}/{sId}")
    @RateLimiter
    public AjaxResult getConsultInfoByServe(@PathVariable("cId") Long cId, @PathVariable("sId") Long sId)
    {
        return AjaxResult.success(psyConsultService.getConsultInfoByServe(cId, sId));
    }

}
