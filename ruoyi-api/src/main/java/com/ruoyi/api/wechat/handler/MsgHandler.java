package com.ruoyi.api.wechat.handler;


import com.ruoyi.api.wechat.constant.WechatMediaIdConstant;
import com.ruoyi.api.wechat.constant.WechatMenuKeyConstant;
import com.ruoyi.api.wechat.constant.WechatMsgKeywordsConstant;
import com.ruoyi.api.wechat.service.ApiWechatService;
import com.ruoyi.common.weixin.properties.WxProperties;
import com.ruoyi.common.weixin.service.WechatMsgReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.util.WxMpConfigStorageHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Binary Wang
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MsgHandler implements WxMpMessageHandler {

    private final WxProperties wxProperties;
    private final WechatMsgReplyService wechatMsgReplyService;
    private final WxMpService wxOfficialAccountService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
        log.info("微信公众号消息事件：开始，参数：{}，{}", wxMessage, context);
        String fromUser = wxMessage.getFromUser();
        String textContent = wxMessage.getContent();
        // 构建菜单指令
        if (WechatMsgKeywordsConstant.BUILD_MENU.equals(textContent)) {
            Boolean buildMenuResult = buildMenu();
            wechatMsgReplyService.replyText(fromUser, "构建菜单结果：" + (buildMenuResult ? "成功" : "失败"));
            return null;
        }
        log.info("微信公众号消息事件：完成");
        return null;

    }


    /**
     * 处理构建菜单指令业务
     *
     * @return 结果
     * */
    private Boolean buildMenu(){
        log.info("处理构建菜单指令业务：开始");
        WxMenu wxMenu = new WxMenu();
        List<WxMenuButton> wxMenuButtonList = new ArrayList<>();
        // 客服微信
        WxMenuButton customerServiceBut = new WxMenuButton();
        customerServiceBut.setName("在线客服");
        customerServiceBut.setType(WxConsts.MenuButtonType.CLICK);
        customerServiceBut.setKey(WechatMenuKeyConstant.ONLINE);
        customerServiceBut.setMediaId(WechatMediaIdConstant.CUSTOMER_SERVICE_WECHAT_IMG);
        wxMenuButtonList.add(customerServiceBut);
        // 前往小程序首页
        WxMenuButton selectStaffBut = new WxMenuButton();
        selectStaffBut.setName("选人下单");
        selectStaffBut.setType(WxConsts.MenuButtonType.MINIPROGRAM);
        selectStaffBut.setKey(WechatMenuKeyConstant.SELECT_STAFF);
        selectStaffBut.setAppId(wxProperties.getMiNiApplet().getAppId());
        selectStaffBut.setPagePath("pages/select/index");
        wxMenuButtonList.add(selectStaffBut);
        // 前往小程序我的页面
        WxMenuButton joinUsBut = new WxMenuButton();
        joinUsBut.setName("加入我们");
        joinUsBut.setType(WxConsts.MenuButtonType.MINIPROGRAM);
        joinUsBut.setKey(WechatMenuKeyConstant.JOIN_US);
        joinUsBut.setAppId(wxProperties.getMiNiApplet().getAppId());
        joinUsBut.setPagePath("pages/me/index");
        wxMenuButtonList.add(joinUsBut);
        try {
            wxMenu.setButtons(wxMenuButtonList);
            wxOfficialAccountService.getMenuService().menuCreate(wxMenu);
        }catch (Exception e){
            log.warn("处理构建菜单指令业务：异常，信息：{}", e.getMessage());
            return Boolean.FALSE;
        }
        log.info("处理构建菜单指令业务：完成");
        return Boolean.TRUE;
    }

}
