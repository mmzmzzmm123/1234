package com.stdiet.custom.service.impl;

import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.WxXmlData;
import com.stdiet.custom.service.ISysWxService;
import com.stdiet.custom.utils.WechatMessageUtil;
import com.stdiet.custom.utils.WxTokenUtils;
import com.thoughtworks.xstream.XStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
@Transactional
public class SysWxServiceImpl implements ISysWxService {

    @Override
    public String wxCheckAuth(String signature, String timestamp, String nonce, String echostr) {
        return WxTokenUtils.checkSignature(signature, timestamp, nonce) ? echostr : null;
    }

    @Override
    public String autoResponse(HttpServletRequest request) {
        try {
            WxXmlData wxData = WxTokenUtils.resolveXmlData(request.getInputStream());

            WxXmlData resultXmlData = new WxXmlData();
            resultXmlData.setToUserName(wxData.getFromUserName());  //收到的消息是谁发来的再发给谁
            resultXmlData.setFromUserName(wxData.getToUserName());  //
            if (!StringUtils.isEmpty(wxData.getEvent())) {
                if (wxData.getEvent().equals(WechatMessageUtil.MESSAGE_EVENT_SUBSCRIBE)) {
                    resultXmlData.setMsgType("text");
                    resultXmlData.setCreateTime(System.currentTimeMillis());
                    resultXmlData.setContent("欢迎来到胜唐体控，这是一条测试用的关注信息");
                } else if (wxData.getEvent().equals(WechatMessageUtil.MESSAGE_EVENT_UNSUBSCRIBE)) {

                }
            } else if (wxData.getContent().equalsIgnoreCase("vip")) {
                resultXmlData.setMsgType("text");
                resultXmlData.setCreateTime(System.currentTimeMillis());
                resultXmlData.setContent("<a href=\"https://my.openwrite.cn/code/generate?blogId=18931-1576559666626-322\">点击该链接，获取博客解锁验证码</a>");
            } else {
                resultXmlData.setMsgType("text");
                resultXmlData.setCreateTime(System.currentTimeMillis());
                resultXmlData.setContent("公众号正在开发中。后期请多多关注！");
            }
            XStream xstream = new XStream();
            xstream.processAnnotations(WxXmlData.class);
            xstream.setClassLoader(WxXmlData.class.getClassLoader());
            return xstream.toXML(resultXmlData);  //XStream的方法，直接将对象转换成 xml数据
        } catch (IOException e) {
            return null;
        }
    }

}
