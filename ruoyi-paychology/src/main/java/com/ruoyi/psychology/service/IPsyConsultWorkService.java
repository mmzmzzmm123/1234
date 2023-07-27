package com.ruoyi.psychology.service;

import com.ruoyi.psychology.domain.PsyConsultWork;
import com.ruoyi.psychology.dto.WorkDTO;
import com.ruoyi.psychology.request.PsyConsultWorkReq;
import com.ruoyi.psychology.request.PsyWorkReq;
import com.ruoyi.psychology.vo.PsyConsultWorkVO;

import java.util.List;

public interface IPsyConsultWorkService {

    List<PsyConsultWork> getList(PsyConsultWorkVO req);


    List<PsyConsultWorkVO> getConsultWorks(PsyWorkReq req);

    List<PsyConsultWork> checkDataUnique(PsyConsultWorkVO req);

    WorkDTO getWorks(PsyWorkReq req);

    PsyConsultWorkVO getOne(Long id);

    Boolean checkWork(Long id, Long consultId, Integer time);

    // type 1-试用 2-释放
    PsyConsultWork handleWork(Long id, Long consultId, Integer time, int type);

    void doSave(PsyConsultWorkReq req);

    int add(PsyConsultWorkVO req);

    int update(PsyConsultWorkVO req);

    int delete(Long id);

}
