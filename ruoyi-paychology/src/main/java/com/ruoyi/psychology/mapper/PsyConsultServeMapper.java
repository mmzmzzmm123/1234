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

    List<PsyConsultServe> getList(PsyConsultServeVO req);

    int tombstonedByIds(Long[] ids);

}
