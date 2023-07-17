package com.ruoyi.psychology.service;

import java.util.List;

import com.ruoyi.psychology.domain.PsyConsultServe;
import com.ruoyi.psychology.domain.PsyConsultServeConfig;
import com.ruoyi.psychology.request.PsyConsultServeConfigReq;
import com.ruoyi.psychology.request.PsyRefConsultServeReq;
import com.ruoyi.psychology.vo.PsyConsultServeConfigVO;

/**
 * 咨询服务配置Service接口
 * 
 * @author ruoyi
 * @date 2023-07-14
 */
public interface IPsyConsultServeConfigService 
{
    PsyConsultServeConfigVO getOne(Long id);

    List<PsyConsultServeConfig> getList(PsyConsultServeConfigReq req);

    List<PsyConsultServeConfig> getListByIds(List<Long> ids);

    int refConsultServe(PsyRefConsultServeReq req);

    boolean setRefNum(PsyRefConsultServeReq req);

    boolean save(List<PsyConsultServeConfig> entities);

    boolean updateBatch(List<PsyConsultServeConfig> entities);

    int add(PsyConsultServeConfigVO req);

    int update(PsyConsultServeConfigVO req);

    int deleteAll(Long[] ids);

    int delete(Long id);
}
