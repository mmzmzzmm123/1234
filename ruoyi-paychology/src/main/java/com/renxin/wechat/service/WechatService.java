package com.renxin.wechat.service;

import com.renxin.wechat.vo.TemplateMessageVo;

import java.util.Map;

public interface  WechatService {

    String getPublicAccessToken();

    String getPublicTicket(String token);

    Map<String, String> makePublicSignStr(String ticket, String url);

    Boolean sendPublicMsg(TemplateMessageVo msg);

}
