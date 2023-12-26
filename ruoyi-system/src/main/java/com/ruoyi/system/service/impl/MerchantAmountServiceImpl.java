package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.MerchantAmount;
import com.ruoyi.common.enums.AmountOperationType;
import com.ruoyi.common.enums.OperatorType;
import com.ruoyi.system.domain.MerchantAmountDetail;
import com.ruoyi.system.domain.MerchantAmountFrozenDetail;
import com.ruoyi.system.domain.dto.*;
import com.ruoyi.system.service.MerchantAmountDetailService;
import com.ruoyi.system.service.MerchantAmountFrozenDetailService;
import com.ruoyi.system.service.MerchantAmountService;
import com.ruoyi.system.mapper.MerchantAmountMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 *
 */
@Service
public class MerchantAmountServiceImpl extends ServiceImpl<MerchantAmountMapper, MerchantAmount>
        implements MerchantAmountService {

    @Resource
    private MerchantAmountDetailService merchantAmountDetailService;

    @Resource
    private MerchantAmountFrozenDetailService merchantAmountFrozenDetailService;

    @Override
    public void initMerchantAmount(String merchantId) {
        if (StringUtils.isEmpty(merchantId)) {
            return;
        }
        MerchantAmount merchantAmount = super.getById(merchantId);
        if (merchantAmount != null) {
            return;
        }
        merchantAmount = new MerchantAmount();
        merchantAmount.setMerchantId(merchantId);
        super.save(merchantAmount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String applyAmountFrozen(ApplyAmountFrozenDTO dto) {
        Assert.isTrue((dto.getAmount() != null && dto.getAmount() > 0), "冻结金额参数异常");
        MerchantAmount merchantAmount = super.getById(dto.getMerchantId());
        Assert.notNull(merchantAmount, "商家不存在");
        Assert.isTrue(merchantAmount.getAvailableAmount() >= dto.getAmount(), "商家可用余额不足");

        merchantAmount.setLockAmount(merchantAmount.getLockAmount() + dto.getAmount());
        merchantAmount.setAvailableAmount(merchantAmount.getTotalAmount() - merchantAmount.getLockAmount());
        super.updateById(merchantAmount);

        String frozenId = IdWorker.getIdStr();
        MerchantAmountFrozenDetail frozenDetail = new MerchantAmountFrozenDetail();
        frozenDetail.setFrozenId(frozenId);
        frozenDetail.setFrozenAmount(dto.getAmount());
        frozenDetail.setMerchantId(dto.getMerchantId());
        frozenDetail.setDescribe(dto.getDescribe());
        frozenDetail.setOrderId(dto.getOrderId());
        boolean save = merchantAmountFrozenDetailService.save(frozenDetail);
        Assert.isTrue(save, "资金冻结失败");
        return frozenId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void amountTransfer(AmountTransferDTO dto) {
        MerchantAmount merchantAmount = super.getById(dto.getMerchantId());
        Assert.notNull(merchantAmount, "划拨商家不存在");
        Assert.isTrue(merchantAmount.getAvailableAmount() > dto.getAmount(), "划拨商家可用资金不足");
        MerchantAmount targetMerchantAmount = super.getById(dto.getToMerchantId());
        Assert.notNull(targetMerchantAmount, "接收资金商家不存在");

        MerchantAmountDetail detail = new MerchantAmountDetail();
        detail.setDetailId(IdWorker.getIdStr());
        detail.setMerchantId(dto.getMerchantId());
        detail.setOperationType(AmountOperationType.TRANSFER.getOperationType());
        detail.setDescribe(AmountOperationType.TRANSFER.getOperationName());
        detail.setChangeBefore(merchantAmount.getTotalAmount());
        detail.setChangeAmount(dto.getAmount());
        MerchantAmountDetail targetDetail = new MerchantAmountDetail();
        targetDetail.setDetailId(IdWorker.getIdStr());
        targetDetail.setMerchantId(dto.getToMerchantId());
        targetDetail.setOperationType(AmountOperationType.TRANSFER.getOperationType());
        targetDetail.setDescribe(AmountOperationType.TRANSFER.getOperationName());
        targetDetail.setChangeBefore(targetMerchantAmount.getTotalAmount());
        targetDetail.setChangeAmount(dto.getAmount());
        // 原商家扣减
        merchantAmount.setTotalAmount(merchantAmount.getTotalAmount() - dto.getAmount());
        merchantAmount.setAvailableAmount(merchantAmount.getAvailableAmount() - dto.getAmount());
        detail.setChangeAfter(merchantAmount.getTotalAmount());
        // 接收商家 增加
        targetMerchantAmount.setTotalAmount(targetMerchantAmount.getTotalAmount() + dto.getAmount());
        targetMerchantAmount.setAvailableAmount(targetMerchantAmount.getAvailableAmount() + dto.getAmount());
        targetDetail.setChangeAfter(targetMerchantAmount.getTotalAmount());
        // 修改余额
        super.updateById(merchantAmount);
        super.updateById(targetMerchantAmount);
        // 保存资金流水
        merchantAmountDetailService.save(detail);
        merchantAmountDetailService.save(targetDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void amountConsumption(AmountConsumptionDTO dto) {
        MerchantAmountFrozenDetail frozenDetail = merchantAmountFrozenDetailService.getById(dto.getFrozenId());
        Assert.notNull(frozenDetail, "冻结单不存在");
        MerchantAmount merchantAmount = super.getById(frozenDetail.getMerchantId());
        Assert.notNull(merchantAmount, "商家不存在");
        long amount = frozenDetail.getFrozenAmount();
        if (dto.getAmount() != null) {
            Assert.isTrue(dto.getAmount() >= 0L, "实际扣款金额应大于等于0");
            Assert.isTrue(dto.getAmount() <= frozenDetail.getFrozenAmount(), "实际扣款金额应小于冻结金额");
            amount = dto.getAmount();
        }
        MerchantAmountDetail detail = new MerchantAmountDetail();
        detail.setDetailId(IdWorker.getIdStr());
        detail.setMerchantId(frozenDetail.getMerchantId());
        detail.setOperationType(AmountOperationType.CONSUMPTION.getOperationType());
        detail.setDescribe(AmountOperationType.CONSUMPTION.getOperationName());
        detail.setChangeBefore(merchantAmount.getTotalAmount());
        detail.setChangeAmount(amount);
        detail.setOrderId(frozenDetail.getOrderId());

        merchantAmount.setTotalAmount(merchantAmount.getTotalAmount() - amount);
        merchantAmount.setLockAmount(merchantAmount.getLockAmount() - frozenDetail.getFrozenAmount());
        merchantAmount.setAvailableAmount(merchantAmount.getAvailableAmount() + (frozenDetail.getFrozenAmount() - amount));

        super.updateById(merchantAmount);
        detail.setChangeAfter(merchantAmount.getTotalAmount());
        merchantAmountDetailService.save(detail);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void amountRefund(AmountRefundDTO dto) {
        Assert.notNull(dto.getDescribe(),"退款描述必填");
        Assert.isTrue((dto.getAmount() != null && dto.getAmount() > 0), "退款金额参数异常");
        MerchantAmount merchantAmount = super.getById(dto.getMerchantId());
        Assert.notNull(merchantAmount, "商家不存在");

        MerchantAmountDetail detail = new MerchantAmountDetail();
        detail.setDetailId(IdWorker.getIdStr());
        detail.setMerchantId(dto.getMerchantId());
        detail.setOperationType(AmountOperationType.REFUND.getOperationType());
        detail.setDescribe(dto.getDescribe());
        detail.setChangeBefore(merchantAmount.getTotalAmount());
        detail.setChangeAmount(dto.getAmount());
        detail.setOrderId(dto.getOrderId());

        merchantAmount.setTotalAmount(merchantAmount.getTotalAmount() + dto.getAmount());
        merchantAmount.setAvailableAmount(merchantAmount.getAvailableAmount() + dto.getAmount());

        super.updateById(merchantAmount);
        detail.setChangeAfter(merchantAmount.getTotalAmount());
        merchantAmountDetailService.save(detail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void amountDeduction(AmountDeductionDTO dto) {
        Assert.notNull(dto.getDescribe(),"扣款描述必填");
        Assert.isTrue((dto.getAmount() != null && dto.getAmount() > 0), "扣款金额参数异常");
        MerchantAmount merchantAmount = super.getById(dto.getMerchantId());
        Assert.notNull(merchantAmount, "商家不存在");
        Assert.isTrue(merchantAmount.getAvailableAmount() >= dto.getAmount(), "商家可用余额不足");

        MerchantAmountDetail detail = new MerchantAmountDetail();
        detail.setDetailId(IdWorker.getIdStr());
        detail.setMerchantId(dto.getMerchantId());
        detail.setOperationType(AmountOperationType.DEDUCTION.getOperationType());
        detail.setDescribe(dto.getDescribe());
        detail.setChangeBefore(merchantAmount.getTotalAmount());
        detail.setChangeAmount(dto.getAmount());

        merchantAmount.setTotalAmount(merchantAmount.getTotalAmount() - dto.getAmount());
        merchantAmount.setAvailableAmount(merchantAmount.getAvailableAmount() - dto.getAmount());

        super.updateById(merchantAmount);
        detail.setChangeAfter(merchantAmount.getTotalAmount());
        merchantAmountDetailService.save(detail);
    }
}




