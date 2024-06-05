package com.ruoyi.psychology.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.psychology.domain.PsyConsult;
import com.ruoyi.psychology.domain.PsyConsultServe;
import com.ruoyi.psychology.request.PsyAdminConsultReq;
import com.ruoyi.psychology.request.PsyConsultReq;
import com.ruoyi.psychology.dto.PsyConsultInfoDTO;
import com.ruoyi.psychology.request.PsyRefConsultServeReq;
import com.ruoyi.psychology.vo.PsyConsultVO;
import com.ruoyi.psychology.vo.PsyConsultWorkVO;

import java.util.List;

public interface IPsyConsultService {

    PsyConsultInfoDTO getConsultInfoByServe(Long cId, Long sId);

    List<PsyConsultWorkVO> getConsultWorksById(Long id);

    PsyConsultVO getOne(Long id);

    PsyConsult getByPhone(String phone);

    List<PsyConsult> search(PsyConsultReq req);

    List<PsyConsult> getList(PsyConsultVO req);

    List<PsyConsult> getList(PsyAdminConsultReq req);

    void updateNum(Long id, int num);

    void updateByApp(PsyConsultVO req);

    int delConsultServeRef(PsyConsultServe req);

    AjaxResult refConsultServe(PsyRefConsultServeReq req);

    AjaxResult add(PsyConsultVO req);

    AjaxResult update(PsyConsultVO req);

    String getAvailableUserName(String name);

    int deleteAll(Long[] ids);

    int delete(Long id);

}
