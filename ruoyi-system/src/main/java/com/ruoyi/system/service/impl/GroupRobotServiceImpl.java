package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    public Map<String, GroupRobot> addGroupLeader(List<String> groupIds, Map<String, String> robotMap) {
        if (CollUtil.isEmpty(groupIds)) {
            return new HashMap<>();
        }
        baseMapper.delete(new LambdaQueryWrapper<GroupRobot>()
                .in(GroupRobot::getGroupId, groupIds).eq(GroupRobot::getMemberType, 1));

        Map<String, GroupRobot> result = new HashMap<>();
        saveOrUpdateBatch(groupIds.stream().map(groupId -> {
            GroupRobot groupRobot = new GroupRobot();
            groupRobot.setGroupId(groupId);
            groupRobot.setRobotId(robotMap.get(groupId));
            groupRobot.setMd5Id();
            groupRobot.setBotType(0);
            groupRobot.setMemberType(1);
            result.put(groupId, groupRobot);
            return groupRobot;
        }).collect(Collectors.toList()));
        return result;
    }

    @Override
    public void add(String robotSerialNo, String groupId) {
        GroupRobot add = new GroupRobot();
        add.setGroupId(groupId);
        add.setRobotId(robotSerialNo);
        add.setMd5Id();
        add.setBotType(0);
        add.setMemberType(0);
        saveOrUpdate(add);
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
    public List<GroupRobot> getAdminRobots(String groupId) {
        return baseMapper.selectList(new LambdaQueryWrapper<GroupRobot>()
                .eq(GroupRobot::getGroupId, groupId)
                .eq(GroupRobot::getBotType, 0)
                .in(GroupRobot::getMemberType, 1, 2)
                .orderByAsc(GroupRobot::getMemberType));
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
    public GroupRobot setAdmin(String groupId, String robotId) {
        GroupRobot groupRobot = getGroupRobot(robotId, groupId);
        if (groupRobot != null && ObjectUtil.notEqual(groupRobot.getMemberType(), 1)) {
            groupRobot.setMemberType(2);
            baseMapper.updateById(groupRobot);
        }
        return groupRobot;
    }

    @Override
    public void addBot(String groupId, String changeValue) {
        GroupRobot add = new GroupRobot();
        add.setGroupId(groupId);
        add.setRobotId(changeValue);
        add.setMd5Id();
        add.setBotType(1);
        add.setMemberType(0);
        saveOrUpdate(add);
    }
}
