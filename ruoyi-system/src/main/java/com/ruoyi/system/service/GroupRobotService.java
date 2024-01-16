package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.domain.GroupRobot;
import com.ruoyi.system.domain.vo.GroupResourceVO;

import java.util.List;
import java.util.Map;

/**
 * 群内机器人信息(GroupRobot)表服务接口
 *
 * @author 朱少波
 * @since 2024-01-15 09:32:38
 */
public interface GroupRobotService extends IService<GroupRobot> {

    Map<String,GroupRobot>  addGroupLeader(List<GroupInfo> groupInfos, Map<String,String> robotMap);

    GroupRobot getRobot(String groupId);
}
