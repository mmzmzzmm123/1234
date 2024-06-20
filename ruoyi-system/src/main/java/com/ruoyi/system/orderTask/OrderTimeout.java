package com.ruoyi.system.orderTask;

import com.ruoyi.common.delayed.Action;
import com.ruoyi.system.domain.BusPostOrder;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-20 11:06:11
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-20     张李鑫                     1.0         1.0 Version
 */
@FunctionalInterface
public interface OrderTimeout extends Action {


    BusPostOrder orderTimeout(Long orderId);
}
