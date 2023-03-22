package com.ruoyi.app.controller.wechat;

import com.ruoyi.common.constant.RespMessageConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.framework.web.service.AppTokenService;
import com.ruoyi.psychology.domain.PsyUser;
import com.ruoyi.psychology.service.IPsyUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户Controller
 * 
 * @author ruoyi
 * @date 2022-08-26
 */
@RestController
@RequestMapping("/app/user")
@Api(value = "AppPsyUserController" ,tags = {"app用户操作api"})
public class AppPsyUserController extends BaseController
{
    @Autowired
    private IPsyUserService psyUserService;

    @Autowired
    private AppTokenService appTokenService;

    /**
     * 微信用户绑定手机号
     */
    @ApiOperation("微信用户绑定手机号")
    @PostMapping("/bindPhone")
    public AjaxResult list(@RequestBody PsyUser psyUser , HttpServletRequest request)
    {
        LoginDTO loginUser = appTokenService.getLoginUser(request);
        loginUser.setPhone(psyUser.getPhone());
        psyUserService.bindPhone(loginUser);
        appTokenService.refreshToken(loginUser ,null);
        return AjaxResult.success(RespMessageConstants.OPERATION_SUCCESS);
    }
}
