package com.ruoyi.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.user.domain.PsyUserIntegralRecord;
import com.ruoyi.user.request.IntegralSearchReq;
import com.ruoyi.user.vo.PsyUserIntegralRecordVO;

import java.util.List;

/**
 * 用户积分记录Mapper接口
 * 
 * @author ruoyi
 * @date 2023-07-10
 */
public interface PsyUserIntegralRecordMapper extends BaseMapper<PsyUserIntegralRecord>
{

    List<PsyUserIntegralRecordVO> getList(IntegralSearchReq req);

}
