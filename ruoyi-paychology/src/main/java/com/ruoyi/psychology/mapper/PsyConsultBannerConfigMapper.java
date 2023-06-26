package com.ruoyi.psychology.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.psychology.domain.PsyConsultBannerConfig;
import com.ruoyi.psychology.vo.PsyConsultBannerConfigVO;

/**
 * 咨询banner配置Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
public interface PsyConsultBannerConfigMapper extends BaseMapper<PsyConsultBannerConfig>
{

    /**
     * 查询咨询banner配置列表
     */
    List<PsyConsultBannerConfig> getList(PsyConsultBannerConfigVO req);

    int tombstonedByIds(Long[] ids);

}
