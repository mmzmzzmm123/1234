package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.domain.UserMerchantRef;
import com.ruoyi.system.domain.dto.CreateMerchantDTO;
import com.ruoyi.system.domain.dto.CreateUserMerchantRefDTO;
import com.ruoyi.system.service.MerchantInfoService;
import com.ruoyi.system.service.UserMerchantRefService;
import com.ruoyi.system.mapper.UserMerchantRefMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 *
 */
@Service
public class UserMerchantRefServiceImpl extends ServiceImpl<UserMerchantRefMapper, UserMerchantRef>
        implements UserMerchantRefService {

    @Resource
    private MerchantInfoService merchantInfoService;

    @Override
    public void createUserMerchantRef(CreateUserMerchantRefDTO dto) {

        // 创建出商家
        CreateMerchantDTO merchantDTO = new CreateMerchantDTO();
        BeanUtils.copyProperties(dto, merchantDTO);
        merchantDTO.setMerchantName(dto.getUserName());
        String merchantId = merchantInfoService.createMerchant(merchantDTO);
        Assert.notNull(merchantId, "商家创建失败");

        UserMerchantRef ref = new UserMerchantRef();
        ref.setUserId(dto.getUserId());
        ref.setMerchantId(merchantId);
        super.save(ref);
    }
}




