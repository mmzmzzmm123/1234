package com.ruoyi.psychology.service;

import com.ruoyi.psychology.domain.PsyConsultServe;
import com.ruoyi.psychology.request.PsyRefConsultServeReq;
import com.ruoyi.psychology.vo.PsyConsultServeVO;

import java.util.List;

public interface IPsyConsultServeService {

    List<PsyConsultServe> getList(PsyConsultServe req);

    PsyConsultServe getOne(PsyConsultServe serveId);

    int getRefCountByConsultId(Long id);

    int batchServeRef(PsyRefConsultServeReq req);

    List<PsyConsultServeVO> getServeRef(PsyConsultServe req);

    List<PsyConsultServeVO> getConsultServeRef(PsyConsultServe req);

    int deleteAll(Long[] ids);
}
