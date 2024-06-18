package com.ruoyi.common.exception.portal;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-18 15:25:58
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-18     张李鑫                     1.0         1.0 Version
 */
public class InsufficientBalanceException  extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public InsufficientBalanceException() {
        super("insufficient.balance.exceed");
    }
}
