package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.bot.mode.output.BotInfoVO;
import com.ruoyi.system.callback.dto.bot.MemberQuantityDTO;
import com.ruoyi.system.domain.GroupMonitorInfo;
import com.ruoyi.system.mapper.GroupMonitorInfoMapper;
import com.ruoyi.system.service.GroupMonitorInfoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 群bot监控返回信息(GroupMonitorInfo)表服务实现类
 *
 * @author 朱少波
 * @since 2024-01-15 13:51:32
 */
@Service("groupMonitorInfoService")
public class GroupMonitorInfoServiceImpl extends ServiceImpl<GroupMonitorInfoMapper, GroupMonitorInfo> implements GroupMonitorInfoService {

    public List<String> existGroup(List<String> groupIds) {
        return (ArrayList) baseMapper.selectObjs(
                new LambdaQueryWrapper<GroupMonitorInfo>()
                        .in(GroupMonitorInfo::getGroupId, groupIds)
                        .select(GroupMonitorInfo::getGroupId));
    }

    @Override
    public void add(List<String> groupIds) {
        if (CollUtil.isEmpty(groupIds)) {
            return;
        }
        List<String> existGroup = existGroup(groupIds);
        groupIds = groupIds.stream().filter(groupId -> !existGroup.contains(groupId)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(groupIds)) {
            saveBatch(groupIds.stream().map(groupId -> {
                GroupMonitorInfo groupInfo = new GroupMonitorInfo();
                groupInfo.setGroupId(groupId);
                groupInfo.setBotCheck(0);
                groupInfo.setBotAdmin(0);
                return groupInfo;
            }).collect(Collectors.toList()));
        }
    }

    @Override
    public void setPlayId(List<String> groupIds, String playId) {
        if (CollUtil.isEmpty(groupIds)) {
            return;
        }
        updateBatchById(groupIds.stream().map(groupId -> {
            GroupMonitorInfo groupInfo = new GroupMonitorInfo();
            groupInfo.setGroupId(groupId);
            groupInfo.setBotPlayId(playId);
            return groupInfo;
        }).collect(Collectors.toList()));
    }

    @Override
    public void robotCheck(String groupId) {
        GroupMonitorInfo groupInfo = new GroupMonitorInfo();
        groupInfo.setGroupId(groupId);
        groupInfo.setBotCheck(1);
        baseMapper.updateById(groupInfo);
    }

    @Override
    public void updateRobotSerialNo(String groupId, String botSerialNo, String robotSerialNo) {
        GroupMonitorInfo groupInfo = new GroupMonitorInfo();
        groupInfo.setGroupId(groupId);
        groupInfo.setBotSerialNo(botSerialNo);
        groupInfo.setRobotId(robotSerialNo);
        baseMapper.updateById(groupInfo);
    }

    @Override
    public void setBotInfo(String groupId, BotInfoVO bo) {
        GroupMonitorInfo groupInfo = new GroupMonitorInfo();
        groupInfo.setGroupId(groupId);
        groupInfo.setBotId(bo.getBotUserId());
        groupInfo.setBotUsername(bo.getBotUsername());
        groupInfo.setOriginalGroupId("");
        baseMapper.updateById(groupInfo);
    }

    @Override
    public void updateOriginalGroupId(String groupId, String originalGroupId) {
        GroupMonitorInfo groupInfo = new GroupMonitorInfo();
        groupInfo.setOriginalGroupId(originalGroupId);
        groupInfo.setGroupId(groupId);
        baseMapper.updateById(groupInfo);
    }


    @Override
    public void updateOriginalGroupId(String botId, String robotId, String originalGroupId) {
        GroupMonitorInfo groupInfo = new GroupMonitorInfo();
        groupInfo.setOriginalGroupId(originalGroupId);
        baseMapper.update(groupInfo, new LambdaQueryWrapper<GroupMonitorInfo>().eq(GroupMonitorInfo::getBotId, botId)
                .eq(GroupMonitorInfo::getRobotId, robotId).eq(GroupMonitorInfo::getRobotId, robotId).eq(GroupMonitorInfo::getOriginalGroupId, ""));
    }

    @Override
    public int originalGroupIdChange(String botId, String oldGroupId, String newGroupId) {
        GroupMonitorInfo groupInfo = new GroupMonitorInfo();
        groupInfo.setOriginalGroupId(newGroupId);
        return baseMapper.update(groupInfo, new LambdaQueryWrapper<GroupMonitorInfo>()
                .eq(GroupMonitorInfo::getOriginalGroupId, oldGroupId));
    }

    @Override
    public int updateInfo(MemberQuantityDTO dto) {
        GroupMonitorInfo groupInfo = new GroupMonitorInfo();
        groupInfo.setMemberCount(dto.getMemberQuantity());
        groupInfo.setJoinCount(dto.getJoinQuantity());
        groupInfo.setExitCount(dto.getLeaveQuantity());
        groupInfo.setLinkJoinCount(dto.getInviteJoinQuantity());

        return baseMapper.update(groupInfo, new LambdaQueryWrapper<GroupMonitorInfo>()
                .eq(GroupMonitorInfo::getOriginalGroupId, dto.getChatId()));
    }

    @Override
    public void updateInfo(String id, MemberQuantityDTO dto) {
        GroupMonitorInfo groupInfo = new GroupMonitorInfo();
        groupInfo.setMemberCount(dto.getMemberQuantity());
        groupInfo.setJoinCount(dto.getJoinQuantity());
        groupInfo.setExitCount(dto.getLeaveQuantity());
        groupInfo.setLinkJoinCount(dto.getInviteJoinQuantity());
        groupInfo.setGroupId(id);
        baseMapper.updateById(groupInfo);
    }

    @Override
    public void setBotAdmin(String groupId) {
        GroupMonitorInfo groupInfo = new GroupMonitorInfo();
        groupInfo.setBotAdmin(1);
        groupInfo.setGroupId(groupId);
        baseMapper.updateById(groupInfo);
    }

    @Override
    public void setBotAdMonitor(String groupId) {
        GroupMonitorInfo groupInfo = new GroupMonitorInfo();
        groupInfo.setBotAdMonitor(1);
        groupInfo.setGroupId(groupId);
        baseMapper.updateById(groupInfo);
    }

    @Override
    public List<String> updateBanned(String originalGroupId) {

        List<String> groupIds = (ArrayList) baseMapper.selectObjs(
                new LambdaQueryWrapper<GroupMonitorInfo>()
                        .eq(GroupMonitorInfo::getOriginalGroupId, originalGroupId));
        if (CollUtil.isNotEmpty(groupIds)) {
            GroupMonitorInfo groupInfo = new GroupMonitorInfo();
            groupInfo.setGroupStatus(1);
            baseMapper.update(groupInfo, new LambdaQueryWrapper<GroupMonitorInfo>().in(GroupMonitorInfo::getGroupId, groupIds));
        }
        return groupIds;
    }

    @Override
    public void updateBannedById(String groupId) {
        GroupMonitorInfo groupInfo = new GroupMonitorInfo();
        groupInfo.setGroupStatus(1);
        groupInfo.setGroupId(groupId);
        baseMapper.updateById(groupInfo);
    }
}
