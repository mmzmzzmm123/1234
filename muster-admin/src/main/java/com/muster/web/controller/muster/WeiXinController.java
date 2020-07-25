package com.muster.web.controller.muster;


import com.muster.common.constant.Constants;
import com.muster.common.constant.WxConsts;
import com.muster.common.core.domain.AjaxResult;
import com.muster.framework.web.service.SysLoginService;
import com.muster.web.controller.muster.weixin.WeChatService;
import com.muster.web.controller.muster.weixin.WeiXinUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName WeiXinController
 * @Description TOOD
 * @Author guoconglin
 * @DATE 2020/4/10 14:29
 * @Version 1.0
 **/
@Controller
@Api(value = "微信相关操作管理", description = "微信相关操作管理", tags = {"微信相关操作管理"})
@RequestMapping("/system/weixin")
public class WeiXinController {

    @Autowired
    private WeChatService weChatService;

    @Autowired
    private SysLoginService loginService;

    private String prefix = "weixin";

    @GetMapping("/redirect")
    public String user()
    {
        return prefix + "/redirect";
    }

    @PostMapping("/login")
    @ResponseBody
    @ApiOperation("子系统通过微信登录")
    @ApiImplicitParam(name = "子系统通过微信登录",value = "子系统通过微信登录", dataTypeClass = WeiXinUserInfo.class)
    public AjaxResult login(@RequestBody WeiXinUserInfo weiXinUserInfo) {

        boolean loginFlag = weChatService.login(weiXinUserInfo);

        if (loginFlag) {
            AjaxResult ajax = AjaxResult.success();
            String token = loginService.login(WxConsts.Account.SHRIO_USERNAME, WxConsts.Account.SHRIO_PASSWORD);

            ajax.put(Constants.TOKEN, token);
            return ajax;

        }
        return AjaxResult.error("微信扫码登录失败,请重试");
    }

}
