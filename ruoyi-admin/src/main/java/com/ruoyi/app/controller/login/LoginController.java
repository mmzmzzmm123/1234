package com.ruoyi.app.controller.login;

import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.login.service.ILoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "获取验证码", notes = "根据手机号获取验证码")
    @PostMapping("/code/get")
    @RepeatSubmit
    @RateLimiter
    public AjaxResult getVerificationCode(@RequestBody @Validated LoginDTO loginDTO) {
        return AjaxResult.success(loginService.getSmsCode(loginDTO));
    }

    @ApiOperation(value = "校验验证码并登录", notes = "校验验证码并登录")
    @PostMapping("/code/verify")
    @RepeatSubmit(interval = 3000)
    @RateLimiter
    public AjaxResult verify(@RequestBody @Validated LoginDTO loginDTO) {
        return loginService.verifyCode(loginDTO);
    }


}
