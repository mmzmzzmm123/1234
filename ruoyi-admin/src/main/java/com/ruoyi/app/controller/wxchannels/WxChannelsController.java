package com.ruoyi.app.controller.wxchannels;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.app.controller.wechat.utils.WechatPayV3Utils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.WxPayDTO;
import com.ruoyi.wechat.service.IWxpayService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@RestController
@RequestMapping("/app/wxChannels")
@Slf4j
@Api(value = "WxChannelsController" ,tags = {"微信视频号订单推送"})
public class WxChannelsController {

    @Autowired
    private IWxpayService wxpayService;

    @Autowired
    public WechatPayV3Utils wechatPayV3Utils;


    @ApiOperation(value = "视频号店铺订单推送")
    @PostMapping("order")
    public AjaxResult payCallBack(@RequestBody WxPayDTO wxPayDTO , HttpServletRequest request){
      
        return null;
    }
}

