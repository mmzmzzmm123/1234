package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.mapper.GroupClusterMapper;
import com.ruoyi.system.domain.GroupCluster;
import com.ruoyi.system.service.GroupClusterService;
import org.springframework.stereotype.Service;

/**
 * 群分组信息(GroupCluster)表服务实现类
 *
 * @author 朱少波
 * @since 2024-01-15 09:31:48
 */
@Service("groupClusterService")
public class GroupClusterServiceImpl extends ServiceImpl<GroupClusterMapper, GroupCluster> implements GroupClusterService {

}
