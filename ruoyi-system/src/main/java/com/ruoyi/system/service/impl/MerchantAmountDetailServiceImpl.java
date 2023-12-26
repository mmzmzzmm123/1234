package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.MerchantAmountDetail;
import com.ruoyi.system.domain.dto.QueryAmountDetailPageDTO;
import com.ruoyi.system.domain.vo.QueryAmountDetailPageVO;
import com.ruoyi.system.service.MerchantAmountDetailService;
import com.ruoyi.system.mapper.MerchantAmountDetailMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class MerchantAmountDetailServiceImpl extends ServiceImpl<MerchantAmountDetailMapper, MerchantAmountDetail>
    implements MerchantAmountDetailService{

    @Override
    public Page<QueryAmountDetailPageVO> page(QueryAmountDetailPageDTO dto) {
        Page<QueryAmountDetailPageVO> page = new Page<>(dto.getPage(),dto.getLimit());
        List<QueryAmountDetailPageVO> records = baseMapper.page(page, dto);
        page.setRecords(records);
        return page;
    }
}




