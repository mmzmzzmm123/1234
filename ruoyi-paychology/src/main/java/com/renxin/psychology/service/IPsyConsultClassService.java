package com.renxin.psychology.service;

import com.renxin.psychology.domain.PsyConsultClass;
import com.renxin.psychology.vo.PsyConsultClassVO;

import java.util.List;

/**
 * 咨询类型Service接口
 * 
 * @author renxin
 * @date 2023-06-16
 */
public interface IPsyConsultClassService
{
    PsyConsultClassVO getOne(Long id);

    List<PsyConsultClass> getList(PsyConsultClassVO req);

    int add(PsyConsultClassVO req);

    int update(PsyConsultClassVO req);

    int deleteAll(Long[] ids);
}
