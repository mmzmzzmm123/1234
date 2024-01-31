package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePush;
import com.ruoyi.system.domain.dto.play.PushOperationDTO;
import com.ruoyi.system.domain.dto.play.QueryRobotDetailDTO;
import com.ruoyi.system.domain.vo.play.QueryRobotDetailVO;
import com.ruoyi.system.domain.vo.play.RobotStatisticsVO;

import java.util.List;

/**
 *
 */
public interface PlayMessagePushService extends IService<PlayMessagePush> {


    List<PlayMessagePush> listByPlayId(Integer playId);

    void operation(PushOperationDTO dto);

    /**
     * 暂停群推送
     *
     * @param pushId
     */
    void pauseGroupPush(Integer pushId);

    void pauseGroupPush(PlayMessagePush playMessagePush);

    /**
     * 继续推送
     *
     * @param pushId
     */
    void resumeGroupPush(Integer pushId);

    void resumeGroupPush(PlayMessagePush playMessagePush);

    /**
     * 创建推送记录
     *
     * @param playId
     */
    void createPush(String playId);

    /**
     * 创建混淆推送详情
     */
    void createPushDetail(String playId);


    RobotStatisticsVO getRobotStatisticsVO(String playId);

    /**
     * 账号明细列表
     * @param dto
     * @return
     */
    List<QueryRobotDetailVO> robotDetails(QueryRobotDetailDTO dto);

    List<PlayMessagePush> selectByPlayId(String playId);

    List<PlayMessagePush> selectByPlayIdAndState(String playId, Integer state);

    PlayMessagePush getOneByPlayIdAndGroupId(String playId, String groupId);

    /**
     * 仅状态是待发送的群
     *
     * @param groupId 群ID
     * @param pauseReason 暂停原因
     */
    void pauseGroupPushWhenWaitSend(String groupId, String pauseReason);

    /**
     * 仅更新任务状态暂停中 且暂停原因是指定值
     *
     * @param groupId 群Id
     * @param pauseReason 暂停原因
     */
    void continuePushWhenPause(String groupId, String pauseReason);

    /**
     * 封群处理 暂停任务推送
     * @param groupId
     */
    void bandGroupAndStopPush(String groupSerialNo);


    @Deprecated
    List<PlayMessagePush> selectByGroupIdAndPlayId(String chatroomId, String playId);
}
