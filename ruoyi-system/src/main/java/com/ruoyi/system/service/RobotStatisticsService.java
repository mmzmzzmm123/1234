package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.robot.RobotStatistics;
import com.ruoyi.system.domain.dto.robot.GetRobotDTO;
import com.ruoyi.system.domain.vo.robot.GetRobotVO;

import java.util.List;

public interface RobotStatisticsService extends IService<RobotStatistics> {

    R<List<GetRobotVO>> getRobot(GetRobotDTO dto);

    boolean checkAndAddLeaderCount(String robotSerialNo, Integer count, Integer checkCount);

    void restoreQuantity(String robotSerialNo, Integer count);

    boolean unLockRobot(List<String> robotSerialNos);
}
