package com.ruoyi.common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson2.JSONObject;

import com.ruoyi.common.utils.http.HttpUtils;

@Component
public class WeixinUtil {

    private static final Logger LOG = LoggerFactory.getLogger(WeixinUtil.class);

    public static String APPID;

    public static String APPSECRET;

    public WeixinUtil() {
    }

    public WeixinUtil(String appId, String appsecret) {
        APPID = appId;
        APPSECRET = appsecret;
    }

    /**
     * 通过code获取到用户的openId
     * @param code
     * @return
     */
    public String code2Session(String code) {
        LOG.info("app_id:" + APPID + "app_secret:" + APPSECRET);
        String code2SessionUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="+ APPID +"&secret="+APPSECRET+"&js_code="+code+"&grant_type=authorization_code";

        LOG.info("request url:" + code2SessionUrl);

        String result = HttpUtils.sendGet(code2SessionUrl);

        LOG.info("request data:" + result);

        String openId = "";

        if (ObjectUtils.isEmpty(result)) {
            throw  new NullPointerException("获取openId失败");
        }

        // 可以获取到数据啦
        try{
            openId = JSONObject.parseObject(result).getString("openid");
        }catch (Exception e) {
            throw new NullPointerException("获取openId失败");
        }
        if (ObjectUtils.isEmpty(openId)) {
            throw new NullPointerException("获取openId失败");
        }
        return openId;
    }
}
