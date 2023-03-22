package com.ruoyi.app.controller.wechat;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.config.WxLoginConfig;
import com.ruoyi.common.constant.RespMessageConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.core.domain.vo.LoginVO;
import com.ruoyi.common.enums.LoginType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
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

    private static String APP_ID = SpringUtils.getBean(WxLoginConfig.class).getAppId();
    private static String APP_SECRET = SpringUtils.getBean(WxLoginConfig.class).getAppSecret();
    private static String PAGE_URL = SpringUtils.getBean(WxLoginConfig.class).getRedirectUri();

    @Autowired
    private IPsyUserService psyUserService;

    @Autowired
    private AppTokenService appTokenService;

    /**
     * 微信登录接口
     */
    @ApiOperation(value = "微信登录接口")
    @RequestMapping("wxLogin")
    public AjaxResult wxLogin() {
        //这里是回调的url
        String redirect_uri = PAGE_URL;
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
     * 微信授权成功的回调函数
     *
     * @param request
     * @throws IOException
     */
    @ApiOperation(value = "微信授权回调接口")
    @RequestMapping("/callBack")
    protected AjaxResult deGet(HttpServletRequest request) throws Exception {
        //获取回调地址中的code
        String code = request.getParameter("code");
        log.info("回调code为：{}", code);
        //拼接url
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APP_ID + "&secret="
                + APP_SECRET + "&code=" + code + "&grant_type=authorization_code";
        log.info("access_token请求url:{}", url);
        JSONObject jsonObject = AuthUtil.doGetJson(url);
        //1.获取微信用户的openid
        String openId = jsonObject.getString("openid");
        //2. 获取access_token
        String accessToken = jsonObject.getString("access_token");
//        Integer expiresIn = jsonObject.getInteger("expires_in");

        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId
                + "&lang=zh_CN";
        log.info("access_token_接口返回{}", infoUrl);
        //3.获取微信用户信息
        JSONObject userInfo = AuthUtil.doGetJson(infoUrl);
        log.info("微信用户信息：{}", userInfo);

        if (null != userInfo.getInteger("errcode")) {
            return AjaxResult.error(RespMessageConstants.ACCESS_TOKEN_EXPIRED);
        }

        //获取手机号登录用户
        LoginDTO loginUser = appTokenService.getLoginUser(request);

        LoginVO loginVO = null;
        //手机号已经登录过
        if (loginUser != null && StringUtils.isNotEmpty(loginUser.getPhone())) {
            //重新缓存用户信息
            loginUser.setWxOpenId(openId);
            PsyUser user = psyUserService.queryUserByAccount(loginUser.getPhone());
            loginVO = getLoginVO(openId, userInfo, loginUser, user);
        } else {
            loginVO = psyUserService.checkPsyUser(openId, userInfo);
            loginUser = new LoginDTO();
            loginUser.setWxOpenId(openId);
            loginUser.setAccount(openId);
            loginUser.setLoginType(LoginType.WX);
            loginUser.setUserId(loginVO.getUserId().toString());
            //创建token
            String token = appTokenService.createToken(loginUser, null);
            loginVO.setToken(token);
        }

        return AjaxResult.success(RespMessageConstants.APP_LOGIN_SUCCESS, loginVO);
    }

    /**
     * 手机登录之后才会涉及微信登录
     */
    private LoginVO getLoginVO(String openId, JSONObject userInfo, LoginDTO loginUser, PsyUser user) {
        String nickname = userInfo.getString("nickname");
        String headImgUrl = userInfo.getString("headimgurl");

        appTokenService.refreshToken(loginUser, null);

        psyUserService.updatePsyUser(PsyUser.builder().id(user.getId()).wxOpenid(openId).name(nickname).avatar(headImgUrl).build());

        LoginVO loginVO = LoginVO.builder().name(nickname).avatar(headImgUrl).phone(user.getPhone()).build();
        return loginVO;
    }


}
