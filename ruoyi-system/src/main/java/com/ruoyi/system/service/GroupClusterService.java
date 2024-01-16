package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.GroupCluster;
import com.ruoyi.system.domain.dto.GroupClusterNameDTO;
import com.ruoyi.system.domain.dto.GroupClusterNameUpdateDTO;

import java.util.List;

/**
 * 群分组信息(GroupCluster)表服务接口
 *
 * @author 朱少波
 * @since 2024-01-15 09:31:48
 */
public interface GroupClusterService extends IService<GroupCluster> {
    List<GroupCluster> queryRobotRadio(GroupClusterNameDTO dto);

    void add(GroupClusterNameDTO dto);

    void update(GroupClusterNameUpdateDTO dto);

    void delete(String clusterId);

    String getClusterDefault(String clusterId);
}
