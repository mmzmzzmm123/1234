package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.domain.GroupRobot;

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

    void add(String robotSerialNo, String groupId);

    void del(String robotSerialNo, String groupId);

    GroupRobot getAdminRobot(String groupId);

    GroupRobot getAnyRobot(String groupId);

    GroupRobot getRobot(String groupId, String robotId);

    void setAdmin(String groupId,String robotId);
}
