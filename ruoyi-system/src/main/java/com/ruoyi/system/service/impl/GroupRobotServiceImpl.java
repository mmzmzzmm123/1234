package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.domain.GroupRobot;
import com.ruoyi.system.mapper.GroupRobotMapper;
import com.ruoyi.system.service.GroupRobotService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 群内机器人信息(GroupRobot)表服务实现类
 *
 * @author 朱少波
 * @since 2024-01-15 09:32:38
 */
@Service("groupRobotService")
public class GroupRobotServiceImpl extends ServiceImpl<GroupRobotMapper, GroupRobot> implements GroupRobotService {

    @Override
    public Map<String, GroupRobot> addGroupLeader(List<GroupInfo> groupInfos, Map<String, String> robotMap) {
        if (CollUtil.isEmpty(groupInfos)) {
            return new HashMap<>();
        }
        Map<String, GroupRobot> result = new HashMap<>();
        saveBatch(groupInfos.stream().map(p -> {
            GroupRobot groupRobot = new GroupRobot();
            groupRobot.setId(IdWorker.getIdStr());
            groupRobot.setGroupId(p.getGroupId());
            groupRobot.setRobotId(robotMap.get(p.getGroupId()));
            groupRobot.setBotType(0);
            groupRobot.setMemberType(1);
            result.put(p.getGroupId(), groupRobot);
            return groupRobot;
        }).collect(Collectors.toList()));
        return result;
    }

    @Override
    public GroupRobot getRobot(String groupId) {
        return baseMapper.selectOne(new LambdaQueryWrapper<GroupRobot>()
                .eq(GroupRobot::getGroupId, groupId)
                .eq(GroupRobot::getBotType, 0)
                .in(GroupRobot::getMemberType, 1, 2)
                .orderByAsc(GroupRobot::getMemberType)
                .last(" limit 1"));
    }
}
