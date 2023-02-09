package com.ruoyi.app.controller.wechat;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.constant.RespMessageConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.core.domain.vo.LoginVO;
import com.ruoyi.framework.web.service.AppTokenService;
import com.ruoyi.psychology.domain.PsyUser;
import com.ruoyi.psychology.service.IPsyUserService;
import com.ruoyi.wechat.utils.AuthUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @User hogan
 * @Time 2022/10/20 19:40
 * @e-mail hkcugwh@163.com
 **/
@RestController
@RequestMapping("/app/user")
@Slf4j
public class WxAuthorizeController {

    private static String APP_ID = "wx81f97b38440a3b86";
    private static String APP_SECRET = "f259b9c0951de86bcfda3e482e7b3c45";
    private static String PAGE_URL = "http://127.0.0.1:8089";

    @Autowired
    private IPsyUserService psyUserService;

    @Autowired
    private AppTokenService appTokenService;

    /**
     * Tea微信登录接口
     */
    @ApiOperation(value = "微信登录接口")
    @RequestMapping("wxLogin")
//    @Anonymous
    public AjaxResult wxLogin(){
        //这里是回调的url
        String redirect_uri = PAGE_URL + "/pages/user/index";
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=APPID" +
                "&redirect_uri=REDIRECT_URI" +
                "&response_type=code" +
                "&scope=SCOPE" +
                "&state=STATE#wechat_redirect";
        url = url.replace("APPID", APP_ID).replace("REDIRECT_URI", redirect_uri).replace("SCOPE", "snsapi_userinfo");
        log.info("wxLogin_url:{}", url);
        return AjaxResult.success(RespMessageConstants.OPERATION_SUCCESS, url);
    }

    /**
     * Tea微信授权成功的回调函数
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "微信授权回调接口")
    @RequestMapping("/callBack")
    protected AjaxResult deGet(HttpServletRequest request) throws Exception {
        //获取回调地址中的code
        String code = request.getParameter("code");
        //拼接url
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APP_ID + "&secret="
                + APP_SECRET + "&code=" + code + "&grant_type=authorization_code";
        JSONObject jsonObject = AuthUtil.doGetJson(url);
        //1.获取微信用户的openid
        String openId = jsonObject.getString("openid");
        //2.获取access_token
        String access_token = jsonObject.getString("access_token");
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openId
                + "&lang=zh_CN";
        //3.获取微信用户信息
        JSONObject userInfo = AuthUtil.doGetJson(infoUrl);
        log.info("微信用户信息：{}", userInfo);

        //获取手机号登录用户
        LoginDTO loginUser = appTokenService.getLoginUser(request);
        PsyUser user = psyUserService.queryUserByAccount(loginUser.getPhone());

        //重新缓存用户信息
        loginUser.setWxOpenId(openId);
        appTokenService.refreshToken(loginUser);

        String nickname = userInfo.getString("nickname");
        String headImgUrl = userInfo.getString("headimgurl");

        //手机登录之后才会涉及微信登录
        psyUserService.updatePsyUser(PsyUser.builder().id(user.getId()).wxOpenid(openId).name(nickname).avatar(headImgUrl).build());

        LoginVO loginVO = LoginVO.builder().name(nickname).avatar(headImgUrl).phone(user.getPhone()).build();

        return AjaxResult.success(RespMessageConstants.APP_LOGIN_SUCCESS , loginVO);
    }


}
