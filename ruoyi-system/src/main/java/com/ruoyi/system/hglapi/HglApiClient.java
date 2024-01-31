package com.ruoyi.system.hglapi;

import cn.hutool.http.*;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.TypeReference;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.extend.UtTouchApiEnum;
import com.ruoyi.system.extend.UtTouchProperties;
import com.ruoyi.system.extend.UtTouchResult;
import com.ruoyi.system.extend.data.*;
import com.ruoyi.system.hglapi.input.GetMerchantTokenInputDTO;
import com.ruoyi.system.hglapi.output.GetMerchantTokenOutputDTO;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiProperties;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.ApiClientDTO;
import com.ruoyi.system.openapi.model.output.ApiClientVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Jing.Zhang
 */
@Slf4j
@Component
public abstract class HglApiClient {

    public static <T> T post(HglApiEnum api, JSONObject body, Class<T> responseClass) {

        String requestUrl = SpringUtils.getBean(HglApiProperties.class).getApiHost() + api.getRequestUrl();

        String token = null;
        if (!HglApiEnum.GET_MERCHANT_TOKEN.equals(api)) {
            token = HglApiClient.getToken();
        }

        // 生成 TraceId 方便排查问题
        String traceId = Ids.getId();

        String response = null;
        try {
            String jsonBody = JSON.toJSONString(body, "yyyy-MM-ddTHH:mm:ss");
            log.info("调用HGL相关接口 {} {} {} {} {}", traceId, requestUrl, jsonBody, token, api.name());
            HttpRequest request = HttpUtil.createPost(requestUrl).header("Bearer ", token).body(jsonBody, ContentType.JSON.toString());
            HttpResponse execute = request.execute();
            response = execute.body();
            log.info("调用HGL相关接口获得响应 {} {}", traceId, response);
        } catch (HttpException e) {
            log.info("调用HGL相关接口发生异常 {} {}", traceId, e.getMessage(), e);
            throw e;
        }

        T result = JSON.parseObject(response, responseClass);

        return result;
    }

    public static GetMerchantTokenOutputDTO login() {
        String loginName = SpringUtils.getBean(HglApiProperties.class).getLoginName();
        String loginPassword = SpringUtils.getBean(HglApiProperties.class).getLoginPassword();
        GetMerchantTokenInputDTO dto = new GetMerchantTokenInputDTO();
        dto.setLoginName(loginName);
        dto.setLoginPassword(loginPassword);

        GetMerchantTokenOutputDTO<String> result = getMerchantToken(dto);
        return Optional.ofNullable(result)
                .orElseThrow(() -> new GlobalException("调用HGL登录发生错误"));
    }

    public static String getToken() {
        RedisTemplate<String, String> redisTemplate = SpringUtils.getBean("redisTemplate");
        String token = redisTemplate.boundValueOps(getKey()).get();
        if (StringUtils.isBlank(token)) {

            // 调用接口获取Token
            GetMerchantTokenOutputDTO outputDTO = HglApiClient.login();
            token = (String) outputDTO.getData();

            // Redis中缓存Token
            if (StringUtils.isNotBlank(token)) {
                redisTemplate.boundValueOps(getKey()).set(token);
                redisTemplate.boundValueOps(getKey()).expire(outputDTO.getExpiressIn() - 600, TimeUnit.SECONDS);
            }
        }
        return token;
    }

    private static String getKey() {
        return "ut-buleprint-backend:HglApiToken";
    }

    public static void main(String[] args) {
        login();
    }

    /**
     * 商家登录 - 获取商家的Token
     *
     * @param data 登录数据
     * @return
     */
    public static GetMerchantTokenOutputDTO<String> getMerchantToken(GetMerchantTokenInputDTO data) {
        return HglApiClient.post(HglApiEnum.GET_MERCHANT_TOKEN, JSONObject.from(data), GetMerchantTokenOutputDTO.class);
    }



}
