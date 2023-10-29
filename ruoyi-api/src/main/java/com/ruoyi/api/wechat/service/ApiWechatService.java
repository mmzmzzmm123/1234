package com.ruoyi.api.wechat.service;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.wechat.constant.WechatMediaIdConstant;
import com.ruoyi.api.wechat.constant.WechatMenuKeyConstant;
import com.ruoyi.api.wechat.model.dto.ApiWechatAuthGetDto;
import com.ruoyi.api.wechat.model.dto.ApiWechatPostDto;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Service;


/**
 * @author LAM
 * @date 2023/10/15 19:36
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiWechatService {

    private final WxMpService wxOfficialAccountService;
    private final WxMpMessageRouter messageRouter;

    /**
     * 微信GET验证
     *
     * @param dto 验证数据
     * @return 结果
     */
    public String authGet(ApiWechatAuthGetDto dto) {
        log.info("接收到来自微信服务器的认证消息：开始，参数：{}", dto);
        if (wxOfficialAccountService.checkSignature(dto.getTimestamp(), dto.getNonce(), dto.getSignature())) {
            log.info("接收到来自微信服务器的认证消息：完成");
            return dto.getEchostr();
        }
        log.info("接收到来自微信服务器的认证消息：失败");
        return Constants.LOGIN_FAIL;
    }

    /**
     * 微信POST请求
     *
     * @param dto 参数
     * @param requestBody 报文
     * @return 结果
     */
    public String post(ApiWechatPostDto dto, String requestBody) {
        log.info("微信POST请求：开始，参数：{},{}", dto, requestBody);
        if (StringUtils.isNotBlank(dto.getEncrypt_type())){
            // 由于微信基本配置选择了密文，所以直接开启密文解密业务
            WxMpXmlMessage wxMpXmlMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxOfficialAccountService.getWxMpConfigStorage(), dto.getTimestamp(), dto.getNonce(), dto.getMsg_signature());
            log.info("微信POST请求：描述，解密报文结果：{}", wxMpXmlMessage);
            WxMpXmlOutMessage wxMpXmlOutMessage = messageRouter.route(wxMpXmlMessage);
            if (ObjectUtil.isNotNull(wxMpXmlOutMessage)){
                log.info("微信POST请求：描述，存在返回消息，直接返回");
                return wxMpXmlOutMessage.toEncryptedXml(wxOfficialAccountService.getWxMpConfigStorage());
            }
        }else{
            log.info("测试号事件：忽视");
        }
        log.info("微信POST请求：完成");
        return Constants.SUCCESS_SMALL_LETTER;
    }
}
