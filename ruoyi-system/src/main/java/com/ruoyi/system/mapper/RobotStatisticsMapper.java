package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.entity.robot.RobotStatistics;
import com.ruoyi.system.domain.dto.robot.AddCountDTO;
import com.ruoyi.system.domain.dto.robot.SelectRobotByRuleDTO;
import com.ruoyi.system.domain.vo.robot.SelectRobotByRuleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RobotStatisticsMapper extends BaseMapper<RobotStatistics> {
    List<SelectRobotByRuleVO> selectRobotByRule(@Param("dto") SelectRobotByRuleDTO dto);
    void addCount(@Param("dto") AddCountDTO dto);
}
