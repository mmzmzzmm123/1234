package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.PlayMessageConfound;
import com.ruoyi.system.domain.vo.PlayMessageConfusionStateVO;

import java.util.List;

/**
 * @Entity com.uc.linkmaster.firegroup.domain.PlayMessageConfound
 */
public interface PlayMessageConfoundMapper extends BaseMapper<PlayMessageConfound> {

    List<PlayMessageConfusionStateVO> selectConfusionStateStatistics();

}




