package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.robot.RobotStatistics;
import com.ruoyi.system.domain.dto.robot.GetRobotDTO;
import com.ruoyi.system.domain.vo.robot.GetRobotVO;
import com.ruoyi.system.mapper.RobotStatisticsMapper;
import com.ruoyi.system.service.RobotStatisticsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RobotStatisticsServiceImpl extends ServiceImpl<RobotStatisticsMapper, RobotStatistics> implements RobotStatisticsService {

    @Override
    public List<GetRobotVO> getRobot(GetRobotDTO dto) {
        return new ArrayList<>();
    }
}
