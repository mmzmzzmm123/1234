package com.ruoyi.common.weixin;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.api.impl.WxMaUserServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author LAM
 * @date 2022/5/16 15:09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WxService {

    private final WxMaService wxMiniAppletService;

    /**
     * 使用微信小程序前端传入code，换取sessionInfo
     *
     * @param code
     * @return 结果
     * */
    public WxMaJscode2SessionResult getWxMiniSessionInfo(String code){
        log.info("请求微信登录：开始，参数：{}", code);
        WxMaJscode2SessionResult wxMaJscode2SessionResult = null;
        try {
            wxMaJscode2SessionResult = wxMiniAppletService.getUserService().getSessionInfo(code);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("请求微信登录：异常，信息：{}", e.getMessage());
        }
        log.info("请求微信登录：完成，返回数据：{}", wxMaJscode2SessionResult);
        return wxMaJscode2SessionResult;
    }

    /**
     * 解密获取微信用户手机号码
     * @param sessionKey
     * @param iv
     * @param encryptedData
     * @return 结果
     * */
    public String decodePhoneNumber(String sessionKey, String iv, String encryptedData) {
        log.info("解密获取微信用户手机号码：开始，参数：sessionKey：{}，iv：{}，encryptedData：{}", sessionKey, iv, encryptedData);
        WxMaPhoneNumberInfo wxMaPhoneNumberInfo = null;
        try {
            WxMaUserService wxMaUserService = new WxMaUserServiceImpl(wxMiniAppletService);
            wxMaPhoneNumberInfo = wxMaUserService.getPhoneNoInfo(sessionKey, encryptedData, iv);
        }catch (Exception e){
            e.printStackTrace();
            log.warn("解密获取微信用户手机号码：异常，信息：{}", e.getMessage());
        }
        log.info("解密获取微信用户手机号码：完成，返回数据：{}", wxMaPhoneNumberInfo.getPhoneNumber());
        return wxMaPhoneNumberInfo.getPhoneNumber();
    }
}
