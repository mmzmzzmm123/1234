package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePush;

import java.util.List;

/**
 *
 */
public interface PlayMessagePushService extends IService<PlayMessagePush> {


    List<PlayMessagePush> listByPlayId(Integer playId);

    void operation(Integer pushId, Integer op);

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

}
