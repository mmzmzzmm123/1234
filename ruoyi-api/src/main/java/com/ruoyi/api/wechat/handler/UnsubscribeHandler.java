package com.ruoyi.api.wechat.handler;

import com.ruoyi.common.enums.SysYesNoEnums;
import com.ruoyi.user.mapper.UserOfficialAccountMapper;
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
 * @author LAM
 * @date 2023/10/15 23:04
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UnsubscribeHandler implements WxMpMessageHandler {

    private final UserOfficialAccountMapper userOfficialAccountMapper;


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager){
        log.info("微信公众号取消关注事件：开始，参数：{}，{}", wxMessage, context);
        userOfficialAccountMapper.updateIfSubscribeByOpenId(SysYesNoEnums.NO.getCode(), wxMessage.getFromUser());
        log.info("微信公众号取消关注事件：完成");
        return null;
    }
}
