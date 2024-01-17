package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.GroupBatchAction;

/**
 * 群批量操作记录表(GroupBatchAction)表服务接口
 *
 * @author 朱少波
 * @since 2024-01-16 10:26:08
 */
public interface GroupBatchActionService extends IService<GroupBatchAction> {
    GroupBatchAction getBatchAction(String groupId,Integer type);

    void updateStatus(String id, Integer status);
}
