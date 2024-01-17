package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.domain.entity.play.PlayMessage;
import com.ruoyi.system.domain.PlayMessageConfound;

import java.util.List;

/**
 *
 */
public interface PlayMessageConfoundService extends IService<PlayMessageConfound> {

    /**
     * 按剧本ID创建剧本消息混淆
     * @param playId
     */
    void clearAndCreateConfound(String playId);

    /**
     * 创建剧本消息混淆
     * @param playInfo
     * @param messages
     */
    void clearAndCreateConfound(Play playInfo, List<PlayMessage> messages);
}
