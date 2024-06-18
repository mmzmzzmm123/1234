package com.ruoyi.system.service;

import com.ruoyi.system.domain.BusWallets;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-18 16:58:11
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-18     张李鑫                     1.0         1.0 Version
 */
public interface BusWalletsExtraService extends IBusWalletsService {

    boolean deduction(BusWallets wallet, Long amount);
}
