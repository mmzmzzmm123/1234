package com.ruoyi.psychology.service;

import com.ruoyi.psychology.domain.PsyConsultingClass;
import com.ruoyi.psychology.vo.PsyConsultingClassVO;

import java.util.List;

/**
 * 咨询类型Service接口
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
public interface IPsyConsultingClassService 
{
    PsyConsultingClassVO getOne(Long id);

    List<PsyConsultingClass> getList(PsyConsultingClassVO req);

    int add(PsyConsultingClassVO req);

    int update(PsyConsultingClassVO req);

    int deleteAll(List<Long> ids);

    int delete(Long id);
}
