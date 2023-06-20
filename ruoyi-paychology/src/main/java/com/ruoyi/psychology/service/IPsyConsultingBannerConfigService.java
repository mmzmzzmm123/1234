package com.ruoyi.psychology.service;

import java.util.List;
import com.ruoyi.psychology.domain.PsyConsultingBannerConfig;
import com.ruoyi.psychology.vo.PsyConsultingBannerConfigVO;

/**
 * 咨询banner配置Service接口
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
public interface IPsyConsultingBannerConfigService 
{

    PsyConsultingBannerConfigVO getOne(Long id);

    List<PsyConsultingBannerConfig> getList(PsyConsultingBannerConfigVO req);

    int add(PsyConsultingBannerConfigVO req);

    int update(PsyConsultingBannerConfigVO req);

    int deleteAll(List<Long> ids);

    int delete(Long id);
}
