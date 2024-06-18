package com.ruoyi.system.service;

import com.ruoyi.portal.form.PayForm;
import com.ruoyi.system.domain.BusPostOrder;

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
public interface BusPostOrderExtraService extends IBusPostOrderService{
    int postOrder(BusPostOrder busPostOrder);

    void payOrder(PayForm payForm);
}
