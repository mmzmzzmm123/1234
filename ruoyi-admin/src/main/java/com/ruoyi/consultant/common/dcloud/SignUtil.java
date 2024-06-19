package com.ruoyi.consultant.common.dcloud;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Map;
import java.util.TreeMap;

public class SignUtil {
    public static String sign(String secret,Map<String, String> params ) throws Exception {

        // 字母顺序排序后拼接签名串
        Map<String, String> sortedParams = new TreeMap<>(params);
        StringBuilder signStrBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            signStrBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        String signStr = signStrBuilder.toString();
        signStr = getSignStr(params);

        Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        hmacSHA256.init(secretKey);

        // 计算签名
        byte[] hash = hmacSHA256.doFinal(signStr.getBytes());
        String sign = bytesToHex(hash);
        params.put("sign", sign);
        signStr = getSignStr(params);
        return signStr;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xff & aByte);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static String getSignStr(Map<String, String> params )
    {
        Map<String, String> sortedParams = new TreeMap<>(params);
        StringBuilder signStrBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            signStrBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        String signStr = signStrBuilder.toString();
        signStr = signStr.substring(0, signStr.length() - 1); // 去掉最后的"&"

        return signStr;

    }
}