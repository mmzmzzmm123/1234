package com.ruoyi.office.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Api(tags = "微信公众号")
@RequestMapping("/wx")
@RestController
@Slf4j
public class WxController {
    @Autowired
    WxMpService wxMpService;

    @Autowired
    private WxMpConfigStorage wxMpConfigStorage;

    @PostConstruct
    public void testAutowire() {
        System.out.println(wxMpService);
        System.out.println(wxMpConfigStorage);
    }

    @GetMapping("/message")
    public String configAccess(String signature,String timestamp,String nonce,String echostr) {
        // 校验签名
        if (wxMpService.checkSignature(timestamp, nonce, signature)){
            // 校验成功原样返回echostr
            return echostr;
        }
        // 校验失败
        return null;
    }

}