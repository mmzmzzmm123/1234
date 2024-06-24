package com.ruoyi.system.manager;

import com.ruoyi.portal.form.PayForm;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-18 16:36:24
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-18     张李鑫                     1.0         1.0 Version
 */
public interface PayManager {

    boolean wallPay(Long amount);

    boolean wechatPay(Long amount);

    boolean pay(Integer payType, Long amount);
}
