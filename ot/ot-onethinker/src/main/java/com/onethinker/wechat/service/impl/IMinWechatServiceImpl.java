package com.onethinker.wechat.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;
import com.onethinker.wechat.service.IMinWechatService;
import com.onethinker.common.enums.SysConfigKeyEnum;
import com.onethinker.system.service.ISysConfigService;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.log4j.Log4j2;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author : yangyouqi
 * @date : 2024/1/12 11:38
 */
@Service
@Log4j2
public class IMinWechatServiceImpl implements IMinWechatService {
    @Resource
    private ISysConfigService sysConfigService;

    private WxMaService wxMaService;

    @Override
    public WxMaJscode2SessionResult getSessionInfo(String code) {
        Assert.isTrue(!StringUtils.isEmpty(code), "code不能为空");
        try {
            WxMaJscode2SessionResult session = this.getWxMaService().getUserService().getSessionInfo(code);
            log.info(session.getSessionKey());
            log.info(session.getOpenid());
            return session;
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        } finally {
            WxMaConfigHolder.remove();//清理ThreadLocal
        }
    }

    @Override
    public String getIMinWechatOpenIdByCode(String code) {
        Assert.isTrue(!StringUtils.isEmpty(code), "code不能为空");
        try {
            WxMaJscode2SessionResult session = this.getWxMaService().getUserService().getSessionInfo(code);
            log.info(session.getSessionKey());
            log.info(session.getOpenid());
            return session.getOpenid();
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        } finally {
            WxMaConfigHolder.remove();//清理ThreadLocal
        }
    }

    @Override
    public WxMaUserInfo getUserInfo(String sessionKey, String encryptedData, String ivStr) {
        // 解密用户信息
        try {
            WxMaUserInfo userInfo = this.getWxMaService().getUserService().getUserInfo(sessionKey, encryptedData, ivStr);
            return userInfo;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        } finally {
            WxMaConfigHolder.remove();//清理ThreadLocal
        }
    }

    @Override
    public void sendSubscribeMsg(WxMaSubscribeMessage subscribeMessage) throws WxErrorException {
        Assert.isTrue(ObjectUtils.isNotEmpty(subscribeMessage), "参数请求为空");
        // 获取发送消息service
        WxMaMsgService msgService = this.getWxMaService().getMsgService();
        try {
            log.info("发送模版消息：" + JSONObject.toJSONString(subscribeMessage));
            msgService.sendSubscribeMsg(subscribeMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        } finally {
            WxMaConfigHolder.remove();//清理ThreadLocal
        }
    }

    private WxMaService getWxMaService() {
        if (wxMaService != null) {
            return wxMaService;
        }
        synchronized (IMinWechatServiceImpl.class) {
            if (wxMaService != null) {
                return wxMaService;
            }
            WxMaService maService = new WxMaServiceImpl();
            Map<String, WxMaConfig> multiConfigMap = Maps.newHashMap();
            WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
            config.setAppid(sysConfigService.selectConfigByKey(SysConfigKeyEnum.APPID));
            config.setSecret(sysConfigService.selectConfigByKey(SysConfigKeyEnum.APPSECRET));
            config.setToken("");
            config.setAesKey("");
            config.setMsgDataFormat("JSON");
            multiConfigMap.put(sysConfigService.selectConfigByKey(SysConfigKeyEnum.APPID), config);
            maService.setMultiConfigs(multiConfigMap);
            this.wxMaService = maService;
            return maService;
        }

    }
}
