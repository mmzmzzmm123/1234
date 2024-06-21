package com.ruoyi.web.controller.potal;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-20 15:23:33
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-20     张李鑫                     1.0         1.0 Version
 */
@RestController
@RequestMapping("/api/orderAssignments")
public class OrderAssignmentsController {

    @Autowired
    private OrderService orderService;


    /**
     * 抢单
     * @return
     */
    @RequestMapping("/takeOrder")
    public AjaxResult takeOrder(Long orderId) {
        return AjaxResult.success(orderService.takeOrder(orderId));
    }


    /**
     * 打样
     * @return
     */
    @RequestMapping("/sample")
    public AjaxResult sample(Long orderId) {
        return AjaxResult.success(orderService.takeOrder(orderId));
    }

}
