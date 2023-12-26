package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.MerchantAmountDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.dto.QueryAmountDetailPageDTO;
import com.ruoyi.system.domain.vo.QueryAmountDetailPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.ruoyi.system.domain.MerchantAmountDetail
 */
public interface MerchantAmountDetailMapper extends BaseMapper<MerchantAmountDetail> {

    List<QueryAmountDetailPageVO> page(Page page, @Param("dto") QueryAmountDetailPageDTO dto);

}




