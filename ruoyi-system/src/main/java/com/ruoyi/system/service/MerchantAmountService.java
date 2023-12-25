package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.entity.MerchantAmount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.dto.ApplyAmountFrozenDTO;

/**
 *
 */
public interface MerchantAmountService extends IService<MerchantAmount> {

    /**
     * 初始化商家资金数据
     * @param merchantId
     */
    void initMerchantAmount(String merchantId);


    /**
     * 申请冻结资金
     * @param dto
     * @return
     */
    String applyAmountFrozen(ApplyAmountFrozenDTO dto);






}
