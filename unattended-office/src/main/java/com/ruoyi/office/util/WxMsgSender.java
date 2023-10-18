package com.ruoyi.office.util;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.office.domain.TRoom;
import com.ruoyi.office.domain.TRoomOrder;
import com.ruoyi.office.domain.TStore;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

public class WxMsgSender {

    private static final Logger log = LoggerFactory.getLogger(WxMsgSender.class);

    @Resource
    WxMpService wxMpService;

    public void sendOrderStartMsg(String mpOpenId, String tStore, String tRoom, TRoomOrder tRoomOrder) {
        try {

            List<WxMpTemplateData> data = Arrays.asList(
                    new WxMpTemplateData("thing2", tStore),
                    new WxMpTemplateData("thing3", tRoom),
                    new WxMpTemplateData("character_string6", tRoomOrder.getOrderNo().toString()),
                    new WxMpTemplateData("time4", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", tRoomOrder.getEndTime())),
                    new WxMpTemplateData("time8", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", tRoomOrder.getStartTime()))
            );

            wxMpService.getAccessToken();
            WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                    .toUser(mpOpenId)//要推送的用户openid
                    .data(data) //数据
                    .templateId("ZGF6vCmr v9DobuDcB NRsC3Encd1gQNJ5R8JIFApPk")//模版id
//                    .url("http://www.baidu.com") // 点击详情跳转地址
                    .build();

            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);

        } catch (Exception e) {
            log.error("Failed to send message: " + e.getMessage());
        }
    }

}
