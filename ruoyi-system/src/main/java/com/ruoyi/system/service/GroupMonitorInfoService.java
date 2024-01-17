package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.GroupMonitorInfo;

import java.util.List;

/**
 * 群bot监控返回信息(GroupMonitorInfo)表服务接口
 *
 * @author 朱少波
 * @since 2024-01-15 13:51:31
 */
public interface GroupMonitorInfoService extends IService<GroupMonitorInfo> {

    void add(List<String> groupIds);

    void setPlayId(List<String> groupIds,String playId);

    void robotCheck(String groupId);

    void updateRobotSerialNo(String groupId, String botSerialNo,String robotSerialNo);
}
