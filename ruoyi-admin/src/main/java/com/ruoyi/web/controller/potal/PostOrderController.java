package com.ruoyi.web.controller.potal;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.framework.aspectj.DataScopeAspect;
import com.ruoyi.portal.form.BusPostOrderForm;
import com.ruoyi.portal.form.PayForm;
import com.ruoyi.system.domain.BusPostOrder;
import com.ruoyi.system.service.BusPostOrderExtraService;
import com.ruoyi.system.service.IBusPostOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-18 14:19:04
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-18     张李鑫                     1.0         1.0 Version
 */
@RestController
@RestControllerAdvice
@RequestMapping("/api/postOrder")
public class PostOrderController {


    @Autowired
    private BusPostOrderExtraService busPostOrderService;


    /**
     * 发布订单
     *
     * @param busPostOrderForm
     * @return
     */
    @RepeatSubmit
    @PostMapping("/postOrder")
    public AjaxResult postOrder(@RequestBody BusPostOrderForm busPostOrderForm) {
        BusPostOrder busPostOrder = new BusPostOrder();
        BeanUtils.copyProperties(busPostOrderForm, busPostOrder);
        return busPostOrderService.postOrder(busPostOrder) > 0 ? AjaxResult.success(busPostOrder) : AjaxResult.error();
    }


    @GetMapping("/findOrderListByUserId")
    @DataScope(userAlias = DataScopeAspect.DATA_SCOPE_SELF)
    public AjaxResult findOrderListByUserId(@RequestBody BusPostOrderForm busPostOrderForm) {
        return AjaxResult.success(busPostOrderService.findOrderListByUserId(busPostOrderForm));
    }


    @RepeatSubmit
    @PostMapping("/payOrder")
    public AjaxResult payOrder(@RequestBody PayForm payForm) {
        busPostOrderService.payOrder(payForm);
        return AjaxResult.success();
    }
}
