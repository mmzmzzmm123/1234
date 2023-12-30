package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.entity.MerchantInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.enums.MerchantType;
import com.ruoyi.system.domain.dto.CreateMerchantDTO;
import com.ruoyi.system.domain.dto.QueryMerchantPageDTO;
import com.ruoyi.system.domain.vo.MerchantInfoVO;

import java.util.List;

/**
 *
 */
public interface MerchantInfoService extends IService<MerchantInfo> {

    String createMerchant(CreateMerchantDTO dto);

    MerchantInfo getByUserId(Long userId);

    Page<MerchantInfoVO> page(QueryMerchantPageDTO dto);

    List<String> selectChildMerchantIds(String merchantId);

}
