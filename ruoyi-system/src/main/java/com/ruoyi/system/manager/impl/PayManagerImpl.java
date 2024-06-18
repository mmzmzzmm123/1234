package com.ruoyi.system.manager.impl;

import com.ruoyi.common.enums.PayType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.portal.form.PayForm;
import com.ruoyi.system.domain.BusWallets;
import com.ruoyi.system.manager.PayManager;
import com.ruoyi.system.service.BusWalletsExtraService;
import com.ruoyi.system.service.IBusWalletsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-18 16:36:32
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-18     张李鑫                     1.0         1.0 Version
 */
@Component("payManager")
public class PayManagerImpl implements PayManager {


    @Autowired
    private BusWalletsExtraService busWalletsService;


    private BusWallets findWalletByUser() {
        Long userId = SecurityUtils.getUserId();
        BusWallets busWallets = new BusWallets();
        busWallets.setUserId(userId);
        List<BusWallets> busWalletsList = busWalletsService.selectBusWalletsList(busWallets);
        if (busWalletsList.size() != 1) {
            throw new RuntimeException("钱包信息异常");
        }
        return busWalletsList.get(0);
    }

    public boolean wallPay(Long amount) {
        BusWallets wallet = findWalletByUser();
        Long balance = wallet.getBalance();
        if (balance >= amount) {
            return busWalletsService.deduction(wallet, amount);
        }
        return false;
    }

    public boolean wechatPay(Long amount) {

        //todo:
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean pay(Integer payType, Long amount) {
        if (PayType.wallet.getValue() == payType) {
            return wallPay(amount);
        }

        if (PayType.wechat.getValue() == payType) {
            return wechatPay(amount);
        }
        return false;
    }

}
