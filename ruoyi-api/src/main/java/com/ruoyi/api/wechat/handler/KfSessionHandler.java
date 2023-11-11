package com.ruoyi.api.wechat.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class KfSessionHandler implements WxMpMessageHandler {

    /**
     * 接收客服会话管理事件
     */
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
        log.info("接收客服会话管理事件：开始，参数：{}，{}", wxMessage, context);

        log.info("接收客服会话管理事件：完成");
        return null;
    }

}
