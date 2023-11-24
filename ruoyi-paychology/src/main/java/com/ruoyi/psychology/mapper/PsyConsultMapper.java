package com.ruoyi.psychology.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.psychology.domain.PsyConsult;
import com.ruoyi.psychology.request.PsyConsultReq;
import com.ruoyi.psychology.vo.PsyConsultVO;

import java.util.List;

/**
 * 咨询师Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
public interface PsyConsultMapper extends BaseMapper<PsyConsult>
{

    List<PsyConsult> search(PsyConsultReq req);

    List<PsyConsult> getList(PsyConsultVO req);

    Long getConsultByUserId(Long userId);

    int tombstonedByIds(Long[] ids);


}
