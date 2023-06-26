package com.ruoyi.psychology.service;

import com.ruoyi.psychology.domain.PsyConsultClass;
import com.ruoyi.psychology.vo.PsyConsultClassVO;

import java.util.List;

/**
 * 咨询类型Service接口
 * 
 * @author ruoyi
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
