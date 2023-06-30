package com.ruoyi.psychology.service;

import com.ruoyi.psychology.domain.PsyConsultWork;
import com.ruoyi.psychology.vo.PsyConsultWorkVO;

import java.util.List;

public interface IPsyConsultWorkService {

    List<PsyConsultWork> checkDataUnique(PsyConsultWorkVO req);

    PsyConsultWorkVO getOne(Long id);

    List<PsyConsultWork> getList(PsyConsultWorkVO req);

    int add(PsyConsultWorkVO req);

    int update(PsyConsultWorkVO req);

    void updateNum(Long id, int num);

    int deleteAll(Long[] ids);

    int delete(Long id);

}
