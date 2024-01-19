package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.GroupActionLog;

import java.util.List;

/**
 * 群操作记录表(GroupActionLog)表服务接口
 *
 * @author 朱少波
 * @since 2024-01-16 10:25:55
 */
public interface GroupActionLogService extends IService<GroupActionLog> {

    void handleActionResult(String id,  String optNo, boolean success, String msg);

    void updateRun(String id);

    List<GroupActionLog> getWaitRunAction();
}
