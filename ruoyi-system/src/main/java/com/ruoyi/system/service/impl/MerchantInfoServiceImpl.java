package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.MerchantInfo;
import com.ruoyi.common.enums.MerchantType;
import com.ruoyi.system.domain.dto.CreateMerchantDTO;
import com.ruoyi.system.domain.dto.QueryMerchantPageDTO;
import com.ruoyi.system.service.MerchantAmountService;
import com.ruoyi.system.service.MerchantInfoService;
import com.ruoyi.system.mapper.MerchantInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.IdGenerator;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 *
 */
@Service
public class MerchantInfoServiceImpl extends ServiceImpl<MerchantInfoMapper, MerchantInfo>
        implements MerchantInfoService {

    @Resource
    private MerchantAmountService merchantAmountService;

    @Override
    public String createMerchant(CreateMerchantDTO dto) {
        String merchantId = dto.getMerchantId();
        if (StringUtils.isEmpty(merchantId)) {
            merchantId = IdWorker.getIdStr();
        }
        MerchantInfo info = new MerchantInfo();
        info.setMerchantId(merchantId);
        info.setMerchantName(dto.getMerchantName());
        info.setMerchantType(dto.getMerchantType());
        info.setPlMerchantId(dto.getPlMerchantId());
        super.saveOrUpdate(info);
        // 初始化商家资金
        merchantAmountService.initMerchantAmount(merchantId);
        return merchantId;
    }

    @Override
    public MerchantInfo getByUserId(Long userId) {
        if (userId == null) {
            return null;
        }
        return baseMapper.getByUserId(userId);
    }

    @Override
    public Page<MerchantInfo> page(QueryMerchantPageDTO dto) {
        Page<MerchantInfo> page = new Page<>(dto.getPage(), dto.getLimit());
        MerchantInfo merchantInfo = this.getByUserId(dto.getUserId());
        String merchantId = Optional.ofNullable(merchantInfo)
                .map(MerchantInfo::getMerchantId)
                .orElse(null);
        if (StringUtils.isEmpty(merchantId)) {
            return page;
        }
        dto.setPlMerchantId(merchantId);
        baseMapper.page(page, dto);

        return page;
    }
}




