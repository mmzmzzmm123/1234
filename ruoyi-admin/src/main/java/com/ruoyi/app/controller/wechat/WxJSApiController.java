package com.ruoyi.app.controller.wechat;

// 微信js-sdk相关接口

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.config.WxLoginConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/app/wxJS")
@Slf4j
public class WxJSApiController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static String APP_ID = SpringUtils.getBean(WxLoginConfig.class).getAppId();
    private static String APP_SECRET = SpringUtils.getBean(WxLoginConfig.class).getAppSecret();
    private static Map<String, String> result = new HashMap<>();

    private static final String KEY_WxJS_TOKEN = "KEY_WxJS_TOKEN";
    private static final String KEY_WxJS_TICKET = "KEY_WxJS_TICKET";
    private static final int EXPIRE_TIME = 7200;
    @Autowired
    private RedisCache redisCache;

    @PostMapping("config")
    public AjaxResult getWXJSConfig(@RequestBody Map<String, Object> map) {
        String accessToken = getAccessToken();
        if (accessToken == null) {
            return AjaxResult.error("微信JS获取token失败");
        }

        String ticket = getTicket(accessToken);
        if (ticket == null) {
            return AjaxResult.error("微信JS获取ticket失败");
        }

        if (!map.containsKey("url")) {
            return AjaxResult.error("请配置调用JS接口页面的完整URL");
        }
        String url = (String) map.get("url");
        String signature = makeSignStr(ticket, url);
        if (signature == null) {
            return AjaxResult.error("微信JS签名失败");
        }
        result.put("appId", APP_ID);
        result.put("signature", signature);

        return AjaxResult.success(result);
    }

    private String getAccessToken() {
        String token = null;

        // 从Redis中获取缓存数据
        boolean isTokenExist = redisCache.hasKey(KEY_WxJS_TOKEN);
        if (isTokenExist) {
            token = redisCache.getCacheObject(KEY_WxJS_TOKEN);
            logger.info("redis get token: {}", token);
            return token;
        }


        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        HttpEntity resEntity = null;
        String result = null;
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        url = url.replace("APPID", APP_ID).replace("APPSECRET", APP_SECRET);
        try {
            httpGet = new HttpGet(url);
            response = httpClient.execute(httpGet);
            resEntity = response.getEntity();
            result = EntityUtils.toString(resEntity, Consts.UTF_8);
            EntityUtils.consume(resEntity);

            System.out.println("result:" + result);
            JSONObject res = JSONObject.parseObject(result);
            if (res.containsKey("access_token")) {
                token = res.getString("access_token");
                logger.info("redis set token: {}", token);
                redisCache.setCacheObject(KEY_WxJS_TOKEN, token, EXPIRE_TIME, TimeUnit.SECONDS);
                return res.getString("access_token");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private String getTicket(String token) {
        String ticket = null;

        // 从Redis中获取缓存数据
        boolean isTicketExist = redisCache.hasKey(KEY_WxJS_TICKET);
        if (isTicketExist) {
            ticket = redisCache.getCacheObject(KEY_WxJS_TICKET);
            logger.info("redis get ticket: {}", ticket);
            return ticket;
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        HttpEntity resEntity = null;
        String result = null;
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
        url = url.replace("ACCESS_TOKEN", token);
        try {
            httpGet = new HttpGet(url);
            response = httpClient.execute(httpGet);
            resEntity = response.getEntity();
            result = EntityUtils.toString(resEntity, Consts.UTF_8);
            EntityUtils.consume(resEntity);

            System.out.println("result:" + result);
            JSONObject res = JSONObject.parseObject(result);
            if (res.containsKey("ticket")) {
                ticket = res.getString("ticket");
                logger.info("redis set ticket: {}", ticket);
                redisCache.setCacheObject(KEY_WxJS_TICKET, ticket, EXPIRE_TIME, TimeUnit.SECONDS);
                return res.getString("ticket");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String makeSignStr(String ticket, String url) {
        String noncestr = RandomStringUtils.randomAlphanumeric(16);
        result.put("nonceStr", noncestr);
        String  timestamp = new Date().getTime() + "";
        result.put("timestamp", timestamp);

        String signStr = "jsapi_ticket=TICKET&noncestr=NONCESTR&timestamp=TIMESTAMP&url=URL";
        signStr = signStr.replace("TICKET", ticket)
                .replace("NONCESTR", noncestr)
                .replace("TIMESTAMP", timestamp)
                .replace("URL", url);

        logger.info("makeSignStr加密前: " + signStr);
        String encodeSignStr = getSha1(signStr);
        logger.info("makeSignStr加密后: " + encodeSignStr);
        return encodeSignStr;
    }

    public static String getSha1(String str) {

        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }
}
