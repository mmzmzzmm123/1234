package com.ruoyi.system.service.impl.extra;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.BusWallets;
import com.ruoyi.system.mapper.extra.BusWalletsExtraMapper;
import com.ruoyi.system.service.extra.BusWalletsExtraService;
import com.ruoyi.system.service.impl.BusWalletsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
@Primary
@Service("BusWalletsExtraService")
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

    @Override
    public List<BusWallets> findWalletByUser() {
        Long userId = SecurityUtils.getUserId();
        BusWallets busWallets = new BusWallets();
        busWallets.setUserId(userId);
        return super.selectBusWalletsList(busWallets);
    }

    @Override
    public int insertBusWallets(BusWallets busWallets) {
        busWallets.setUpdatedAt(new Date());
        busWallets.setCreatedAt(new Date());
        return super.insertBusWallets(busWallets);
    }

    @Override
    public int updateBusWallets(BusWallets busWallets) {
        busWallets.setUpdatedAt(new Date());
        return super.updateBusWallets(busWallets);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean initWallet() {
        List<BusWallets> wallets = findWalletByUser();
        if (wallets.isEmpty()) {
            BusWallets busWallets = new BusWallets();
            Long userId = SecurityUtils.getUserId();
            busWallets.setUserId(userId);
            busWallets.setBalance(0L);
            busWallets.setFrozenBalance(0L);
            busWallets.setVersion(0L);
            return insertBusWallets(busWallets) > 0;
        }
        return true;
    }

    @Override
    public BusWallets selectBusWalletsByUserIdForUpdate() {
        List<BusWallets> wallets = busWalletsExtraMapper.selectBusWalletsByUserIdForUpdate(SecurityUtils.getLoginUser().getUserId());
        if (wallets.isEmpty()) {
            throw new RuntimeException("钱包异常");
        }
        return wallets.get(0);
    }
}
