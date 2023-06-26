package com.ruoyi.psychology.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.psychology.domain.PsyConsult;
import com.ruoyi.psychology.vo.PsyConsultVO;

import java.util.List;

public interface IPsyConsultService {

    PsyConsultVO getOne(Long id);

    List<PsyConsult> getList(PsyConsultVO req);

    AjaxResult add(PsyConsultVO req);

    AjaxResult update(PsyConsultVO req);

    int deleteAll(Long[] ids);

    int delete(Long id);

}
