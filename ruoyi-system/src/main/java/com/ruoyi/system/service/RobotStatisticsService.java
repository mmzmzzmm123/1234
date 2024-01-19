package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.robot.RobotStatistics;
import com.ruoyi.system.domain.dto.robot.GetRobotDTO;
import com.ruoyi.system.domain.vo.robot.GetRobotVO;

import java.util.List;

public interface RobotStatisticsService extends IService<RobotStatistics> {

    /**
     * 获取水军
     * @param dto
     * @return
     */
    R<List<GetRobotVO>> getRobot(GetRobotDTO dto);

    boolean checkAndAddLeaderCount(String robotSerialNo, Integer count, Integer checkCount);

    void restoreQuantity(String robotSerialNo, Integer count);

    /**
     * 取消锁定水军
     * @param robotSerialNos
     * @return
     */
    boolean unLockRobot(List<String> robotSerialNos);

    /**
     * 减少带群数
     * @param robotSerialNos
     * @return
     */
    boolean decreaseRobotGroup(List<String> robotSerialNos);

    /**
     * 清除水军每日累计数据
     * @return
     */
    void clearRobotOneDayNum();
}
