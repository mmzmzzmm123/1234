package com.ruoyi.app.controller.login;

import com.ruoyi.common.constant.RespMessageConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.framework.web.service.AppTokenService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.login.service.ILoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @User hogan
 * @Time 2022/10/18 15:12
 * @e-mail hkcugwh@163.com
 **/
@RestController
@RequestMapping("/app/login")
@Api(value = "登录模块", tags = {"登录模块"})
public class LoginController {

    @Resource
    private ILoginService loginService;

    @Autowired
    private AppTokenService appTokenService;

    @ApiOperation(value = "获取验证码", notes = "根据手机号获取验证码")
    @PostMapping("/code/get")
    public AjaxResult getVerificationCode(@RequestBody @Validated LoginDTO loginDTO) {
        return AjaxResult.success(loginService.getSmsCode(loginDTO));
    }

    @ApiOperation(value = "校验验证码并登录", notes = "校验验证码并登录")
    @PostMapping("/code/verify")
    public AjaxResult verify(@RequestBody @Validated LoginDTO loginDTO) {

        if(loginService.verifyCode(loginDTO)){
            return AjaxResult.success(RespMessageConstants.APP_LOGIN_SUCCESS ,appTokenService.createToken(loginDTO));
        }
        return AjaxResult.error(RespMessageConstants.SMS_VERIFY_ERROR);
    }


}
