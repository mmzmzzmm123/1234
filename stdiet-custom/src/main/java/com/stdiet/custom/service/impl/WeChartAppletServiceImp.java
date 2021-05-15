package com.stdiet.custom.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.stdiet.common.core.redis.RedisCache;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.SysWxUserInfo;
import com.stdiet.custom.domain.wechat.WxSubscribePostLog;
import com.stdiet.custom.service.ISysWxUserInfoService;
import com.stdiet.custom.service.IWechatAppletService;
import com.stdiet.custom.service.IWxSubscribePostLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class WeChartAppletServiceImp implements IWechatAppletService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ISysWxUserInfoService sysWxUserInfoService;

    @Autowired
    private IWxSubscribePostLogService wxSubscribePostLogService;

    @Override
    public String getAccessToken(String appId) {
        String accessToken = redisCache.getCacheObject(appId);
        if (StringUtils.isNull(accessToken)) {
            String appSecret = "";
            if (appId.equals("wx26be9b2aa525fc1e")) {
                appSecret = "de436c17e42e6fc0637bd0de169ea0c1";
            }
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appId}&secret={appSecret}";
            Map<String, String> param = new HashMap<>();
            param.put("appId", appId);
            param.put("appSecret", appSecret);

            ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class, param);


            JSONObject resultObj = JSONObject.parseObject(entity.getBody());

            accessToken = resultObj.getString("access_token");
            Integer expiresIn = resultObj.getInteger("expires_in");
            redisCache.setCacheObject(appId, accessToken, expiresIn, TimeUnit.SECONDS);
        }
        return accessToken;
    }

    @Override
    public String postRecipesMessage(Long cusId, Long planId, String name, String startDate, String endDate, String remark) {
        SysWxUserInfo sysWxUserInfo = sysWxUserInfoService.selectSysWxUserInfoByCusId(cusId);
        if (StringUtils.isNull(sysWxUserInfo)) {
            return null;
        }

        String accessToken = getAccessToken(sysWxUserInfo.getAppid());
        if (StringUtils.isNull(accessToken)) {
            return "";
        }
        String tmpId = "EWeha9m0ggpnhMANDLHtl2ezLfPWKY_9PsJubbG_6eA";
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken;


        JsonObject param = new JsonObject();
        param.addProperty("access_token", accessToken);
        param.addProperty("touser", sysWxUserInfo.getOpenid());
        param.addProperty("template_id", tmpId);
        param.addProperty("page", "pages/recipes/index");

        JsonObject dataParam = new JsonObject();
        dataParam.addProperty("phrase1", name);
        dataParam.addProperty("date3", startDate);
        dataParam.addProperty("thing6", endDate);
//        dataParam.addProperty("thing6", remark);

        param.add("data", dataParam);

        ResponseEntity<String> entity = restTemplate.postForEntity(url, param, String.class);

        JSONObject resultObj = JSONObject.parseObject(entity.getBody());

        WxSubscribePostLog postLog = new WxSubscribePostLog();
        postLog.setAppid(sysWxUserInfo.getAppid());
        postLog.setOpenid(sysWxUserInfo.getOpenid());
        postLog.setErrcode(resultObj.getInteger("errcode"));
        postLog.setErrmsg(resultObj.getString("errmsg"));
        postLog.setSendTime(DateUtils.getNowDate());
        postLog.setPlanId(planId);
        postLog.setData(dataParam);
        wxSubscribePostLogService.insertWxSubscribePostLog(postLog);

        return resultObj.toJSONString();
//        Integer errcode = resultObj.getInteger("errcode");

    }
}
