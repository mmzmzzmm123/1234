package com.ruoyi.common.weixin.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.ruoyi.common.weixin.properties.WxPayInfoProperties;
import com.ruoyi.common.weixin.properties.WxProperties;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @author LAM
 * @date 2023/9/19 17:19
 */
@Configuration
@RequiredArgsConstructor
public class WxConfig {

    private final WxProperties wxProperties;
    private final WxPayInfoProperties wxPayInfoProperties;

    /**
     * 微信小程序参数构建
     */
    @Bean
    @Lazy
    public WxMaService wxMiniAppletService() {
        WxMaService service = new WxMaServiceImpl();
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(wxProperties.getMiNiApplet().getAppId());
        config.setSecret(wxProperties.getMiNiApplet().getAppSecret());
        service.setWxMaConfig(config);
        return service;
    }

    /**
     * 微信公众号参数构建
     * */
    @Bean
    @Lazy
    public WxMpService wxOfficialAccountService() {
        WxMpServiceImpl wxMpService = new WxMpServiceImpl();
        WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
        config.setAppId(wxProperties.getOfficialAccount().getAppId());
        config.setSecret(wxProperties.getOfficialAccount().getAppSecret());
        config.setToken(wxProperties.getOfficialAccount().getToken());
        config.setAesKey(wxProperties.getOfficialAccount().getEncodingAesKey());
        wxMpService.setWxMpConfigStorage(config);
        return wxMpService;
    }

    /**
     * 微信小程序支付参数构建
     */
    @Bean
    @Lazy
    public WxPayServiceImpl wxPayService() {
        WxPayConfig wxPayConfig = new WxPayConfig();
        WxProperties.MiNiApplet miNiApplet = wxProperties.getMiNiApplet();
        wxPayConfig.setMchId(wxPayInfoProperties.getMchId());
        wxPayConfig.setMchKey(wxPayInfoProperties.getMchKey());
        wxPayConfig.setPrivateKeyPath(wxPayInfoProperties.getAppKeyPath());
        wxPayConfig.setPrivateCertPath(wxPayInfoProperties.getAppCertPath());
        wxPayConfig.setAppId(miNiApplet.getAppId());
        wxPayConfig.setNotifyUrl(wxPayInfoProperties.getNotifyUrl());
        wxPayConfig.setKeyPath(wxPayInfoProperties.getKeyPath());
        wxPayConfig.setSignType(wxPayInfoProperties.getSignType());
        wxPayConfig.setTradeType("JSAPI");
        wxPayConfig.setApiV3Key(wxPayInfoProperties.getMchKeyV3());
        WxPayServiceImpl wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(wxPayConfig);
        return wxPayService;
    }

}
