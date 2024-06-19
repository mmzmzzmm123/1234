package com.renxin.psychology.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.renxin.psychology.domain.PsyConsultBannerConfig;
import com.renxin.psychology.vo.PsyConsultBannerConfigVO;

/**
 * 咨询banner配置Mapper接口
 * 
 * @author renxin
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
