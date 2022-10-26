package com.ruoyi.app.controller.wechat;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.core.domain.dto.WxPayDTO;
import com.ruoyi.framework.web.service.AppTokenService;
import com.ruoyi.wechat.service.IWxpayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @User hogan
 * @Time 2022/10/20 19:40
 * @e-mail hkcugwh@163.com
 **/
@RestController
@RequestMapping("/app/wxPay")
@Slf4j
@Api(value = "WxPayController" ,tags = {"微信支付api"})
public class WxPayController {

    @Autowired
    private IWxpayService wxpayService;

    /**
     * 微信支付
     */
    @ApiOperation(value = "微信支付")
    @PostMapping("pay")
    public AjaxResult pay(@RequestBody WxPayDTO wxPayDTO , HttpServletRequest request){
        LoginDTO loginUser = AppTokenService.getInstance().getLoginUser(request);
        wxpayService.pay(wxPayDTO ,loginUser);

        return AjaxResult.success("");
    }


}
