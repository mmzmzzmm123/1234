package com.muster.web.controller.common;


import com.muster.common.constant.WxConsts;
import com.muster.common.core.controller.BaseController;
import com.muster.common.core.domain.AjaxResult;
import com.muster.common.utils.StringUtils;
import com.muster.common.utils.file.MimeTypeUtils;
import com.muster.common.utils.uuid.IdUtils;
import com.muster.web.controller.muster.weixin.QrCodeUtils;
import com.muster.web.controller.muster.weixin.WeChatService;
import com.muster.web.controller.muster.weixin.WeiXinCommonUtil;
import com.muster.web.controller.muster.weixin.WeiXinOauth2Token;
import com.muster.web.controller.muster.weixin.WeiXinUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName WeiXinController
 * @Description TOOD
 * @Author guoconglin
 * @DATE 2020/4/10 14:29
 * @Version 1.0
 **/

@RestController
@Api(value = "微信相关操作管理", description = "微信相关操作管理", tags = {"微信相关操作管理"})
@RequestMapping("/system/wechat")
public class WeChatController extends BaseController {

    @Value("${wx.open.appid}")
    private String wxMapAppid;

    @Value("${wx.open.appsecret}")
    private String wxMapAppsecret;

    @Value("${wx.open.callback_url}")
    private String wxMapCallbackUrl;

    @Autowired
    private WeChatService weChatService;

