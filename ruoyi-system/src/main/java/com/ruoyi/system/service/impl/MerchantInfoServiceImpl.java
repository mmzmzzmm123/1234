package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.MerchantInfo;
import com.ruoyi.system.domain.dto.QueryMerchantPageDTO;
import com.ruoyi.system.service.MerchantInfoService;
import com.ruoyi.system.mapper.MerchantInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 *
 */
@Service
public class MerchantInfoServiceImpl extends ServiceImpl<MerchantInfoMapper, MerchantInfo>
        implements MerchantInfoService {


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
        if (StringUtils.isEmpty(merchantId)){
            return page;
        }
        dto.setPlMerchantId(merchantId);
        baseMapper.page(page,dto);

        return page;
    }
}




