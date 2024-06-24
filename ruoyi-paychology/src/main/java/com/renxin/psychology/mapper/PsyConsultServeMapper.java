package com.renxin.psychology.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.renxin.psychology.domain.PsyConsultServe;
import com.renxin.psychology.vo.PsyConsultServeVO;

import java.util.List;

/**
 * 咨询服务Mapper接口
 * 
 * @author renxin
 * @date 2023-06-16
 */
public interface PsyConsultServeMapper extends BaseMapper<PsyConsultServe>
{

    int batchServeRef(List<PsyConsultServe> refs);

    int tombstonedByIds(Long[] ids);

    List<PsyConsultServeVO> getServeRef(PsyConsultServe req);

    List<PsyConsultServeVO> getConsultServeRef(PsyConsultServe req);

}