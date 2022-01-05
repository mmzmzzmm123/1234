package com.ruoyi.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.text.CharsetKit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;

/**
 * @author libiao
 * @version 1.0
 * @Description: 虾皮2.0授权工具类
 * @date 2021/10/24 21:12
 **/
public class ShopAuth {
    public static final String AUTH_SIGN_METHOD = "HmacSHA256";

    public static void main(String[] args) {
        // 虾皮测试环境
        String host = "https://partner.test-stable.shopeemobile.com";
        String path = "/api/v2/shop/auth_partner";
        //TODO 回跳到花生壳内网地址（转发到8080端口），需要补充URI
        String redirect_url = "http://4v4c570155.zicp.vip:47393/swagger-ui/index.html";
        // edterp 的 测试 Partner
        long partner_id = 1002192L;
        String partner_key = "8e8618dbb99923b4b298517d263b0b45df02a2ef8341d512539bdbacc76b0bb1";
        String authUrl = calculateShopAuthUrl(host, path, redirect_url, partner_id, partner_key);
        System.out.println(authUrl);

        getTokenShopLevel("code123",partner_id,partner_key,"");
    }

    /**
     * @Description: 虾皮店铺授权链接生成
     * 1.拼接基础字符串，api path + partner_id + api path + timestamp
     * 2.基于基础字符串计算签名
     * @Author: libiao
     * @Date: 2021/10/24 21:56
     * @param host: 虾皮地址
     * @param path: 虾皮授权接口路径（授权："/api/v2/shop/auth_partner"，取消授权："/api/v2/shop/cancel_auth_partner"）
     * @param redirect_url: 授权成功后跳转地址，ERP的地址
     * @param partner_id: 开发者账号id
     * @param partner_key: 开发者账号key
     * @return: java.lang.String 授权链接
     **/
    public static String calculateShopAuthUrl(String host, String path, String redirect_url, long partner_id, String partner_key) {
        long timestamp = System.currentTimeMillis() / 1000L;
        String tmp_base_string = String.format("%s%s%s", partner_id, path, timestamp);
        byte[] partner_key_bytes;
        byte[] base_string_bytes;
        // 计算加密签名
        BigInteger sign = null;
        String sign_str = "";
        try {
            partner_key_bytes = partner_key.getBytes(CharsetKit.CHARSET_UTF_8);
            base_string_bytes = tmp_base_string.getBytes(CharsetKit.CHARSET_UTF_8);
            Mac mac = Mac.getInstance("HmacSHA256"); //"HmacSHA256"
            SecretKeySpec secretKeySpec = new SecretKeySpec(partner_key_bytes, "HmacSHA256");
            mac.init(secretKeySpec);
            //sign = new BigInteger(mac.doFinal(base_string_bytes));
            byte[] sign_bytes = mac.doFinal(base_string_bytes);
            sign_str = byteArrayToHexString(sign_bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 拼接授权url
        //String url = host + path + String.format("?partner_id=%s&timestamp=%s&sign=%032x&redirect=%s", partner_id, timestamp, sign, redirect_url);
        String url = host + path + String.format("?partner_id=%s&timestamp=%s&sign=%s&redirect=%s", partner_id, timestamp, sign_str, redirect_url);
        return url;
    }

    /**
     * @Description: 获取店铺级别接口鉴权access_token refresh_token
     * @Author: libiao
     * @Date: 2021/11/3 21:16
     * @param code: 店铺授权成功后返回的
     * @param partner_id: 开发者账号id
     * @param partner_key: 开发者账号key
     * @param shop_id: 店铺id(店铺授权成功后也会返回)
     * @return: void
     **/
    public static void getTokenShopLevel(String code, long partner_id, String partner_key, String shop_id){
        long timestamp = System.currentTimeMillis()/1000L;
        JSONObject body = new JSONObject();
        body.put("code",code);
        body.put("shop_id",shop_id);
        body.put("partner_id", partner_id);

        //String host = "https://partner.shopeemobile.com/api/v2/auth/token/get";
        String host = "https://partner.test-stable.shopeemobile.com";
        String path = "/api/v2/auth/token/get";
        String tmp_base_string = String.format("%s%s%s",partner_id,path,timestamp);

        byte[] partner_key_bytes;
        byte[] base_string_bytes;

        String sign = null;
        try {
            partner_key_bytes = partner_key.getBytes("UTF-8");
            base_string_bytes = tmp_base_string.getBytes("UTF-8");
            Mac mac = Mac.getInstance(AUTH_SIGN_METHOD); //"HmacSHA256"
            SecretKeySpec secretKeySpec = new SecretKeySpec(partner_key_bytes,AUTH_SIGN_METHOD);
            mac.init(secretKeySpec);
            byte[] sign_bytes = mac.doFinal(base_string_bytes);
            sign = byteArrayToHexString(sign_bytes);
        }catch (Exception e){
            e.printStackTrace();
        }
        // 拼接授权url
        String url = host + path + String.format("?partner_id=%s&timestamp=%s&sign=%s",partner_id,timestamp,sign);
        // TODO http请求获取access_token和refresh_token
        System.out.println(url);
    }

    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }
}
