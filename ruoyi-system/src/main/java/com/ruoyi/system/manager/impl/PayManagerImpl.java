package com.ruoyi.system.manager.impl;

import com.ruoyi.common.enums.PayType;
import com.ruoyi.common.enums.TransactionStatus;
import com.ruoyi.common.enums.TransactionType;
import com.ruoyi.system.domain.BusTransactions;
import com.ruoyi.system.domain.BusWallets;
import com.ruoyi.system.manager.PayManager;
import com.ruoyi.system.service.extra.BusWalletsExtraService;
import com.ruoyi.system.service.impl.extra.BusTransactionsExtraServiceImpl;
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


    @Autowired
    private BusTransactionsExtraServiceImpl busTransactionsExtraService;

    /**
     * 先创建流水 默认失败
     * 如果扣除成功 更新流水为成功
     * @param amount
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean wallPay(Long amount) {
        BusTransactions busTransactions = busTransactionsExtraService.create(TransactionType.WALLET, amount);
        List<BusWallets> busWalletsList = busWalletsService.findWalletByUser();
        if (busWalletsList.size() != 1) {
            throw new RuntimeException("钱包信息异常");
        }
        BusWallets wallet = busWalletsList.get(0);
        Long balance = wallet.getBalance();
        if (balance >= amount && busWalletsService.deduction(wallet, amount)) {
            busTransactions.setStatus(TransactionStatus.OK.getRemark());
            return busTransactionsExtraService.updateBusTransactions(busTransactions) > 0;
        }
        return false;
    }

    @Override
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
