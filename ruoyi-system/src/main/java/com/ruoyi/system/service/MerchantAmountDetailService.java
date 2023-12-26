package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.MerchantAmountDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.dto.QueryAmountDetailPageDTO;
import com.ruoyi.system.domain.vo.QueryAmountDetailPageVO;

/**
 *
 */
public interface MerchantAmountDetailService extends IService<MerchantAmountDetail> {

    Page<QueryAmountDetailPageVO> page(QueryAmountDetailPageDTO dto);

}
