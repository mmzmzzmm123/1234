package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.mapper.GroupMonitorInfoMapper;
import com.ruoyi.system.domain.GroupMonitorInfo;
import com.ruoyi.system.service.GroupMonitorInfoService;
import org.springframework.stereotype.Service;

/**
 * 群bot监控返回信息(GroupMonitorInfo)表服务实现类
 *
 * @author 朱少波
 * @since 2024-01-15 13:51:32
 */
@Service("groupMonitorInfoService")
public class GroupMonitorInfoServiceImpl extends ServiceImpl<GroupMonitorInfoMapper, GroupMonitorInfo> implements GroupMonitorInfoService {

}
