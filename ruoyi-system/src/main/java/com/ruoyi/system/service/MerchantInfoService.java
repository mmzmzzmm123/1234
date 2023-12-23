package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.entity.MerchantInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.dto.QueryMerchantPageDTO;

/**
 *
 */
public interface MerchantInfoService extends IService<MerchantInfo> {

    MerchantInfo getByUserId(Long userId);

    Page<MerchantInfo> page(QueryMerchantPageDTO dto);

}
