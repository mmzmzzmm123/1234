package com.ruoyi.psychology.service;

import com.ruoyi.psychology.domain.PsyConsultServe;
import com.ruoyi.psychology.vo.PsyConsultServeVO;

import java.util.List;

public interface IPsyConsultServeService {

    PsyConsultServeVO getOne(Long id);

    List<PsyConsultServe> getList(PsyConsultServeVO req);

    boolean save(List<PsyConsultServe> entities);

    boolean updateBatch(List<PsyConsultServe> entities);

    int add(PsyConsultServeVO req);

    int update(PsyConsultServeVO req);

    int deleteAll(Long[] ids);

    int delete(Long id);
}
