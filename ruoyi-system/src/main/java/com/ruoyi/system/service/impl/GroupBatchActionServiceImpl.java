package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.GroupBatchAction;
import com.ruoyi.system.mapper.GroupBatchActionMapper;
import com.ruoyi.system.service.GroupBatchActionService;
import org.springframework.stereotype.Service;

/**
 * 群批量操作记录表(GroupBatchAction)表服务实现类
 *
 * @author 朱少波
 * @since 2024-01-16 10:26:08
 */
@Service("groupBatchActionService")
public class GroupBatchActionServiceImpl extends ServiceImpl<GroupBatchActionMapper, GroupBatchAction> implements GroupBatchActionService {

    @Override
    public GroupBatchAction getBatchAction(String groupId, Integer type) {
        return baseMapper.selectOne(new LambdaQueryWrapper<GroupBatchAction>()
                .eq(GroupBatchAction::getSetType, type)
                .eq(GroupBatchAction::getGroupId, groupId)
                .eq(GroupBatchAction::getBatchStatus, 0)
                .last(" limit 1"));
    }

    @Override
    public void updateStatus(String id, Integer status) {
        GroupBatchAction action = new GroupBatchAction();
        action.setBatchId(id);
        action.setBatchStatus(status);
        baseMapper.updateById(action);
    }
}
