package com.ruoyi.psychology.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.psychology.domain.PsyConsultingBannerConfig;
import com.ruoyi.psychology.vo.PsyConsultingBannerConfigVO;

/**
 * 咨询banner配置Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
public interface PsyConsultingBannerConfigMapper extends BaseMapper<PsyConsultingBannerConfig>
{

    /**
     * 查询咨询banner配置列表
     */
    List<PsyConsultingBannerConfig> getList(PsyConsultingBannerConfigVO req);

}
