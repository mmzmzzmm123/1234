package com.ruoyi.common.weixin.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.ruoyi.common.weixin.properties.WxProperties;
import lombok.RequiredArgsConstructor;
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

    /**
     * 微信小程序参数构建
     * */
    @Bean
    @Lazy
    public WxMaService wxMiniAppletService(){
        WxMaService service = new WxMaServiceImpl();
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(wxProperties.getMiNiApplet().getAppId());
        config.setSecret(wxProperties.getMiNiApplet().getAppSecret());
        service.setWxMaConfig(config);
        return service;
    }

}
