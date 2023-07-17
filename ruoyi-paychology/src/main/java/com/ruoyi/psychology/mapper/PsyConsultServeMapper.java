package com.ruoyi.psychology.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.psychology.domain.PsyConsultServe;
import com.ruoyi.psychology.vo.PsyConsultServeVO;

import java.util.List;

/**
 * 咨询服务Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
public interface PsyConsultServeMapper extends BaseMapper<PsyConsultServe>
{

    int batchServeRef(List<PsyConsultServe> refs);

    int tombstonedByIds(Long[] ids);

    List<PsyConsultServeVO> getServeRef(PsyConsultServe req);

    List<PsyConsultServeVO> getConsultServeRef(PsyConsultServe req);

}
