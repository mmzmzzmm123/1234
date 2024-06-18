package com.ruoyi.web.controller.potal;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.portal.form.BusPostOrderForm;
import com.ruoyi.portal.form.PayForm;
import com.ruoyi.system.domain.BusPostOrder;
import com.ruoyi.system.service.BusPostOrderExtraService;
import com.ruoyi.system.service.IBusPostOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
     * @param busPostOrderForm
     * @return
     */
    @RequestMapping("/postOrder")
    public AjaxResult postOrder(BusPostOrderForm busPostOrderForm){
        BusPostOrder busPostOrder = new BusPostOrder();
        BeanUtils.copyProperties(busPostOrderForm,busPostOrder);
        return  busPostOrderService.postOrder(busPostOrder)>0? AjaxResult.success(busPostOrder):AjaxResult.error();
    }



    @RequestMapping("/payOrder")
    public AjaxResult payOrder(PayForm payForm){
        busPostOrderService.payOrder(payForm);
        return AjaxResult.success();
    }
}
