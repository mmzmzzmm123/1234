package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.GroupCluster;
import com.ruoyi.system.domain.GroupClusterRef;
import com.ruoyi.system.domain.vo.GroupClusterCountVO;

import java.util.List;

/**
 * 群分组关联表(GroupClusterRef)表服务接口
 *
 * @author 朱少波
 * @since 2024-01-15 09:32:03
 */
public interface GroupClusterRefService extends IService<GroupClusterRef> {

    List<GroupClusterCountVO> fillCount(List<GroupCluster> list);

    void moving(String oldClusterId, String newClusterId);

    void moving(List<String> groupIds, String newClusterId);

    List<String> add(List<String> groupIds, String newClusterId);

    void deleteGroup(List<String> groupIds);
}
