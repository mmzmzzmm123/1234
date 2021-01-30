package com.stdiet.custom.utils;

import com.alibaba.fastjson.JSONObject;
import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.redis.RedisCache;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.http.HttpUtils;
import com.stdiet.custom.domain.WxXmlData;
import com.stdiet.custom.domain.wechat.WxAccessToken;
import com.stdiet.custom.domain.wechat.WxFileUploadResult;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public class WxTokenUtils {

    // 与接口配置信息中的Token要一致
    private static String token = "shengtangdiet";
//    private static String appId = "wx4a9c1fc9dba53202";
//    private static String appSecret = "fff029ade5d3575df755f4cf9e52f8da";
private static String appId = "wx11c6f62133e58d57";
        private static String appSecret = "239eff8a38be32f2feafa924410b3c11";
    private static String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token";

    public static WxAccessToken fetchAccessToken() {
        try {
            String resStr = HttpUtils.sendGet(tokenUrl, "grant_type=client_credential&appid=" + appId + "&secret=" + appSecret);
            if (StringUtils.isEmpty(resStr)) {
                return null;
            }
            JSONObject obj = JSONObject.parseObject(resStr);

            WxAccessToken token = JSONObject.toJavaObject(obj, WxAccessToken.class);
            return token;
        } catch (Exception e) {
            return null;
        }
    }

//    public static WxFileUploadResult uploadImage() {
//
//    }

    /**
     * 验证签名
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{token, timestamp, nonce};
        // 将token、timestamp、nonce三个参数进行字典序排序
        // Arrays.sort(arr);
        sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }

    public static void sort(String a[]) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[j].compareTo(a[i]) < 0) {
                    String temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

    public static WxXmlData resolveXmlData(InputStream in) {
        WxXmlData wxXmlData = null;
        try {
            String xmlData = IOUtils.toString(in, StandardCharsets.UTF_8.name());
            XStream xstream = new XStream();
            //这个必须要加 不然无法转换成WxXmlData对象
            xstream.setClassLoader(WxXmlData.class.getClassLoader());
            xstream.processAnnotations(WxXmlData.class);
            xstream.alias("xml", WxXmlData.class);
            wxXmlData = (WxXmlData) xstream.fromXML(xmlData);
//            log.info("【wxXmlData: {}】 ", wxXmlData);
        } catch (Exception e) {
//            log.error("【error】{}", e.getMessage());
        }
        return wxXmlData;
    }
}
