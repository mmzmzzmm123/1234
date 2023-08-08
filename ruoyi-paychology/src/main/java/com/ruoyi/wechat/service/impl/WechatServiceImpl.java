package com.ruoyi.wechat.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.config.WxLoginConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.RestTemplateUtil;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.wechat.domain.PsyWechatExceptions;
import com.ruoyi.wechat.service.IPsyWechatExceptionsService;
import com.ruoyi.wechat.service.WechatService;
import com.ruoyi.wechat.vo.TemplateMessageVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class WechatServiceImpl implements WechatService {
    private final String appId = SpringUtils.getBean(WxLoginConfig.class).getAppId();
    private final String appSecret = SpringUtils.getBean(WxLoginConfig.class).getAppSecret();

    @Resource
    private RedisCache redisCache;

    @Resource
    private RestTemplateUtil restTemplateUtil;

    @Resource
    private IPsyWechatExceptionsService psyWechatExceptionsService;

    /**
     * 微信公众号发送模板消息
     * @param msg 模板消息对象
     * @return 是否发送成功
     */
    @Override
    public Boolean sendPublicMsg(TemplateMessageVo msg) {
        JSONObject jsonData = JSONObject.parseObject(JSONObject.toJSONString(msg));

        if (StrUtil.isBlankIfStr(msg.getTouser())) {
            wxExceptionDispose(jsonData, JSONObject.parseObject("{\"errcode\":40001,\"errmsg\":\"openId为空\"}"), "微信公众号发送模板消息异常");
            return Boolean.TRUE;
        }

        String accessToken = getPublicAccessToken();
        String url = StrUtil.format(Constants.WECHAT_PUBLIC_SEND_TEMPLATE_URL, accessToken);
        String result = restTemplateUtil.postJsonData(url, jsonData);
        JSONObject data = JSONObject.parseObject(result);
        String err = "ok";
        if (ObjectUtil.isNull(data)) {
            err = "微信平台接口异常，没任何数据返回！";
        }
        if (data.containsKey("errcode") && !"0".equals(data.getString("errcode"))) {
            if (data.containsKey("errmsg")) {
                err = "微信公众号发送模板消息异常";
            }
        }

        if (!"ok".equals(err)) {
            wxExceptionDispose(jsonData, data, err);
        }

        return Boolean.TRUE;
    }
    // {"errcode":40164,"errmsg":"invalid ip 61.183.89.14 ipv6 ::ffff:61.183.89.14, not in whitelist rid: 64d1e675-2ca73ab3-2e56ca44"}

    @Override
    public String getPublicAccessToken() {
        String token = null;

        // 从Redis中获取缓存数据
        boolean isTokenExist = redisCache.hasKey(Constants.WECHAT_PUBLIC_KEY_WXJS_TOKEN);
        if (isTokenExist) {
            token = redisCache.getCacheObject(Constants.WECHAT_PUBLIC_KEY_WXJS_TOKEN);
            log.info("redis get token: {}", token);
            return token;
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        HttpEntity resEntity = null;
        String result = null;
        String url = StrUtil.format(Constants.WECHAT_PUBLIC_ACCESS_TOKEN_URL, appId, appSecret);
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
                log.info("redis set token: {}", token);
                redisCache.setCacheObject(Constants.WECHAT_PUBLIC_KEY_WXJS_TOKEN, token, Constants.WECHAT_PUBLIC_EXPIRE_TIME, TimeUnit.SECONDS);
                return res.getString("access_token");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String getPublicTicket(String token) {
        String ticket = null;

        // 从Redis中获取缓存数据
        boolean isTicketExist = redisCache.hasKey(Constants.WECHAT_PUBLIC_KEY_WXJS_TICKET);
        if (isTicketExist) {
            ticket = redisCache.getCacheObject(Constants.WECHAT_PUBLIC_KEY_WXJS_TICKET);
            log.info("redis get ticket: {}", ticket);
            return ticket;
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        HttpEntity resEntity = null;
        String result = null;
        String url = StrUtil.format(Constants.WECHAT_PUBLIC_TICKET_URL, token);
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
                log.info("redis set ticket: {}", ticket);
                redisCache.setCacheObject(Constants.WECHAT_PUBLIC_KEY_WXJS_TICKET, ticket, Constants.WECHAT_PUBLIC_EXPIRE_TIME, TimeUnit.SECONDS);
                return res.getString("ticket");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String> makePublicSignStr(String ticket, String url) {
        Map<String, String> result = new HashMap<>();
        String noncestr = RandomStringUtils.randomAlphanumeric(16);
        result.put("nonceStr", noncestr);
        String  timestamp = new Date().getTime() + "";
        result.put("timestamp", timestamp);

        String signStr = "jsapi_ticket=TICKET&noncestr=NONCESTR&timestamp=TIMESTAMP&url=URL";
        signStr = signStr.replace("TICKET", ticket)
                .replace("NONCESTR", noncestr)
                .replace("TIMESTAMP", timestamp)
                .replace("URL", url);

        log.info("makeSignStr加密前: " + signStr);
        String encodeSignStr = getSha1(signStr);
        log.info("makeSignStr加密后: " + encodeSignStr);
        result.put("signature", encodeSignStr);
        result.put("appId", appId);
        return result;
    }

    private String getSha1(String str) {

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

    /**
     * 微信异常处理
     * @param jsonObject 微信返回数据
     * @param remark 备注
     */
    private void wxExceptionDispose(JSONObject req, JSONObject jsonObject, String remark) {
        PsyWechatExceptions wechatExceptions = new PsyWechatExceptions();
        wechatExceptions.setErrcode(jsonObject.getString("errcode"));
        wechatExceptions.setErrmsg(jsonObject.toJSONString());
        wechatExceptions.setData(req.toJSONString());
        wechatExceptions.setRemark(remark);
        Date date = new Date();
        wechatExceptions.setCreateTime(date);
        wechatExceptions.setUpdateTime(date);
        psyWechatExceptionsService.add(wechatExceptions);
    }

}
