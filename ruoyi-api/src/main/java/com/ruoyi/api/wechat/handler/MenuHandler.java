package com.ruoyi.api.wechat.handler;

import com.ruoyi.api.wechat.constant.WechatMediaIdConstant;
import com.ruoyi.api.wechat.constant.WechatMenuKeyConstant;
import com.ruoyi.common.weixin.service.WechatMsgReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MenuHandler implements WxMpMessageHandler {

    private final WechatMsgReplyService msgReplyService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
        log.info("微信公众号点击菜单事件：开始，参数：{}，{}", wxMessage, context);
        // 发送图片消息(在线客服)
        if (WechatMenuKeyConstant.ONLINE.equals(wxMessage.getEventKey())){
            msgReplyService.replyImage(wxMessage.getFromUser(), WechatMediaIdConstant.CUSTOMER_SERVICE_WECHAT_IMG);
        }

        log.info("微信公众号点击菜单事件：完成");
        return null;
    }


}
