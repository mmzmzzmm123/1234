package com.renxin.psychology.service;

import com.renxin.psychology.domain.PsyConsultServeConfig;
import com.renxin.psychology.request.PsyConsultServeConfigReq;
import com.renxin.psychology.request.PsyRefConsultServeReq;
import com.renxin.psychology.vo.PsyConsultServeConfigVO;

import java.util.List;

/**
 * 咨询服务配置Service接口
 * 
 * @author renxin
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

    void updateNum(Long id);

    int add(PsyConsultServeConfigVO req);

    int update(PsyConsultServeConfigVO req);

    int deleteAll(Long[] ids);

    int delete(Long id);

}
