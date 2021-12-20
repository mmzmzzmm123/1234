package vip.carpool.api.gateway.controller.wechat;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vip.carpool.api.gateway.common.api.ApiResult;
import vip.carpool.api.gateway.common.exception.BadRequestException;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/wxmini")
@Api(value = "微信小程序模块", tags = "微信小程序模块", description = "微信小程序模块")
public class WeChatMiniController {

    @Resource
    private WxMaService wxMiniService;

    /**
     * 小程序，颁发token，暂时模拟openid
     *
     * @return token字符串
     */
    @PostMapping("/oauth/accessToken")
    @ApiOperation(value = "获取小程序accessToken", notes = "获取小程序accessToken")
    public ApiResult login(@ApiParam(name = "code必传", required = true) @RequestParam("code") String code,
                           @ApiParam(name = "encryptedData必传", required = true) @RequestParam("encryptedData") String encryptedData,
                           @ApiParam(name = "iv必传", required = true) @RequestParam("iv") String iv) {
        try {
            log.info("正在获取小程序受权……");
            WxMaJscode2SessionResult session = wxMiniService.getUserService().getSessionInfo(code);
            log.info("正在获取小程序受权：session为：{}", session.toString());
            // 先获取微信token
            String sessionKey = session.getSessionKey();
            String unionId = session.getUnionid();
            // 解密用户信息
            WxMaUserInfo userInfo = wxMiniService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
            if (StringUtils.isBlank(unionId)) {
                unionId = userInfo.getUnionId();
            } else {
                userInfo.setUnionId(unionId);
            }
            log.info("正在获取小程序受权结束：unionId为：{}，userInfo：{}", unionId, userInfo.toString());
            return ApiResult.ok(userInfo);
        } catch (WxErrorException e) {
            log.error("获取小程序受权失败 accessToken: code={}, error={}", code, e.getMessage());
            throw new BadRequestException("获取小程序受权失败");
        }
    }


    /**
     * 小程序，颁发token，暂时模拟openid
     *
     * @return token字符串
     */
    @PostMapping("/oauth/code2session")
    @ApiOperation(value = "通过Code获取小程序session", notes = "通过code获取小程序session")
    public ApiResult code2Session(@ApiParam(name = "code必传", required = true) @RequestParam("code") String code ) {
        try {
            log.info("正在获取小程序受权……");
            WxMaJscode2SessionResult session = wxMiniService.getUserService().getSessionInfo(code);
            log.info("正在获取小程序受权：session为：{}", session.toString());
            // 先获取微信token
            String sessionKey = session.getSessionKey();
            String unionId = session.getUnionid();
            String openId = session.getOpenid();
            log.info("正在获取小程序受权结束：unionId为：{}，sessionKey：{}，openId：{}", unionId, sessionKey,openId);
            return ApiResult.ok(session);
        } catch (WxErrorException e) {
            log.error("获取小程序受权失败 accessToken: code={}, error={}", code, e.getMessage());
            throw new BadRequestException("获取小程序受权失败:" + e.getMessage() );
        }
    }




}
