package com.ruoyi.system.service.extra;

import com.ruoyi.system.domain.BusWallets;
import com.ruoyi.system.service.IBusWalletsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    List<BusWallets>  findWalletByUser();

    boolean initWallet();

    BusWallets selectBusWalletsByUserIdForUpdate();
}
