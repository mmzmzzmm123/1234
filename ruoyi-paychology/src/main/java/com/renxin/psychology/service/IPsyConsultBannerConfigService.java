package com.renxin.psychology.service;

import java.util.List;
import com.renxin.psychology.domain.PsyConsultBannerConfig;
import com.renxin.psychology.vo.PsyConsultBannerConfigVO;

/**
 * 咨询banner配置Service接口
 * 
 * @author renxin
 * @date 2023-06-16
 */
public interface IPsyConsultBannerConfigService
{

    PsyConsultBannerConfigVO getOne(Long id);

    List<PsyConsultBannerConfig> getList(PsyConsultBannerConfigVO req);

    int add(PsyConsultBannerConfigVO req);

    int update(PsyConsultBannerConfigVO req);

    int deleteAll(Long[] ids);

}
