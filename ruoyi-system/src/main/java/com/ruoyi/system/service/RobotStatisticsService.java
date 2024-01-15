package com.ruoyi.system.service;

import com.ruoyi.system.domain.dto.robot.GetRobotDTO;
import com.ruoyi.system.domain.vo.robot.GetRobotVO;

import java.util.List;

public interface RobotStatisticsService {

    List<GetRobotVO> getRobot(GetRobotDTO dto);
}
