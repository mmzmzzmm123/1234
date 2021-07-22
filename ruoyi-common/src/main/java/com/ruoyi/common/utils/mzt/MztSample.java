package com.ruoyi.common.utils.mzt;

import com.alibaba.fastjson.JSON;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author Flex Hu
 * 调用接口例子
 * @version 1.1
 */
public class MztSample {
    
    /**
     * POST参数
     * @param url
     * @param params
     * @return
     */
    public static String sendPostParams(String url,Map<String,Object> params){
        DefaultHttpClient httpclient = new DefaultHttpClient(
                new ThreadSafeClientConnManager());
        HttpPost httpost = new HttpPost(url);
        BasicResponseHandler responseHandler = new BasicResponseHandler();
        // 添加参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null && params.keySet().size() > 0) {
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String,Object> entry = (Entry<String,Object>) iterator.next();
                nvps.add(new BasicNameValuePair((String) entry.getKey(),
                        (String) entry.getValue())); 
            }
        }
        httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        try {
            String result =  httpclient.execute(httpost,responseHandler).toString();
            httpclient.getConnectionManager().shutdown();
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        String url = "https://mztapp.fujian.gov.cn:8304/"
                + "dataset/AppSerController/invokeservice.do";
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        /*paramsMap.put("INVOKESERVICE_CODE","062");
        paramsMap.put("INVOKECALLER_CODE","授权码");
        paramsMap.put("USER_IDCARD",AESOperator.aesEncrypt("35222719870103333"));
        paramsMap.put("USER_NAME",AESOperator.aesEncrypt("张三"));
        paramsMap.put("USER_MOBILE",AESOperator.aesEncrypt("13452031733"));*/
        
        paramsMap.put("INVOKESERVICE_CODE","033");
        paramsMap.put("INVOKECALLER_CODE","2bf26e96740adbedda3917e8d72f1989f1d3c8d9");
        paramsMap.put("USER_ID","1");
        paramsMap.put("USER_TOKEN","1");
//        paramsMap.put("TRUST_TICKET","34343433dddd");
        String POSTPARAM_JSON = JSON.toJSONString(paramsMap);
        Map<String,Object> clientParam = new HashMap<String,Object>();
        clientParam.put("POSTPARAM_JSON", POSTPARAM_JSON);
        String result = sendPostParams(url, clientParam);
        System.out.println(result);
    }

}
