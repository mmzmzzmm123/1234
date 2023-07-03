package com.ruoyi.app.controller.psychology;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.psychology.constant.ConsultConstant;
import com.ruoyi.psychology.service.IPsyConsultOrderService;
import com.ruoyi.psychology.service.IPsyConsultServeService;
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
    private IPsyConsultServeService psyConsultServeService;

    @Resource
    private IPsyConsultOrderService psyConsultOrderService;

    @GetMapping(value = "/getOrderInfo/{id}")
    public AjaxResult getOrderInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultOrderService.getOne(id));
    }

    /**
     * 获取订单列表
     */
    @PostMapping(value = "/getOrderList")
    public AjaxResult getOrderList(@RequestBody PsyConsultOrderVO req)
    {
        return AjaxResult.success(psyConsultOrderService.getOrderList(req));
    }

    /**
     * 取消
     */
    @GetMapping(value = "/doConsult/{id}/{workId}")
    public AjaxResult doConsult(@PathVariable("id") Long id, @PathVariable("workId") Long workId)
    {
        return AjaxResult.success(psyConsultOrderService.doConsult(id, workId));
    }

    /**
     * 取消
     */
    @GetMapping(value = "/cancel/{id}")
    public AjaxResult cancel(@PathVariable("id") Long id)
    {
        PsyConsultOrderVO one = psyConsultOrderService.getOne(id);
        one.setStatus(ConsultConstant.CONSULT_ORDER_STATUE_CANCELED);
        return AjaxResult.success(psyConsultOrderService.update(one));
    }

    /**
     * 获取心理咨询师详细信息
     */
    @GetMapping(value = "/getConsultInfoByServe/{id}")
    public AjaxResult getConsultInfoByServe(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultService.getConsultInfoByServe(id));
    }

}
