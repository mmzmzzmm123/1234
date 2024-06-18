package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.BusWallets;
import com.ruoyi.system.mapper.BusWalletsExtraMapper;
import com.ruoyi.system.service.BusWalletsExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-18 16:58:34
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-18     张李鑫                     1.0         1.0 Version
 */
@Component("BusWalletsExtraService")
public class BusWalletsExtraServiceImpl extends BusWalletsServiceImpl implements BusWalletsExtraService {
    @Autowired
    private BusWalletsExtraMapper busWalletsExtraMapper;


    /**
     * 扣款
     *
     * @param wallet
     * @param amount
     * @return
     */
    @Override
    public boolean deduction(BusWallets wallet, Long amount) {
        return busWalletsExtraMapper.deduction(wallet, amount) > 0;
    }
}
