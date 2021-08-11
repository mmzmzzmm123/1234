package com.stdiet.custom.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.stdiet.common.core.redis.RedisCache;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.domain.SysWxUserInfo;
import com.stdiet.custom.domain.wechat.WxSubscribePostLog;
import com.stdiet.custom.service.ISysCustomerService;
import com.stdiet.custom.service.ISysWxUserInfoService;
import com.stdiet.custom.service.IWechatAppletService;
import com.stdiet.custom.service.IWxSubscribePostLogService;
import com.stdiet.custom.utils.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class WeChartAppletServiceImp implements IWechatAppletService {
    static final String WX_LITE_SUBSCRIBE_TEM_ID = "Ow0j0Jt4OJhjy6GruBstOMLTGjAVagM4hTZRLAaxqJo";

    static final String WX_PUBLIC_CUSTOMER_PUNCH_NOTICE_TEM_ID = "XapMBz6KsPW6_rK5__oxxaY8cSLyVYyoSljstZsITyo";

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ISysWxUserInfoService sysWxUserInfoService;

    @Autowired
    private IWxSubscribePostLogService wxSubscribePostLogService;

    @Autowired
    private ISysCustomerService sysCustomerService;

    @Override
    public String getAccessToken(String appId) {
        String accessToken = redisCache.getCacheObject(appId);
        if (StringUtils.isNull(accessToken)) {
            String appSecret = "";
            if (appId.equals("wx26be9b2aa525fc1e")) {
                appSecret = "de436c17e42e6fc0637bd0de169ea0c1";
            } else if (appId.equals("wx4a9c1fc9dba53202")) {
                appSecret = "fff029ade5d3575df755f4cf9e52f8da";
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
    public Integer postSubscribeMessage(Long cusId, Long planId, String name, String startDate, String endDate, String remark) {
        SysWxUserInfo sysWxUserInfo = sysWxUserInfoService.selectSysWxUserInfoByCusId(cusId);
        if (StringUtils.isNull(sysWxUserInfo) || StringUtils.isNull(sysWxUserInfo.getAppid())) {
            return -1;
        }

        String accessToken = getAccessToken(sysWxUserInfo.getAppid());
        if (StringUtils.isNull(accessToken)) {
            return -1;
        }
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken;


        JSONObject param = new JSONObject();
        param.put("access_token", accessToken);
        param.put("touser", sysWxUserInfo.getOpenid());
        param.put("template_id", WX_LITE_SUBSCRIBE_TEM_ID);
        param.put("page", "pages/recipes/index");


        JSONObject dataParam = new JSONObject();
        dataParam.put("thing1", JSONObject.parse("{\"value\":\"" + name + "\"}"));
        dataParam.put("time2", JSONObject.parse("{\"value\":\"" + startDate + "\"}"));
        dataParam.put("time3", JSONObject.parse("{\"value\":\"" + endDate + "\"}"));
        String mRemark = StringUtils.isNull(remark) ? "无" : remark;
        dataParam.put("thing4", JSONObject.parse("{\"value\":\"" + mRemark + "\"}"));

        param.put("data", dataParam);

        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        ResponseEntity<String> entity = restTemplate.postForEntity(url, param.toJSONString(), String.class);

        JSONObject resultObj = JSONObject.parseObject(entity.getBody());

        WxSubscribePostLog postLog = new WxSubscribePostLog();
        postLog.setAppid(sysWxUserInfo.getAppid());
        postLog.setOpenid(sysWxUserInfo.getOpenid());
        int errCode = resultObj.getInteger("errcode");
        postLog.setType(0);
        postLog.setResult(resultObj);
        postLog.setSendTime(DateUtils.getNowDate());
        postLog.setPlanId(planId);
        //
        dataParam.put("tmpId", WX_LITE_SUBSCRIBE_TEM_ID);
        postLog.setData(dataParam);
        wxSubscribePostLogService.insertWxSubscribePostLog(postLog);

        return errCode;
    }

    @Override
    public Integer postSms(Long cusId, Long planId, String plan) {
        try {
            SysCustomer customer = sysCustomerService.selectSysCustomerById(cusId);
            JSONObject paramObj = new JSONObject();
            paramObj.put("plan", plan);
            SendSmsResponse response = SmsUtils.sendSms(customer.getPhone(), paramObj.toJSONString(), SmsUtils.SMS_216839183, SmsUtils.SMS_SIGN_NAME);

            WxSubscribePostLog postLog = new WxSubscribePostLog();
            postLog.setPhone(customer.getPhone());
            postLog.setPlanId(planId);
            JSONObject resultObj = new JSONObject();
            resultObj.put("requestId", response.getRequestId());
            resultObj.put("bizId", response.getBizId());
            resultObj.put("code", response.getCode());
            resultObj.put("message", response.getMessage());
            postLog.setResult(resultObj);
            JSONObject dataParam = new JSONObject();
            dataParam.put("phone", customer.getPhone());
            dataParam.put("plan", plan);
            dataParam.put("tmpCode", SmsUtils.SMS_216839183);
            dataParam.put("signName", SmsUtils.SMS_SIGN_NAME);
            postLog.setData(dataParam);
            postLog.setSendTime(DateUtils.getNowDate());
            postLog.setType(1);
            wxSubscribePostLogService.insertWxSubscribePostLog(postLog);

            return response.getCode().equals("OK") ? 0 : -1;
        } catch (Exception e) {
            System.out.println(e);
        }
        return -1;
    }

    @Override
    public Integer postCustomerPunchNotice(String name,  String toUser) {
        String accessToken = getAccessToken("wx4a9c1fc9dba53202");
        if (StringUtils.isNull(accessToken)) {
            return -1;
        }
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;

        JSONObject param = new JSONObject();
        param.put("touser", toUser);
        param.put("template_id", WX_PUBLIC_CUSTOMER_PUNCH_NOTICE_TEM_ID);
//        param.put("url", "pages/recipes/index");
//        JSONObject miniData = new JSONObject();
//        miniData.put("appid", "");
//        miniData.put("pagepath", "");
//        param.put("miniprogram", miniData);

        JSONObject dataParam = new JSONObject();
        dataParam.put("first", JSONObject.parse("{\"value\":\"完成打卡\"}"));
        dataParam.put("keyword1", JSONObject.parse("{\"value\":\"" + name + "\"}"));
        dataParam.put("keyword2", JSONObject.parse("{\"value\":\"" + DateUtils.getTime() + "\"}"));
        dataParam.put("keyword3", JSONObject.parse("{\"value\":\"成功\"}"));
        dataParam.put("remark", JSONObject.parse("{\"value\":\" \"}"));

        param.put("data", dataParam);

        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        ResponseEntity<String> entity = restTemplate.postForEntity(url, param.toJSONString(), String.class);

        JSONObject resultObj = JSONObject.parseObject(entity.getBody());

        WxSubscribePostLog postLog = new WxSubscribePostLog();
        postLog.setAppid("wx4a9c1fc9dba53202");
        postLog.setOpenid(toUser);
        int errCode = resultObj.getInteger("errcode");
        postLog.setType(2);
        postLog.setResult(resultObj);
        postLog.setSendTime(DateUtils.getNowDate());
        //
        dataParam.put("tmpId", WX_PUBLIC_CUSTOMER_PUNCH_NOTICE_TEM_ID);
        postLog.setData(dataParam);
        wxSubscribePostLogService.insertWxSubscribePostLog(postLog);

        return errCode;
    }


}
