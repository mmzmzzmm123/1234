package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePushDetail;

import java.util.List;

/**
 *
 */
public interface PlayMessagePushDetailService extends IService<PlayMessagePushDetail> {

    /**
     * 统计查询
     * @param playId
     * @return
     */
    List<PlayMessagePushDetail> listByPlayIdStatistics(String playId);


    List<PlayMessagePushDetail> selectOldRobotMessage(String playId, String spokesmanNickname, String groupId);

    PlayMessagePushDetail selectRobotMessage(String playId, String spokesmanNickname, String groupId, Integer messageSort);

}
