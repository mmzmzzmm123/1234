package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.entity.MerchantInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.dto.QueryMerchantPageDTO;
import com.ruoyi.system.domain.vo.MerchantInfoVO;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity com.ruoyi.system.domain.MerchantInfo
 */
public interface MerchantInfoMapper extends BaseMapper<MerchantInfo> {

    MerchantInfo getByUserId(@Param("userId") Long userId);

    Page<MerchantInfoVO> page(Page page, @Param("dto") QueryMerchantPageDTO dto);

}




