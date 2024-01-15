package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.entity.robot.RobotStatistics;
import com.ruoyi.system.domain.dto.robot.SelectRobotByRuleDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RobotStatisticsMapper extends BaseMapper<RobotStatistics> {
    List<String> selectRobotByRule(@Param("dto") SelectRobotByRuleDTO dto);
}