    @GetMapping("/callback")
    @ApiOperation("微信回调接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "redirectUri", value = "跳转url", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "微信授权的Code", dataType = "String")
    })
    public void callback(HttpServletResponse response, HttpServletRequest request,
                         @RequestParam("redirectUri") String redirectUri,
                         @RequestParam(value = "code", required = false)  String code,
                         @RequestParam("state") String state) throws IOException {
        String redirectUrl = String.format("%s?code=%s&state=%s", redirectUri , code, state);
        response.sendRedirect(redirectUrl);
    }

    /**
     * <1>用户访问微信网页版，此时微信服务器会为其生成一个全局唯一的UUID。然后这个UUID就 回调路径wxMapCallbackUrl的后面，
     * 此时该操作并没有和用户有交互，所以该ID仅仅是个唯一字符串而已，系统并不知道该ID会和哪个用户相绑定。
      如果此时你不断地刷新，你会发现每次的ID都会发生过变化。感兴趣的可以自己手动来抓包，这里就不做示范了。
     注意：此时服务器和你网页还会建立一个长连接（websocket），为了节约系统资源，如果一段时间不扫描，便会超时。返回状态为408。

     <2>用户扫描PC端的二维码，然后用户和服务创建一个长链接（websocket）判断当前状态，并且。这个步骤的目的是为了获取起生成的全局唯一UUID，进行前端状态变化。

     <3>用户如果此时授权，则会像系统发送一条请求，并且将UUID和code一块发送过去，然后根据code获取微信用户信息，看系统是否存在该用户，不存在，则将微信用户信息保存，然后让用户取绑定账号
     <4> 如果用户输入的账号也不存在，则需要用户开通账号（开通账号方式分为自己注册和要求管理员开通），
      4.1 如果自己注册，则开始走注册流程，走注册流程的话，审核流程通过之后，然后重新扫码绑定。
      4.2 如果需要管理员开通账号，开通账号之后，首次扫码登录或者微信授权登录都需要重新绑定一次

     <5>系统受到这一步的目的是将UUIIDI和用户账号（或token）绑定在一起，因为二者都是唯一，便可以确定唯一的对应关系。
     处理完该关系后，系统会向PC端反馈消息，这个UUID对应的用户是A，然后网页便可请求加载A的微信信息和资料

     */

    @GetMapping("/wxQrMapCode")
    @ApiOperation("获取微信公众号二维码")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "width",value = "图片宽度",dataType = "Integer"),
            @ApiImplicitParam(name = "height",value = "图片高度",dataType = "Integer")
    })
    public AjaxResult wxQrMapCode(@RequestParam(value = "width", required = false, defaultValue = "100") int width,
                                  @RequestParam(value = "height", required = false, defaultValue = "100") int height) {
        AjaxResult ajax = AjaxResult.success();
        // 唯一标识
        String uuid = IdUtils.simpleUUID();
//        //将uuid带到回调地址上，判断当前用户是否使用了
        String authorizeUrl = WeiXinCommonUtil.getAuthorizeUrl(wxMapAppid, String.format(wxMapCallbackUrl,uuid), WxConsts.QrConnectScope.SNSAPI_LOGIN, null);
        String qrCodeBasePicture = QrCodeUtils.getQrCodeBasePicture(authorizeUrl, width, height, MimeTypeUtils.getExtension(MimeTypeUtils.IMAGE_PNG));
        ajax.put("img", qrCodeBasePicture);
        ajax.put("uuid",uuid);
        return ajax;
    }

    @PostMapping("/user-info")
    @ApiOperation("通过authorizeCode获取微信用户信息，并进行判断，让前端根据返回的参数去判断")
    @ApiImplicitParam(name = "authorizeCode",value = "微信授权authorizeCode",dataType = "String")
    public AjaxResult getWeChatCode(@RequestParam("authorizeCode") String authorizeCode,
                                    @RequestParam("state") String state,
                                    @RequestParam("appId") String appId,
                                    @RequestParam("bizId") String bizId){
        if(StringUtils.isNotNull(authorizeCode)){
            WeiXinOauth2Token oauth2AccessToken = WeiXinCommonUtil.getOauth2AccessToken(authorizeCode, wxMapAppid, wxMapAppsecret);
            if(StringUtils.isNotNull(oauth2AccessToken)){
                if(oauth2AccessToken.getErrcode()==0){
//                    String refreshToken = oauth2AccessToken.getRefreshToken();
                    String accessToken = oauth2AccessToken.getAccessToken();
                    String openId = oauth2AccessToken.getOpenId();
//                    //校验授权token是否有效，false则取刷新token
//                    boolean b = WeiXinCommonUtil.validateToken(refreshToken, wxMapAppid);
//                    if(!b){
//                        refreshToken = WeiXinCommonUtil.getRefreshToken(wxMapAppid, refreshToken);
//                    }

                    WeiXinUserInfo userInfo = WeiXinCommonUtil.getUserInfo(accessToken, openId);
                    boolean loginFlag = weChatService.login(userInfo, appId, bizId);
                    if (loginFlag) {

                        return AjaxResult.success(userInfo);
                    }


                    //先拿openId去库里查找微信信息标
//                    BWxOpenUser bWxOpenUser = ibWxOpenUserService.selectByOpenId(openId);
//                    // 如果没有则获取微信用户信息，插入数据库
//                    if(StringUtils.isNull(bWxOpenUser)){
//                        //获取微信用户信息
//                        WeiXinUserInfo userInfo = WeiXinCommonUtil.getUserInfo(refreshToken, openId);
//                        if(StringUtils.isNotNull(userInfo) && userInfo.getErrcode()==0){
//                           ibWxOpenUserService.saveWxOpenUser(userInfo);
//                        }
//                    }else {
//                        return AjaxResult.success(openId);
//                    }
                    //如果有则判断userId有没有，如果userId为0或者null，则返回让用户绑定账号。如果有，则使用userId查询sys_user标，将user_name和password查找到，使用loginService登录判断

                }

            }
            return AjaxResult.error("登录异常，请重试");

        }else {
            return AjaxResult.error("参数不合法,请传入授权码");
        }
    }

    @GetMapping("/pushToWeb/{uuid}")
    public AjaxResult pushToWeb(@PathVariable String uuid){
        //TODO 写逻辑
        return AjaxResult.success();
    }
}
