package com.ruoyi.system.service.extra;

import com.ruoyi.portal.form.BusPostCursorForm;
import com.ruoyi.portal.form.BusPostOrderForm;
import com.ruoyi.system.domain.BusPostOrder;
import com.ruoyi.system.service.IBusPostOrderService;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-18 17:20:55
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-18     张李鑫                     1.0         1.0 Version
 */
public interface BusPostOrderExtraService extends IBusPostOrderService {
    BusPostOrder postOrder(BusPostOrderForm busPostOrder);

//    void payOrder(BusPostOrderForm busPostOrderForm);

    int updateOrderByUserId(BusPostOrderForm busPostOrderForm);

    List<BusPostOrder> findOrderListByUserId(BusPostOrderForm busPostOrderForm);

//    int confirm(Long orderId);

    List<BusPostOrder> findSampleOrder(List<Long>ids);

    BusPostOrder checkOrderTimeoutAndSampling(Long orderId);

    BusPostOrder orderTimeout(Long aLong);

    List<BusPostOrder> list(BusPostCursorForm busPostOrderForm);

    void checkUser(BusPostOrder busPostOrder);

    void checkUser(Long orderId);
}
