package com.ruoyi.common.platform;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.erp.shop.domain.ShopType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.constant.Constants;


public class WishApi {

    //public static void main(String[] args) throws Exception {
        /*StringBuilder result = new StringBuilder();
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[] { new HttpUtils.TrustAnyTrustManager() }, new java.security.SecureRandom());
        URL console = new URL("https://sandbox.merchant.wish.com/api/v2/product?access_token=53555e6c129b4d5ba77c083f80a8debe&format=json&id=5ee714326f563f6d4eba2cf8");
        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
        conn.setRequestProperty("accept", "");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        conn.setRequestProperty("Accept-Charset", "utf-8");
        conn.setRequestProperty("contentType", "utf-8");
        conn.setDoOutput(true);
        conn.setDoInput(true);

        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setHostnameVerifier(new HttpUtils.TrustAnyHostnameVerifier());
        conn.connect();
        InputStream is = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String ret = "";
        while ((ret = br.readLine()) != null)
        {
            if (ret != null && !ret.trim().equals(""))
            {
                result.append(new String(ret.getBytes("ISO-8859-1"), "utf-8"));
            }
        }
        log.info("recv - {}", result);
        conn.disconnect();
        br.close();*/
        //System.out.println(HttpUtils.sendGet("https://sandbox.merchant.wish.com/api/v3/oauth/access_token","client_id=5ef2a92621a3120021446048&client_secret=658f70bc66a848eea3d27856e17faf7d&grant_type=authorization_code&redirect_uri=https%3A%2F%2Ferp.886bk.com%2F&code=2feafab7d6974dfe9bfdc2c4f75861ac"));
    //}
        private static final Logger log = LoggerFactory.getLogger(WishApi.class);

    // API地址(正式上 要去掉 sandbox.)
    public static final String API_URL = "https://sandbox.merchant.wish.com/api/v3/";

    // V2 API地址
    public static final String API_URL_V2 = "https://sandbox.merchant.wish.com/api/v2/";

    //回调地址
    public static final String redirect_uri = "https://erp.886bk.com/";

    //获取token
    public static String getAccessToken(ShopType shopType, String code)
    {
        //String access_token = "";
        try
        {
            String rspStr = HttpUtils.sendGet(API_URL + "oauth/access_token", "client_id=" + shopType.getClientId() + "&client_secret=" + shopType.getClientSecret() + "&grant_type=authorization_code&redirect_uri=" + redirect_uri + "&code=" + code);
            if (StringUtils.isEmpty(rspStr))
            {
                log.error("获取接口异常,得到空值 {}", code);
                return "{\"code\":\"404\"}";
            }
            return rspStr;
        }
        catch (Exception e)
        {
            log.error("获取接口异常 {}", code);
        }
        return "{\"code\":\"404\"}";
    }

    //创建下载订单任务
    public static String createDownloadJob(String accessToken, String startTime, String endTime)
    {
        try
        {
            String param = "access_token=" + accessToken;
            if(startTime != "" && endTime != ""){ param += "&start=" + startTime + "&end=" + endTime;}
            String rspStr = HttpUtils.sendPost(API_URL_V2 + "order/create-download-job", param);
            if (StringUtils.isEmpty(rspStr))
            {
                log.error("createDownloadJob获取接口异常,得到空值 {}", accessToken);
                return "{\"code\":\"404\"}";
            }
            return rspStr;
        }
        catch (Exception e)
        {
            log.error("createDownloadJob获取接口异常 {}", accessToken);
        }
        return "{\"code\":\"404\"}";
    }

    //下载订单任务
    public static String downloadOrderJob(String accessToken, String jobId)
    {
        try
        {
            String param = "access_token=" + accessToken + "&job_id=" + jobId;
            String rspStr = HttpUtils.sendPost(API_URL_V2 + "order/get-download-job-status", param);
            if (StringUtils.isEmpty(rspStr))
            {
                log.error("downloadOrderJob获取接口异常,得到空值 {}", jobId);
                return "{\"code\":\"404\"}";
            }
            return rspStr;
        }
        catch (Exception e)
        {
            log.error("downloadOrderJob获取接口异常 {}", jobId);
        }
        return "{\"code\":\"404\"}";
    }

}
