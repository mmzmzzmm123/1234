package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.mapper.GroupActionLogMapper;
import com.ruoyi.system.domain.GroupActionLog;
import com.ruoyi.system.service.GroupActionLogService;
import org.springframework.stereotype.Service;

/**
 * 群操作记录表(GroupActionLog)表服务实现类
 *
 * @author 朱少波
 * @since 2024-01-16 10:25:55
 */
@Service("groupActionLogService")
public class GroupActionLogServiceImpl extends ServiceImpl<GroupActionLogMapper, GroupActionLog> implements GroupActionLogService {

}
