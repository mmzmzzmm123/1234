package com.ruoyi.system.bot;

import cn.hutool.http.*;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.openapi.OpenApiProperties;
import com.ruoyi.system.openapi.model.output.ExtTgSelectRobotByMerchantVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author 朱少波
 * @date 2024/1/17/017 19:18
 */
@Slf4j
@Component
public class ApiClient {

    public static <T> ApiResult<T> post(ApiEnum api, JSONObject body, Class<T> responseClass) {

        String requestUrl = SpringUtils.getBean(OpenApiProperties.class).getOpenApiHost() + api.getRequestUrl();
        // 生成 TraceId 方便排查问题
        String traceId = Ids.getId();

        String response = null;
        try {
            String jsonBody = JSON.toJSONString(body);
            log.info("调用BOT相关接口 {} {} {} {}", traceId, requestUrl, jsonBody, api.name());
            HttpRequest request = HttpUtil.createPost(requestUrl).body(jsonBody, ContentType.JSON.toString());
            HttpResponse execute = request.execute();
            response = execute.body();
            log.info("调用BOT相关接口获得响应 {} {}", traceId, response);
        } catch (HttpException e) {
            log.info("调用BOT相关接口发生异常 {} {}", traceId, e.getMessage(), e);
            return ApiResult.failed("系统繁忙,请稍后重试" + traceId);
        }

        ApiResult<T> result = JSON.parseObject(response, new TypeReference<ApiResult<T>>(responseClass) {
        });

        return result;
    }

    public static <T> ApiResult<T> post(ApiEnum api, JSONObject body, TypeReference<T> typeReference) {

        // 先使用 String 接收
        ApiResult<String> tempResult = ApiClient.post(api, body, String.class);
        T data = Optional.ofNullable(tempResult).map(ApiResult::getData).map(it -> JSONObject.parseObject(it, typeReference)).orElse(null);

        ApiResult<T> result = new ApiResult<>();
        BeanUtils.copyProperties(tempResult, result);
        result.setData(data);

        return result;
    }


    public static ApiResult<Page<ExtTgSelectRobotByMerchantVO>> getBot(String data) {
        return ApiClient.post(ApiEnum.GET_BOT, JSONObject.from(data), new TypeReference<Page<ExtTgSelectRobotByMerchantVO>>(ExtTgSelectRobotByMerchantVO.class) {
        });
    }


    public static ApiResult<String> setBotAdMonitor(String data) {
        return ApiClient.post(ApiEnum.SET_BOT_AD_MONITOR, JSONObject.from(data), String.class);
    }

}
