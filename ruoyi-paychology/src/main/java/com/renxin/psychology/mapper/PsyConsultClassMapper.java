package com.renxin.psychology.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.renxin.psychology.domain.PsyConsultClass;
import com.renxin.psychology.vo.PsyConsultClassVO;

import java.util.List;

/**
 * 咨询类型Mapper接口
 * 
 * @author renxin
 * @date 2023-06-16
 */
public interface PsyConsultClassMapper extends BaseMapper<PsyConsultClass>
{

    List<PsyConsultClass> getList(PsyConsultClassVO req);

    int tombstonedByIds(Long[] ids);

}
