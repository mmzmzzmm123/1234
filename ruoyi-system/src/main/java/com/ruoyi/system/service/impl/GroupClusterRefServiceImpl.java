package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.mapper.GroupClusterRefMapper;
import com.ruoyi.system.domain.GroupClusterRef;
import com.ruoyi.system.service.GroupClusterRefService;
import org.springframework.stereotype.Service;

/**
 * 群分组关联表(GroupClusterRef)表服务实现类
 *
 * @author 朱少波
 * @since 2024-01-15 09:32:03
 */
@Service("groupClusterRefService")
public class GroupClusterRefServiceImpl extends ServiceImpl<GroupClusterRefMapper, GroupClusterRef> implements GroupClusterRefService {

}
