package com.xinyu.idol.utils;



import com.alibaba.fastjson2.JSON;

import com.xinyu.idol.common.core.domain.R;
import com.xinyu.idol.common.utils.bean.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * <p>
 *  todo
 * </p>
 *
 * @author yu.li
 * @Date 2022/12/29 22:23
 */

@Slf4j
public class OkHttpUtil {

    public static final MediaType jsonType = MediaType.parse("application/json; charset=utf-8");

    public static final MediaType mediaType = MediaType.parse("application/octet-stream");

    public final static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(8000, TimeUnit.MILLISECONDS)
            .readTimeout(8000, TimeUnit.MILLISECONDS)
            .build();

    /**
     * 发送post请求通过Form表单形式
     *
     * @param reqUrl 请求url
     * @param mapParam 请求参数
     *
     */
    public static void sendPostByForm(String reqUrl, Map<String,String> mapParam){
        try {
            long startTime = System.currentTimeMillis();
            //循环form表单，将表单内容添加到form builder中
            FormBody.Builder formBody = new FormBody.Builder();
            for (Map.Entry<String, String> m : mapParam.entrySet()) {
                String name = m.getKey();
                String value = m.getValue()+"";
                formBody.add(name, value);
            }
            //构建formBody(formBody.build())，将其传入Request请求中
            Request.Builder builder = new Request.Builder().url(reqUrl).post(formBody.build());
            try(Response response = okHttpClient.newCall(builder.build()).execute()){
                String body = response.body().string();
                log.info("{} response body:{}", reqUrl.substring(reqUrl.lastIndexOf("/") + 1),
                        body);
            }catch(Exception e){
                log.error("调用接口出错\n"+ ExceptionUtils.getMessage(e));
            }finally{
                long endTime = System.currentTimeMillis();
                log.info("{} cost time:{}", reqUrl.substring(reqUrl.lastIndexOf("/") + 1),
                        (endTime - startTime));
            }
        } catch (Exception e) {
            log.error("error", e);
        }
    }

    /**
     * 发送post请求通过JSON参数,并且转成bean
     *
     * @param reqUrl 请求url
     * @param param 请求参数
     *
     */
    public static <T>T postJsonAndToBean(String reqUrl, Object param,Class clazz){
        try {
            String paramStr = JSON.toJSONString(param);

            RequestBody.create(paramStr,jsonType);
            RequestBody requestBody = RequestBody.create(jsonType, paramStr);
            //long startTime = System.currentTimeMillis();
            Request.Builder builder = new Request.Builder().url(reqUrl).post(requestBody);

            try(Response response = okHttpClient.newCall(builder.build()).execute()){
                wrapNetworkResponse(response);
                String body = response.body().string();
                T t = R.<T>wrapData(body, clazz);
                log.info("response body:{}",body);
                return t;
            }catch(Exception e){
                log.error("调用接口出错\n"+ ExceptionUtils.getMessage(e));
                throw new RuntimeException("sendPostByJson okhttp异常:");
            }

        } catch (Exception e) {
            log.error("error", e);
            throw new RuntimeException("sendPostByJson okhttp异常");
        }

    }

    /**
     * 发送get请求通过JSON参数,并且转成bean
     *
     * @param reqUrl 请求url
     * @param clazz 请求参数
     *
     */
    public static <T>T getJsonAndToBean(String reqUrl,Class clazz){
        try {
            Request.Builder builder = new Request.Builder().url(reqUrl).get();
            try(Response response = okHttpClient.newCall(builder.build()).execute()){
                wrapNetworkResponse(response);
                String body = response.body().string();
                T t = R.<T>wrapData(body, clazz);

                log.info("response body:{}",body);
                return t;
            }catch(Exception e){
                log.error("调用接口出错\n"+ ExceptionUtils.getMessage(e));
                throw new RuntimeException("sendPostByJson okhttp异常");
            }

        } catch (Exception e) {
            log.error("error", e);
            throw new RuntimeException("sendPostByJson okhttp异常");
        }
    }

    private static void wrapNetworkResponse(Response response){
        int code = response.networkResponse().code();
        if(code!=200){
            throw new RuntimeException("远程接口响应失败:"+ response.networkResponse().toString());
        }
    }

}
