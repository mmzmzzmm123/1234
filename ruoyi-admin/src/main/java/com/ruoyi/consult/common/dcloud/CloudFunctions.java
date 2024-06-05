package com.ruoyi.consult.common.dcloud;

import java.util.HashMap;
import java.util.Map;


import cn.hutool.json.JSONObject;
import com.ruoyi.common.core.domain.dto.ConsultLoginDTO;
import com.ruoyi.common.utils.http.HttpUtils;

public class CloudFunctions {
    public static String getPhoneNumber(ConsultLoginDTO consultLoginDTO) throws Exception {

        Map<String, String> params = new HashMap<>();
        params.put("access_token", consultLoginDTO.getAccess_token()); // 客户端传到自己服务器的参数
        params.put("openid", consultLoginDTO.getOpenid());

        String requestUrl="https://fc-mp-b531bf3e-ef6d-4478-b2c9-350c3814888d.next.bspapp.com/getphonenumber";
        String secret="4/81I10MHq3zI7XDVLJ8OA==";

        String requestParams=SignUtil.sign(secret,params);

        String res=HttpUtils.sendSSLPost(requestUrl,requestParams);

        JSONObject jsonObject = new JSONObject(res);
        String phoneNumber=jsonObject.getJSONObject("data").getStr("phoneNumber");
        return phoneNumber;
    }

  
}