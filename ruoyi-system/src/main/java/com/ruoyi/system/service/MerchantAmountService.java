package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.entity.MerchantAmount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.dto.*;

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


    /**
     * 资金划拨
     * A- B+
     */
    void amountTransfer(AmountTransferDTO dto);

    /**
     * 消费
     * 按冻结单来扣款
     */
    void amountConsumption(AmountConsumptionDTO dto);

    /**
     * 退款
     * 描述必填
     */
    void amountRefund(AmountRefundDTO dto);

    /**
     * 扣款
     * 直接扣钱 描述必填
     */
    void amountDeduction(AmountDeductionDTO dto);

    /**
     * 回收
     */
    void amountRecovery(AmountRecoveryDTO dto);

}
