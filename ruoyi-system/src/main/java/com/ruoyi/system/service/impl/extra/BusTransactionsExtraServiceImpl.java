package com.ruoyi.system.service.impl.extra;

import com.ruoyi.common.enums.TransactionStatus;
import com.ruoyi.common.enums.TransactionType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.BusTransactions;
import com.ruoyi.system.service.extra.BusTransactionsExtraService;
import com.ruoyi.system.service.impl.BusTransactionsServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-18 17:26:35
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-18     张李鑫                     1.0         1.0 Version
 */
@Service
@Primary
public class BusTransactionsExtraServiceImpl extends BusTransactionsServiceImpl implements BusTransactionsExtraService {
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)//不管整体事务是否成功都要留下这个流水记录
    public BusTransactions create(TransactionType transactionType, Long amount) {
        BusTransactions busTransactions = new BusTransactions();
        busTransactions.setStatus(TransactionStatus.ERROR.getRemark());
        busTransactions.setAmount(amount);
        busTransactions.setTransactionType(transactionType.getRemark());
        busTransactions.setUserId(SecurityUtils.getUserId());
        insertBusTransactions(busTransactions);
        return busTransactions;
    }


    @Override
    public int insertBusTransactions(BusTransactions busTransactions) {
        busTransactions.setCreatedAt(new Date());
        busTransactions.setUpdatedAt(new Date());
        return super.insertBusTransactions(busTransactions);
    }

    @Override
    public int updateBusTransactions(BusTransactions busTransactions) {
        busTransactions.setUpdatedAt(new Date());
        return super.updateBusTransactions(busTransactions);
    }
}
