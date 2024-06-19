package com.renxin.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.renxin.user.domain.PsyUserIntegralRecord;
import com.renxin.user.request.IntegralSearchReq;
import com.renxin.user.vo.PsyUserIntegralRecordVO;

import java.util.List;

/**
 * 用户积分记录Mapper接口
 * 
 * @author renxin
 * @date 2023-07-10
 */
public interface PsyUserIntegralRecordMapper extends BaseMapper<PsyUserIntegralRecord>
{

    List<PsyUserIntegralRecordVO> getList(IntegralSearchReq req);

}
