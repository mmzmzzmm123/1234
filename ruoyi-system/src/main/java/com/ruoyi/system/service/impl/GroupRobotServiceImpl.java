package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
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
    public void add(String robotSerialNo, String groupId) {
        GroupRobot groupRobot = getGroupRobot(robotSerialNo, groupId);
        if (groupRobot == null) {
            GroupRobot add = new GroupRobot();
            add.setId(IdWorker.getIdStr());
            add.setGroupId(groupId);
            add.setRobotId(robotSerialNo);
            add.setBotType(0);
            add.setMemberType(0);
            baseMapper.insert(add);
        }
    }

    private GroupRobot getGroupRobot(String robotSerialNo, String groupId) {
        GroupRobot groupRobot = baseMapper.selectOne(new LambdaQueryWrapper<GroupRobot>()
                .eq(GroupRobot::getGroupId, groupId)
                .eq(GroupRobot::getRobotId, robotSerialNo)
                .last(" limit 1"));
        return groupRobot;
    }

    @Override
    public void del(String robotSerialNo, String groupId) {
        GroupRobot groupRobot = getGroupRobot(robotSerialNo, groupId);
        if (groupRobot != null) {
            baseMapper.deleteById(groupRobot.getId());
        }
    }


    @Override
    public GroupRobot getAdminRobot(String groupId) {
        return baseMapper.selectOne(new LambdaQueryWrapper<GroupRobot>()
                .eq(GroupRobot::getGroupId, groupId)
                .eq(GroupRobot::getBotType, 0)
                .in(GroupRobot::getMemberType, 1, 2)
                .orderByAsc(GroupRobot::getMemberType)
                .last(" limit 1"));
    }

    @Override
    public GroupRobot getAnyRobot(String groupId) {

        GroupRobot adminRobot = getAdminRobot(groupId);
        if (adminRobot != null) {
            return adminRobot;
        }
        return baseMapper.selectOne(new LambdaQueryWrapper<GroupRobot>()
                .eq(GroupRobot::getGroupId, groupId)
                .eq(GroupRobot::getBotType, 0)
                .eq(GroupRobot::getMemberType, 0)
                .last(" limit 1"));
    }

    @Override
    public GroupRobot getRobot(String groupId, String robotId) {
        return baseMapper.selectOne(new LambdaQueryWrapper<GroupRobot>()
                .eq(GroupRobot::getGroupId, groupId)
                .eq(GroupRobot::getRobotId, robotId)
                .last(" limit 1"));
    }

    @Override
    public void setAdmin(String groupId, String robotId) {
        GroupRobot groupRobot = getGroupRobot(robotId, groupId);
        if (groupRobot != null && ObjectUtil.notEqual(groupRobot.getMemberType(), 1)) {

        }
    }
}
