package com.ruoyi.psychology.service;

import com.ruoyi.psychology.domain.PsyConsultWork;
import com.ruoyi.psychology.dto.WorkDTO;
import com.ruoyi.psychology.request.PsyConsultWorkReq;
import com.ruoyi.psychology.request.PsyWorkReq;
import com.ruoyi.psychology.vo.PsyConsultWorkVO;

import java.util.HashMap;
import java.util.List;

public interface IPsyConsultWorkService {

    void doSave(PsyConsultWorkReq req);

    WorkDTO getWorks(PsyWorkReq req);

    List<PsyConsultWork> checkDataUnique(PsyConsultWorkVO req);

    PsyConsultWorkVO getOne(Long id);

    List<PsyConsultWork> getList(PsyConsultWorkVO req);

    int add(PsyConsultWorkVO req);

    int update(PsyConsultWorkVO req);

    void updateNum(Long id, int num, int buyNum);

    int delete(Long id);

}
