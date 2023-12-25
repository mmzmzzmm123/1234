package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.MerchantAmount;
import com.ruoyi.system.domain.MerchantAmountDetail;
import com.ruoyi.system.domain.MerchantAmountFrozenDetail;
import com.ruoyi.system.domain.dto.ApplyAmountFrozenDTO;
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

        MerchantAmount merchantAmount = super.getById(dto.getMerchantId());
        Assert.notNull(merchantAmount,"");

        String frozenId = IdWorker.getIdStr();
        MerchantAmountFrozenDetail frozenDetail = new MerchantAmountFrozenDetail();
        frozenDetail.setFrozenId(frozenId);
        frozenDetail.setFrozenAmount(dto.getFrozenAmount());
        frozenDetail.setMerchantId(dto.getMerchantId());
        frozenDetail.setDescribe(dto.getDescribe());
        frozenDetail.setOrderId(dto.getOrderId());
        boolean save = merchantAmountFrozenDetailService.save(frozenDetail);
        Assert.isTrue(save,"资金冻结失败");
        return frozenId;
    }
}




