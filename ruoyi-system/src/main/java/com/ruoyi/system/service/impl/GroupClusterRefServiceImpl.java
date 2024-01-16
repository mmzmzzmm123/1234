package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.GroupCluster;
import com.ruoyi.system.domain.GroupClusterRef;
import com.ruoyi.system.domain.dto.IdCountDTO;
import com.ruoyi.system.domain.vo.GroupClusterCountVO;
import com.ruoyi.system.mapper.GroupClusterRefMapper;
import com.ruoyi.system.service.GroupClusterRefService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 群分组关联表(GroupClusterRef)表服务实现类
 *
 * @author 朱少波
 * @since 2024-01-15 09:32:03
 */
@Service("groupClusterRefService")
public class GroupClusterRefServiceImpl extends ServiceImpl<GroupClusterRefMapper, GroupClusterRef> implements GroupClusterRefService {

    @Override
    public List<GroupClusterCountVO> fillCount(List<GroupCluster> list) {
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<String> clusterIds = list.stream().map(GroupCluster::getClusterId).collect(Collectors.toList());
        List<IdCountDTO> infos = baseMapper.getClusterCount(clusterIds);
        Map<String, Integer> map = CollUtil.isEmpty(list) ? new HashMap<>() : infos.stream().collect(Collectors.toMap(IdCountDTO::getId, IdCountDTO::getCount));
        return list.stream().map(item -> {
            GroupClusterCountVO vo = BeanUtil.copyProperties(item, GroupClusterCountVO.class);
            vo.setCount(map.getOrDefault(item.getClusterId(), 0));
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public void moving(String oldClusterId, String newClusterId) {
        GroupClusterRef update = new GroupClusterRef();
        update.setClusterId(newClusterId);
        baseMapper.update(update, new LambdaQueryWrapper<GroupClusterRef>().eq(GroupClusterRef::getClusterId, oldClusterId));
    }

    @Override
    public void moving(List<String> groupIds, String newClusterId) {
        if (CollUtil.isEmpty(groupIds)) {
            return;
        }
        GroupClusterRef update = new GroupClusterRef();
        update.setClusterId(newClusterId);
        baseMapper.update(update, new LambdaQueryWrapper<GroupClusterRef>().in(GroupClusterRef::getGroupId, groupIds));
    }

    @Override
    public void add(List<String> groupIds, String newClusterId) {
        if (CollUtil.isEmpty(groupIds)) {
            return;
        }
        saveBatch(groupIds.stream().map(groupId -> {
            GroupClusterRef ref = new GroupClusterRef();
            ref.setRefId(IdWorker.getIdStr());
            ref.setClusterId(newClusterId);
            ref.setGroupId(groupId);
            ref.setCreateTime(LocalDateTime.now());
            return ref;
        }).collect(Collectors.toList()));
    }

    @Override
    public void deleteGroup(List<String> groupIds) {
        baseMapper.delete(new LambdaQueryWrapper<GroupClusterRef>().in(GroupClusterRef::getGroupId, groupIds));
    }


}
