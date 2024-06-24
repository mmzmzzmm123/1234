package com.ruoyi.system.service;

import com.ruoyi.portal.form.BusPostOrderForm;
import com.ruoyi.system.domain.BusOrderAssignments;

/**
 * Description: 订单相关混合业务类
 * Copyright:   Copyright (c)2024
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-20 16:15:50
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-20     张李鑫                     1.0         1.0 Version
 */
public interface OrderService {
    boolean takeOrder(Long orderId);

    void payOrder(BusPostOrderForm busPostOrderForm);

    int confirm(Long orderId);
}
