package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.domain.GroupState;
import com.ruoyi.system.mapper.GroupStateMapper;
import com.ruoyi.system.service.GroupInfoService;
import com.ruoyi.system.service.GroupStateService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 群状态信息(GroupState)表服务实现类
 *
 * @author 朱少波
 * @since 2024-01-15 09:32:55
 */
@Service("groupStateService")
public class GroupStateServiceImpl extends ServiceImpl<GroupStateMapper, GroupState> implements GroupStateService {

    @Override
    public void addImportGroup(List<String> groupIds) {
        if (CollUtil.isEmpty(groupIds)) {
            return;
        }
        saveOrUpdateBatch(groupIds.stream().map(groupId -> {
            GroupState groupState = new GroupState();
            groupState.setGroupId(groupId);
            groupState.setGroupUsed(0);
            groupState.setGroupStatus(0);
            groupState.setGroupStatusTime(LocalDateTime.now());
            groupState.setUsedTime(null);
            groupState.setLeaderRobot(1);
            return groupState;
        }).collect(Collectors.toList()));
    }

    @Override
    public void addExternal(String groupId) {
        if (StrUtil.isBlank(groupId)) {
            return;
        }
        GroupState groupState = new GroupState();
        groupState.setGroupId(groupId);
        groupState.setGroupUsed(1);
        groupState.setGroupStatus(0);
        groupState.setGroupStatusTime(LocalDateTime.now());
        groupState.setUsedTime(LocalDateTime.now());
        groupState.setLeaderRobot(0);
        baseMapper.insert(groupState);
    }

    @Override
    public void addExternalGroup(List<String> groupIds) {
        if (CollUtil.isEmpty(groupIds)) {
            return;
        }
        saveBatch(groupIds.stream().map(groupId -> {
            GroupState groupState = new GroupState();
            groupState.setGroupId(groupId);
            groupState.setGroupUsed(1);
            groupState.setGroupStatus(0);
            groupState.setGroupStatusTime(LocalDateTime.now());
            groupState.setUsedTime(LocalDateTime.now());
            groupState.setLeaderRobot(0);
            return groupState;
        }).collect(Collectors.toList()));
    }

    @Override
    public void markUsed(List<String> groupIds, Integer groupUsed) {
        if (CollUtil.isEmpty(groupIds)) {
            return;
        }
        GroupState groupState = new GroupState();
        groupState.setGroupUsed(groupUsed);
        if (ObjectUtil.equal(1, groupUsed)) {
            groupState.setUsedTime(LocalDateTime.now());
        }
        baseMapper.update(groupState, new LambdaQueryWrapper<GroupState>()
                .in(GroupState::getGroupId, groupIds)
                .ne(GroupState::getGroupUsed, groupUsed));
    }

    @Override
    public void banned(List<String> groupIds) {
        if (CollUtil.isEmpty(groupIds)) {
            return;
        }
        GroupState groupState = new GroupState();
        groupState.setGroupStatus(1);
        groupState.setGroupStatusTime(LocalDateTime.now());
        baseMapper.update(groupState, new LambdaQueryWrapper<GroupState>()
                .in(GroupState::getGroupId, groupIds));
    }

    @Override
    public void recover(String groupId) {
        GroupState groupState = new GroupState();
        groupState.setGroupStatus(0);
        groupState.setGroupId(groupId);
        baseMapper.updateById(groupState);
    }

    @Override
    public void groupBaned(String chatroomSerialNo, LocalDateTime banedTime) {
        GroupInfo groupInfo = SpringUtils.getBean(GroupInfoService.class).getGroupBySerialNo(chatroomSerialNo);
        if (groupInfo == null) {
            return;
        }

        LambdaUpdateWrapper<GroupState> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(GroupState::getGroupId, groupInfo.getGroupId())
                .set(GroupState::getGroupStatus, 1)
                .set(banedTime != null, GroupState::getGroupStatusTime, banedTime);
        this.update(updateWrapper);
    }

    @Override
    public void updateUpgradeTime(String groupId) {
        GroupState groupState = new GroupState();
        groupState.setUpgradeTime(LocalDateTime.now());
        groupState.setGroupId(groupId);
        baseMapper.updateById(groupState);
    }
}
