package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.mapper.GroupStateMapper;
import com.ruoyi.system.domain.GroupState;
import com.ruoyi.system.service.GroupStateService;
import org.springframework.stereotype.Service;

/**
 * 群状态信息(GroupState)表服务实现类
 *
 * @author 朱少波
 * @since 2024-01-15 09:32:55
 */
@Service("groupStateService")
public class GroupStateServiceImpl extends ServiceImpl<GroupStateMapper, GroupState> implements GroupStateService {

}
