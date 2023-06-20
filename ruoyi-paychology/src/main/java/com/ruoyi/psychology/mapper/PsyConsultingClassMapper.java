package com.ruoyi.psychology.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.psychology.domain.PsyConsultingClass;
import com.ruoyi.psychology.vo.PsyConsultingClassVO;

import java.util.List;

/**
 * 咨询类型Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
public interface PsyConsultingClassMapper extends BaseMapper<PsyConsultingClass>
{

    List<PsyConsultingClass> getList(PsyConsultingClassVO req);

}
