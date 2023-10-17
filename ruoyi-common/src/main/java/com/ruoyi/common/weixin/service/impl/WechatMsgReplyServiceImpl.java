package com.ruoyi.common.weixin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.weixin.service.WechatMsgReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.builder.kefu.MiniProgramPageBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LAM
 * @date 2023/10/16 0:45
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WechatMsgReplyServiceImpl implements WechatMsgReplyService {

    private final WxMpService wxOfficialAccountService;


    /**
     * 回复文本消息
     *
     * @param toUser  用户openId
     * @param content 文本数据
     */
    @Override
    public void replyText(String toUser, String content) {
        log.info("回复文本消息：开始，参数：{}，{}", toUser, content);
        try {
            wxOfficialAccountService.getKefuService().sendKefuMessage(WxMpKefuMessage.TEXT().toUser(toUser).content(content).build());
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("回复文本消息：异常");
            return;
        }
        log.info("回复文本消息：完成");
    }

    /**
     * 回复图片消息
     *
     * @param toUser  用户openId
     * @param mediaId 素材标识
     */
    @Override
    public void replyImage(String toUser, String mediaId) {
        log.info("回复图片消息：开始，参数：{}，{}", toUser, mediaId);
        try {
            wxOfficialAccountService.getKefuService().sendKefuMessage(WxMpKefuMessage.IMAGE().toUser(toUser).mediaId(mediaId).build());
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("回复图片消息：异常");
            return;
        }
        log.info("回复图片消息：完成");
    }

    /**
     * 回复录音消息
     *
     * @param toUser  用户openId
     * @param mediaId 素材标识
     */
    @Override
    public void replyVoice(String toUser, String mediaId) {
        log.info("回复录音消息：开始，参数：{}，{}", toUser, mediaId);
        try {
            wxOfficialAccountService.getKefuService().sendKefuMessage(WxMpKefuMessage.VOICE().toUser(toUser).mediaId(mediaId).build());
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("回复录音消息：异常");
            return;
        }
        log.info("回复录音消息：完成");
    }

    /**
     * 回复视频消息
     *
     * @param toUser  用户openId
     * @param mediaId 素材标识
     */
    @Override
    public void replyVideo(String toUser, String mediaId) {
        log.info("回复视频消息：开始，参数：{}，{}", toUser, mediaId);
        try {
            wxOfficialAccountService.getKefuService().sendKefuMessage(WxMpKefuMessage.VIDEO().toUser(toUser).mediaId(mediaId).build());
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("回复视频消息：异常");
            return;
        }
        log.info("回复视频消息：完成");
    }

    /**
     * 回复音乐消息
     *
     * @param toUser        用户openId
     * @param musicInfoJson 音乐json数据
     */
    @Override
    public void replyMusic(String toUser, String musicInfoJson) {
        log.info("回复音乐消息：开始，参数：{}，{}", toUser, musicInfoJson);
        try {
            JSONObject json = JSON.parseObject(musicInfoJson);
            wxOfficialAccountService.getKefuService().sendKefuMessage(
                    WxMpKefuMessage.MUSIC().toUser(toUser)
                            .musicUrl(json.getString("musicurl"))
                            .hqMusicUrl(json.getString("hqmusicurl"))
                            .title(json.getString("title"))
                            .description(json.getString("description"))
                            .thumbMediaId(json.getString("thumb_media_id"))
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("回复音乐消息：异常");
            return;
        }
        log.info("回复音乐消息：完成");
    }

    /**
     * 回复图文消息（点击跳转到外链）
     * 图文消息条数限制在1条以内
     *
     * @param toUser       用户openId
     * @param newsInfoJson 数据
     */
    @Override
    public void replyNews(String toUser, String newsInfoJson) {
        log.info("回复图文消息：开始，参数：{}，{}", toUser, newsInfoJson);
        try {
            WxMpKefuMessage.WxArticle wxArticle = JSON.parseObject(newsInfoJson, WxMpKefuMessage.WxArticle.class);
            List<WxMpKefuMessage.WxArticle> newsList = new ArrayList<WxMpKefuMessage.WxArticle>() {{
                add(wxArticle);
            }};
            wxOfficialAccountService.getKefuService().sendKefuMessage(WxMpKefuMessage.NEWS().toUser(toUser).articles(newsList).build());
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("回复图文消息：异常");
            return;
        }
        log.info("回复图文消息：完成");
    }

    /**
     * 回复公众号文章消息（点击跳转到图文消息页面）
     * 图文消息条数限制在1条以内
     *
     * @param toUser  用户openId
     * @param mediaId 素材标识
     */
    @Override
    public void replyMpNews(String toUser, String mediaId) {
        log.info("回复公众号文章消息：开始，参数：{}，{}", toUser, mediaId);
        try {
            wxOfficialAccountService.getKefuService().sendKefuMessage(WxMpKefuMessage.MPNEWS().toUser(toUser).mediaId(mediaId).build());
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("回复公众号文章消息：异常");
            return;
        }
        log.info("回复公众号文章消息：完成");
    }

    /**
     * 回复卡券消息
     *
     * @param toUser 用户openId
     * @param cardId 卡券标识
     */
    @Override
    public void replyWxCard(String toUser, String cardId) {
        log.info("回复公众号文章消息：开始，参数：{}，{}", toUser, cardId);
        try {
            wxOfficialAccountService.getKefuService().sendKefuMessage(WxMpKefuMessage.WXCARD().toUser(toUser).cardId(cardId).build());
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("回复公众号文章消息：异常");
            return;
        }
        log.info("回复公众号文章消息：完成");
    }

    /**
     * 回复小程序消息
     *
     * @param toUser              用户openId
     * @param miniProgramPageBuilder 小程序数据
     */
    @Override
    public void replyMiniProgram(String toUser, MiniProgramPageBuilder miniProgramPageBuilder) {
        log.info("回复小程序消息：开始，参数：{}，{}", toUser, miniProgramPageBuilder);
        try {
            wxOfficialAccountService.getKefuService().sendKefuMessage(miniProgramPageBuilder.toUser(toUser).build());
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("回复小程序消息：异常");
            return;
        }
        log.info("回复小程序消息：完成");
    }

    /**
     * 回复菜单消息
     *
     * @param toUser       用户openId
     * @param msgMenusJson 菜单文本数据
     */
    @Override
    public void replyMsgMenu(String toUser, String msgMenusJson) {
        log.info("回复菜单消息：开始，参数：{}，{}", toUser, msgMenusJson);
        try {
            JSONObject json = JSON.parseObject(msgMenusJson);
            List<WxMpKefuMessage.MsgMenu> msgMenus = JSON.parseArray(json.getString("list"), WxMpKefuMessage.MsgMenu.class);
            wxOfficialAccountService.getKefuService().sendKefuMessage(
                    WxMpKefuMessage.MSGMENU()
                            .toUser(toUser)
                            .headContent(json.getString("head_content"))
                            .tailContent(json.getString("tail_content"))
                            .msgMenus(msgMenus).build());
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("回复菜单消息：异常");
            return;
        }
        log.info("回复菜单消息：完成");
    }
}
