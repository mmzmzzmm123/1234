package com.ruoyi.app.controller.wechat;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.constant.RespMessageConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.framework.web.service.AppTokenService;
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

    private static String APP_ID = "wx8604d98104f67e66";
    private static String APP_SECRET = "98dca366602a0806da390ed138e4a7ea";
    private static String PAGE_URL = "http://10.28.124.184:8080";

    @Autowired
    private IPsyUserService psyUserService;

    @Autowired
    private AppTokenService appTokenService;

    /**
     * Tea微信登录接口
     *
     * @throws IOException
     */
    @ApiOperation(value = "微信登录接口")
    @RequestMapping("wxLogin")
//    @Anonymous
    public AjaxResult wxLogin(HttpServletResponse response) throws IOException {
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
    protected AjaxResult deGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取回调地址中的code
        String code = request.getParameter("code");
        //拼接url
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APP_ID + "&secret="
                + APP_SECRET + "&code=" + code + "&grant_type=authorization_code";
        JSONObject jsonObject = AuthUtil.doGetJson(url);
        //1.获取微信用户的openid
        String openId = jsonObject.getString("openid");
        //2.获取获取access_token
        String access_token = jsonObject.getString("access_token");
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openId
                + "&lang=zh_CN";
        //3.获取微信用户信息
        JSONObject userInfo = AuthUtil.doGetJson(infoUrl);
        log.info("微信用户信息：{}", userInfo);
        //至此拿到了微信用户的所有信息,剩下的就是业务逻辑处理部分了
        //保存openid和access_token到session
        request.getSession().setAttribute("openid", openId);
        request.getSession().setAttribute("access_token", access_token);

        //缓存用户信息，返回token
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setWxOpenId(openId);
        String token = appTokenService.createToken(loginDTO);
        return psyUserService.checkPsyUser(openId ,token ,userInfo);
    }


}
