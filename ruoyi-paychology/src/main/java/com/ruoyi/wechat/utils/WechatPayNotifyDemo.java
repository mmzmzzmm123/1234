package com.ruoyi.wechat.utils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class WechatPayNotifyDemo {
    private static final String NOTIFY_URL = "https://yourdomain.com/notify"; // 请替换为您自己的通知URL

    public static void main(String[] args) throws Exception {
        // 从微信服务器获取POST请求参数
        Map<String, String> params = new HashMap<>();
        String requestBody = getRequestBodyFromWechat();
        String[] requestParams = requestBody.split("&");
        for (String requestParam : requestParams) {
            String[] param = requestParam.split("=");
            if (param.length == 2) {
                params.put(param[0], param[1]);
            }
        }

        // 验证签名
        String signature = params.get("sign");
        String[] signParams = requestBody.split("&")[0...(requestBody.length() / 2 - 1)];
        Arrays.sort(signParams);
        String sortedParams = "";
        for (String signParam : signParams) {
            sortedParams += signParam + "=";
        }
        String yourAppSign = generateSign(sortedParams); // 请替换为您的应用签名方法
        if (yourAppSign.equals(signature)) {
            // 验证签名通过，执行相应的业务逻辑
            System.out.println("支付通知验证通过，执行业务逻辑");
            // ...

        } else {
            // 验证签名失败，可进行相应的处理
            System.out.println("支付通知验证失败");
            // ...
        }
    }

    private static String getRequestBodyFromWechat() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        while ((inputLine = reader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }
        return stringBuilder.toString();
    }

    private static String generateSign(String params) {
        // 请替换为您自己的签名方法
        // ...
        return "your_sign"; // 请替换为您自己的签名结果
    }
}