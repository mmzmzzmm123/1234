package com.renxin.psychology.service;

import com.renxin.psychology.vo.PsyConsultColumnVO;

import java.util.List;

public interface IPsyConsultColumnService {

    PsyConsultColumnVO getOne(Long id);

    List<PsyConsultColumnVO> getList(PsyConsultColumnVO req);

    int add(PsyConsultColumnVO req);

    int update(PsyConsultColumnVO req);

    int delete(Long id);

}
