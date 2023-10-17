package com.ruoyi.common.weixin.service;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.builder.kefu.MiniProgramPageBuilder;

/**
 * @author LAM
 * @date 2023/10/16 0:40
 */
public interface WechatMsgReplyService {


    /**
     * 回复文字消息
     *
     * @param toUser       用户openId
     * @param replyContent 文本数据
     */
    void replyText(String toUser, String replyContent);

    /**
     * 回复图片消息
     *
     * @param toUser  用户openId
     * @param mediaId 素材标识
     */
    void replyImage(String toUser, String mediaId);

    /**
     * 回复录音消息
     *
     * @param toUser  用户openId
     * @param mediaId 素材标识
     */
    void replyVoice(String toUser, String mediaId);

    /**
     * 回复视频消息
     *
     * @param toUser  用户openId
     * @param mediaId 素材标识
     */
    void replyVideo(String toUser, String mediaId);

    /**
     * 回复音乐消息
     *
     * @param toUser        用户openId
     * @param musicInfoJson 音乐数据JSON
     */
    void replyMusic(String toUser, String musicInfoJson);

    /**
     * 回复图文消息（点击跳转到外链）
     * 图文消息条数限制在1条以内
     *
     * @param toUser       用户openId
     * @param newsInfoJson 数据
     */
    void replyNews(String toUser, String newsInfoJson);

    /**
     * 回复公众号文章消息（点击跳转到图文消息页面）
     * 图文消息条数限制在1条以内
     *
     * @param toUser  用户openId
     * @param mediaId 素材标识
     */
    void replyMpNews(String toUser, String mediaId);

    /**
     * 回复卡券消息
     *
     * @param toUser 用户openId
     * @param cardId 卡券标识
     */
    void replyWxCard(String toUser, String cardId);

    /**
     * 回复小程序消息
     *
     * @param toUser              用户openId
     * @param miniProgramPageBuilder 小程序数据
     */
    void replyMiniProgram(String toUser, MiniProgramPageBuilder miniProgramPageBuilder);

    /**
     * 回复菜单消息
     *
     * @param toUser       用户openId
     * @param msgMenusJson 菜单文本数据
     */
    void replyMsgMenu(String toUser, String msgMenusJson);

}
