package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.GroupCluster;
import com.ruoyi.system.domain.dto.GroupClusterNameDTO;
import com.ruoyi.system.domain.dto.GroupClusterNameUpdateDTO;
import com.ruoyi.system.mapper.GroupClusterMapper;
import com.ruoyi.system.service.GroupClusterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * 群分组信息(GroupCluster)表服务实现类
 *
 * @author 朱少波
 * @since 2024-01-15 09:31:48
 */
@Service("groupClusterService")
@RequiredArgsConstructor
public class GroupClusterServiceImpl extends ServiceImpl<GroupClusterMapper, GroupCluster> implements GroupClusterService {


    @Override
    public List<GroupCluster> queryRobotRadio(GroupClusterNameDTO dto) {
        List<GroupCluster> groupList = baseMapper.selectList(new LambdaQueryWrapper<GroupCluster>()
                .like(StrUtil.isNotBlank(dto.getClusterName()), GroupCluster::getClusterName, dto.getClusterName())
                .orderByAsc(GroupCluster::getCreateTime));
        if (CollUtil.isEmpty(groupList) && StrUtil.isBlank(dto.getClusterName())) {
            groupList = Collections.singletonList(getAndAddDefault());
        }
        return groupList;
    }


    public GroupCluster getAndAddDefault() {
        GroupCluster defaultCluster = new GroupCluster();
        defaultCluster.setClusterId("1001");
        defaultCluster.setClusterName("默认分组");
        defaultCluster.setClusterType(1);
        defaultCluster.setMerchantId("");
        defaultCluster.setCreateTime(LocalDateTime.now());
        defaultCluster.setUpdateTime(LocalDateTime.now());
        try {
            save(defaultCluster);
        } catch (Exception ignored) {

        }
        return defaultCluster;
    }

    @Override
    public void add(GroupClusterNameDTO dto) {
        Assert.notBlank(dto.getClusterName(), "分组名不能为空");

        //检查组名
        checkName(dto.getClusterName());
        GroupCluster add = new GroupCluster();
        add.setClusterId(IdWorker.getIdStr());
        add.setClusterName(dto.getClusterName());
        add.setClusterType(0);
        add.setMerchantId("");
        add.setCreateTime(LocalDateTime.now());
        add.setUpdateTime(LocalDateTime.now());
        baseMapper.insert(add);
    }

    public void checkName(String name) {
        Assert.isFalse(baseMapper.selectCount(new LambdaQueryWrapper<GroupCluster>()
                .eq(GroupCluster::getClusterName, name)) > 0, "分组名重复");
    }

    @Override
    public void update(GroupClusterNameUpdateDTO dto) {
        Assert.isNull(dto.getClusterId(), "分组id不能为空");
        Assert.notBlank(dto.getClusterName(), "分组名不能为空");
        GroupCluster old = baseMapper.selectById(dto.getClusterId());
        Assert.isNull(old, "分组不存在");
        if (ObjectUtil.equal(dto.getClusterName(), old.getClusterName())) {
            return;
        }
        checkName(dto.getClusterName());

        GroupCluster update = new GroupCluster();
        update.setClusterId(dto.getClusterId());
        update.setClusterName(dto.getClusterName());
        update.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(update);
    }

    @Override
    public void delete(String clusterId) {
        Assert.isNull(clusterId, "分组id不能为空");
        GroupCluster old = baseMapper.selectById(clusterId);
        Assert.isNull(old, "分组不存在");
        Assert.isFalse(ObjectUtil.equal(old.getClusterType(), 1), "默认分组不允许删除");
        baseMapper.deleteById(clusterId);
    }

    @Override
    public String getClusterDefault(String clusterId) {
        if(StrUtil.isBlank(clusterId)){
            return "1001";
        }

        GroupCluster old = baseMapper.selectById(clusterId);
        if (old != null) {
            return old.getClusterId();
        }
        return "1001";
    }
}
