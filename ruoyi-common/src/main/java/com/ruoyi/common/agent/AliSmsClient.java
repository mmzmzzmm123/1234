package com.ruoyi.common.agent;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Administrator on 2019/6/13.
 */
@Slf4j
public class AliSmsClient {
    public static final String accessKeyId = "LTAI4GEarWQ4iftggaobeYAG";
    public static final String accessSecret = "vbncaPx5G8x0Lwo6NI56bbuCzPBJU6";

    public static void send(String phone, String code) {
        send(phone, code, 3);
    }

    public static void send(String phone, String code, Integer maxTry) {
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        JSONObject jcode = new JSONObject();
        jcode.put("code", code);
        log.info("json code is {}", JSON.toJSONString(jcode));

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("TemplateCode", "SMS_186577787");
        request.putQueryParameter("SignName", "云鲲计算");
        request.putQueryParameter("TemplateParam", JSON.toJSONString(jcode));
        try {
            CommonResponse response = client.getCommonResponse(request);
            log.info(response.getData());

            if (response.getData().indexOf("isv.BUSINESS_LIMIT_CONTROL") > 0) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (maxTry > 0) {
                    maxTry--;
                    send(phone, code, maxTry);
                }


            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }


}
