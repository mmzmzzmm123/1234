package com.ruoyi.system.utils;

import cn.hutool.core.codec.Base64;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.http.HttpHeaders;

import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 开放接口OpenApi鉴权样例工具类
 */
public class OpenApiAuthUtil {


    /**
     * 调试
     * @param args
     */
//    public static void main(String[] args) {
//        String appKey = "";
//        String appSecret = "";
//        generateAuthHeaders(appKey, appSecret);
//    }

    /**
     * 根据appKey和appSecret生成接口鉴权请求头信息
     * @param appKey
     * @param appSecret
     * @return
     */
    public static HttpHeaders generateAuthHeaders(String appKey, String appSecret) {
        // 一次性随机字符串，取32位uuid，连续两次请求的nonce不能重复，否则将认为是重复请求，请求无效
        String nonce = UUID.randomUUID().toString().replace("-", "");
        // 时间戳，毫秒，超过一定时间请求无效
        String timestamp = String.valueOf(System.currentTimeMillis());
        // 拼接原始签名字符串
        String originalSign = appSecret + nonce + timestamp;
        // 使用国密SM3算法对原始签名进行摘要加密，生成签名
        String sign = Base64.encode(sm3Hash(originalSign.getBytes()));

        System.out.println("app-key:" + appKey);
        System.out.println("nonce:" + nonce);
        System.out.println("timestamp:" + timestamp);
        System.out.println("sign:" + sign);

        HttpHeaders headers = new HttpHeaders();
        headers.add("app-key", appKey);
        headers.add("nonce", nonce);
        headers.add("timestamp", timestamp);
        headers.add("sign", sign);
        return headers;
    }

    /**
     * 验证鉴权签名
     * @param appSecret
     * @param nonce
     * @param timestamp
     * @param sign
     * @return
     */
    public static boolean verifySign(String appSecret, String nonce, String timestamp, String sign) {
        String validSign = appSecret + nonce + timestamp;
        return sm3Verify(validSign.getBytes(), sign.getBytes());
    }




    /**
     * 添加BouncyCastle国密安全服务
     */
    static {
        Security.addProvider(new BouncyCastleProvider());
    }


    /**
     * 计算SM3摘要值
     *
     * @param srcData 原文
     * @return 摘要值，对于SM3算法来说是32字节
     */
    public static byte[] sm3Hash(byte[] srcData) {
        SM3Digest digest = new SM3Digest();
        digest.update(srcData, 0, srcData.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return hash;
    }

    /**
     * 验证SM3摘要
     *
     * @param srcData 原文
     * @param sm3Hash 摘要值
     * @return 返回true标识验证成功，false标识验证失败
     */
    public static boolean sm3Verify(byte[] srcData, byte[] sm3Hash) {
        byte[] newHash = sm3Hash(srcData);
        if (Arrays.equals(newHash, sm3Hash)) {
            return true;
        } else {
            return false;
        }
    }


}
