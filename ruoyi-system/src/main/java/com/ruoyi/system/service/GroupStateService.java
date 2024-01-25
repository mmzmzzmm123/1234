package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.GroupState;

import java.util.List;

/**
 * 群状态信息(GroupState)表服务接口
 *
 * @author 朱少波
 * @since 2024-01-15 09:32:55
 */
public interface GroupStateService extends IService<GroupState> {

    void addImportGroup(List<String> groupIds);

    void addExternal(String groupId);

    void addExternalGroup(List<String> groupIds);

    void markUsed(List<String> groupIds,Integer groupUsed);

    void banned(List<String> groupIds);

    void recover(String groupId);
}
