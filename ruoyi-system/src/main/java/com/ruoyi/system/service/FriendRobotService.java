package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.callback.dto.Called1100910012DTO;
import com.ruoyi.system.callback.dto.Called1100910027DTO;
import com.ruoyi.system.callback.dto.CalledDTO;
import com.ruoyi.system.domain.FriendRobot;

import java.util.List;

public interface FriendRobotService extends IService<FriendRobot> {

    void addSync(CalledDTO root, List<Called1100910027DTO> dto);

    void unlockUpdateData(CalledDTO root, List<Called1100910012DTO> dto);
}
