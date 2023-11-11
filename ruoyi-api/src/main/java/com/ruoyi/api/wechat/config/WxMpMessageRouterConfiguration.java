package com.ruoyi.api.wechat.config;

import com.ruoyi.api.wechat.handler.*;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import static me.chanjar.weixin.common.api.WxConsts.EventType.*;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.EVENT;
import static me.chanjar.weixin.mp.constant.WxMpEventConstants.CustomerService.KF_CREATE_SESSION;

/**
 * @author LAM
 * @date 2023/10/15 22:56
 */
@Configuration
@RequiredArgsConstructor
public class WxMpMessageRouterConfiguration {

    private final WxMpService wxOfficialAccountService;
    private final KfSessionHandler kfSessionHandler;
    private final SubscribeHandler subscribeHandler;
    private final UnsubscribeHandler unsubscribeHandler;
    private final MenuHandler menuHandler;
    private final NullHandler nullHandler;
    private final ScanHandler scanHandler;
    private final MsgHandler msgHandler;

    /**
     * 微信公众号消息路由
     * */
    @Bean
    @Lazy
    public WxMpMessageRouter messageRouter() {
        WxMpMessageRouter newRouter = new WxMpMessageRouter(wxOfficialAccountService);
        // 接收客服会话管理事件
        newRouter.rule().async(false).msgType(EVENT).event(KF_CREATE_SESSION).handler(kfSessionHandler).end();
        // 自定义菜单事件
        newRouter.rule().async(false).msgType(EVENT).event(WxConsts.EventType.CLICK).handler(menuHandler).end();
        // 关注事件
        newRouter.rule().async(false).msgType(EVENT).event(SUBSCRIBE).handler(subscribeHandler).end();
        // 取消关注事件
        newRouter.rule().async(false).msgType(EVENT).event(UNSUBSCRIBE).handler(unsubscribeHandler).end();
        //扫描带参二维码事件
        newRouter.rule().async(false).msgType(EVENT).event(SCAN).handler(scanHandler).end();
        //其他事件
        newRouter.rule().async(false).msgType(EVENT).handler(nullHandler).end();
        // 默认
        newRouter.rule().async(false).handler(msgHandler).end();
        return newRouter;
    }
}
