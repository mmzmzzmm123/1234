package com.ruoyi.psychology.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.psychology.domain.PsyConsultServe;
import com.ruoyi.psychology.domain.PsyConsultServeConfig;
import com.ruoyi.psychology.request.PsyConsultServeConfigReq;

import java.util.List;

/**
 * 咨询服务配置Mapper接口
 * 
 * @author ruoyi
 * @date 2023-07-14
 */
public interface PsyConsultServeConfigMapper extends BaseMapper<PsyConsultServeConfig>
{

    List<PsyConsultServeConfig> getList(PsyConsultServeConfigReq req);

    int tombstonedByIds(Long[] ids);

}
