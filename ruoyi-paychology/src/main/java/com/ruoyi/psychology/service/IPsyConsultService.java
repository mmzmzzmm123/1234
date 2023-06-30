package com.ruoyi.psychology.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.psychology.domain.PsyConsult;
import com.ruoyi.psychology.request.PsyConsultReq;
import com.ruoyi.psychology.dto.PsyConsultInfoDTO;
import com.ruoyi.psychology.vo.PsyConsultVO;

import java.util.List;

public interface IPsyConsultService {

    PsyConsultInfoDTO getConsultInfoByServe(Long id);

    PsyConsultVO getOne(Long id);

    List<PsyConsult> search(PsyConsultReq req);

    List<PsyConsult> getList(PsyConsultVO req);

    void updateNum(Long id, int num);

    void updateByApp(PsyConsultVO req);

    AjaxResult add(PsyConsultVO req);

    AjaxResult update(PsyConsultVO req);

    int deleteAll(Long[] ids);

    int delete(Long id);

}
