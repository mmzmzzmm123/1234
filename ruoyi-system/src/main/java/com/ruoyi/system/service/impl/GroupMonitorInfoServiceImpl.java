package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.GroupMonitorInfo;
import com.ruoyi.system.mapper.GroupMonitorInfoMapper;
import com.ruoyi.system.service.GroupMonitorInfoService;
import org.springframework.stereotype.Service;

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

    @Override
    public void add(List<String> groupIds) {
        if (CollUtil.isEmpty(groupIds)) {
            return;
        }
        saveBatch(groupIds.stream().map(groupId -> {
            GroupMonitorInfo groupInfo = new GroupMonitorInfo();
            groupInfo.setGroupId(groupId);
            groupInfo.setBotCheck(0);
            return groupInfo;
        }).collect(Collectors.toList()));
    }

    @Override
    public void setPlayId(List<String> groupIds,String playId) {
        if (CollUtil.isEmpty(groupIds)) {
            return;
        }
        saveBatch(groupIds.stream().map(groupId -> {
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
    public void updateRobotSerialNo(String groupId, String botSerialNo,String robotSerialNo) {
        GroupMonitorInfo groupInfo = new GroupMonitorInfo();
        groupInfo.setGroupId(groupId);
        groupInfo.setBotSerialNo(botSerialNo);
        groupInfo.setRobotId(robotSerialNo);
        baseMapper.updateById(groupInfo);
    }
}
