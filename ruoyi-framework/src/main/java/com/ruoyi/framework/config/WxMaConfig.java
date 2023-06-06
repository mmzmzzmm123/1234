package com.ruoyi.framework.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.ruoyi.framework.config.properties.WxMaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(WxMaProperties.class)
public class WxMaConfig {
    public static final String DING_CANG = "dingcang";
    private final WxMaProperties properties;
    private static final Map<String, WxMaService> maServices = new HashMap<>();

    @Autowired
    public WxMaConfig(WxMaProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        List<WxMaProperties.Config> configs = this.properties.getConfigs();
        if (configs == null) {
            return;
        }
        for (WxMaProperties.Config config :
                configs) {
            WxMaDefaultConfigImpl wxConfig = new WxMaDefaultConfigImpl();
            wxConfig.setAppid(config.getAppid());
            wxConfig.setSecret(config.getSecret());
            wxConfig.setToken(config.getToken());
            wxConfig.setAesKey(config.getAesKey());
            wxConfig.setMsgDataFormat(config.getMsgDataFormat());

            WxMaService service = new WxMaServiceImpl();
            service.setWxMaConfig(wxConfig);
            maServices.put(config.getName(), service);
        }
    }

    public static WxMaService getMaServiceByName(String name) {
        WxMaService wxService = maServices.get(name);
        if (wxService == null) {
            throw new IllegalArgumentException(String.format("未找到对应name=[%s]的微信小程序配置，请核实！", name));
        }
        return wxService;
    }

    public static WxMaService getMaServiceById(String appid) {
        for (WxMaService service : maServices.values()) {
            if (service.getWxMaConfig().getAppid().equals(appid)) {
                return service;
            }
        }
        throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的微信小程序配置，请核实！", appid));
    }
}
