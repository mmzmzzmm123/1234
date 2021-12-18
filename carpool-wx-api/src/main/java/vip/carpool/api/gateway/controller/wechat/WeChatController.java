package vip.carpool.api.gateway.controller.wechat;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vip.carpool.api.gateway.common.api.ApiResult;
import vip.carpool.api.gateway.common.exception.BadRequestException;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/wechat")
@Api(value = "微信公众号模块", tags = "微信公众号模块", description = "微信公众号模块")
public class WeChatController {

    @Resource
    private WxMpService wxMpService;

    /**
     * 小程序，颁发token，暂时模拟openid
     *
     * @return token字符串
     */
    @PostMapping("/oauth/accessToken")
    @ApiOperation(value = "获取公众号accessToken", notes = "获取公众号accessToken")
    public ApiResult login(@ApiParam(name = "code必传", required = true) @RequestParam("code") String code) {
        log.info("客户绑定颁发token入参：{}", code);
        try {
            log.info("正在获取公众号受权……");
            // 获取accessToken
            WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(code);
            // 获取用户信息
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(accessToken, null);
            log.info("正在获取公众号受权结束：unionId为：{}", wxMpUser.getUnionId());
            return ApiResult.ok(wxMpUser);
        } catch (WxErrorException e) {
            log.error("获取公众号受权失败 accessToken: code={}, error={}", code, e.getMessage());
            throw new BadRequestException("获取公众号受权失败");
        }
    }

    /**
     * 小程序，颁发token，暂时模拟openid
     *
     * @return token字符串
     */
    @PostMapping("/oauth/getWxMpUser")
    @ApiOperation(value = "获取公众号用户信息", notes = "获取公众号用户信息")
    public ApiResult getWxMpUser(@ApiParam(name = "accessToken必传", required = true) @RequestParam("accessToken") String accessToken,
                                 @ApiParam(name = "openId必传", required = true) @RequestParam("openId") String openId) {
        log.info("客户绑定颁发token入参：{}", accessToken);
        try {
            log.info("正在获取公众号用户信息……");
            // 获取accessToken
            WxMpOAuth2AccessToken oAuth2AccessToken = new WxMpOAuth2AccessToken();
            oAuth2AccessToken.setAccessToken(accessToken);
            oAuth2AccessToken.setOpenId(openId);
            // 获取用户信息
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(oAuth2AccessToken, null);
            log.info("正在获取公众号受权结束：unionId为：{}", wxMpUser.getUnionId());
            return ApiResult.ok(wxMpUser);
        } catch (WxErrorException e) {
            log.error("获取公众号受权失败 accessToken: {}, openId={}, error={}", accessToken, openId, e.getMessage());
            throw new BadRequestException("获取公众号受权失败");
        }
    }

    @PostMapping("/getShareInfo")
    @ApiOperation(value = "公众号分享", notes = "公众号分享功能")
    public ApiResult share(@ApiParam(name = "需要分享的url，必传", required = true) @RequestParam("url") String url) {
        try {
            WxJsapiSignature wxJsapiSignature = wxMpService.createJsapiSignature(url);
            return ApiResult.ok(wxJsapiSignature);
        } catch (WxErrorException e) {
            log.error("公众号分享: url={}, error={}", url, e.getMessage());
            throw new BadRequestException("公众号分享失败");
        }
    }
}
