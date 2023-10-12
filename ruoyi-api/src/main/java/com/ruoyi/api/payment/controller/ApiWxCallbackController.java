package com.ruoyi.api.payment.controller;

import com.ruoyi.api.payment.service.ApiWxCallbackService;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.weixin.utils.WxUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LAM
 * @date 2023/10/6 0:09
 */
@Anonymous
@Api(tags = "api-微信回调相关接口")
@RestController
@RequestMapping("/api/wxCallback")
@RequiredArgsConstructor
public class ApiWxCallbackController {

    private final ApiWxCallbackService service;

    @ApiOperation("支付成功回调")
    @PostMapping(value = "/payCallback")
    @ResponseBody
    public void payCallback(HttpServletRequest request, HttpServletResponse response){
        try {
            String result = service.payCallback(request);
            if (Constants.SUCCESS_SMALL_LETTER.equals(result)){
                response.getWriter().write(WxUtils.setXml("SUCCESS", "OK"));
            }else{
                throw new ServiceException("支付成功回调业务处理失败");
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("支付成功回调业务处理失败");
        }
    }
    @ApiOperation("退款成功回调")
    @PostMapping(value = "/refundCallback")
    @ResponseBody
    public void refundCallback(HttpServletRequest request, HttpServletResponse response){
        try{
            service.refundCallback(request);
            response.getWriter().write(WxUtils.setXml("SUCCESS", "OK"));
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("退款成功回调业务处理失败");
        }
    }
}
