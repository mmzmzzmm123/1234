package com.ruoyi.psychology.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.psychology.domain.PsyConsultWork;
import com.ruoyi.psychology.request.PsyConsultWorkReq;
import com.ruoyi.psychology.request.PsyWorkReq;
import com.ruoyi.psychology.vo.PsyConsultOrderItemVO;
import com.ruoyi.psychology.vo.PsyConsultWorkVO;

import java.util.List;

/**
 * 咨询排班表Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
public interface PsyConsultWorkMapper extends BaseMapper<PsyConsultWork>
{

    List<PsyConsultWork> checkDataUnique(PsyConsultWorkVO req);

    List<PsyConsultWork> getList(PsyConsultWorkVO req);

    List<PsyConsultWorkVO> getWorks(PsyWorkReq req);

    List<PsyConsultOrderItemVO> getOrderItems(PsyWorkReq req);

}
