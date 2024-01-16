package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.dto.robot.GetRobotDTO;
import com.ruoyi.system.domain.vo.robot.GetRobotVO;

import java.util.List;

public interface RobotStatisticsService {

    R<List<String>> getRobot(GetRobotDTO dto);

    boolean checkAndAddLeaderCount(String robotSerialNo, Integer count, Integer checkCount);

    void restoreQuantity(String robotSerialNo, Integer count);
}
