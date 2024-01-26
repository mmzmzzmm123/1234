package com.ruoyi.system.bot;

import cn.hutool.http.*;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.bot.mode.input.AdMonitorDTO;
import com.ruoyi.system.bot.mode.input.PageDTO;
import com.ruoyi.system.bot.mode.output.BotInfoVO;
import com.ruoyi.system.bot.mode.output.GroupStateVO;
import com.ruoyi.system.domain.base.PageBaseDTO;
import com.ruoyi.system.openapi.OpenApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 朱少波
 * @date 2024/1/17/017 19:18
 */
@Slf4j
@Component
public class ApiClient {

    public static <T> ApiResult<T> post(ApiEnum api, String pathVariable, Class<T> responseClass) {

        String requestUrl = SpringUtils.getBean(BotProperties.class).getApiUrl() + String.format(api.getRequestUrl(), pathVariable);
        // 生成 TraceId 方便排查问题
        String traceId = Ids.getId();

        String response = null;
        try {

            HttpRequest request = HttpUtil.createPost(requestUrl).body("", ContentType.JSON.toString());
            HttpResponse execute = request.execute();
            response = execute.body();
            log.info("调用bot服务 {}, {}, {},{}", traceId, requestUrl, pathVariable, response);
        } catch (HttpException e) {
            log.info("调用BOT相关接口发生异常 {}, {}, {}", traceId, requestUrl, e.getMessage(), e);
            return ApiResult.failed("系统繁忙,请稍后重试" + traceId);
        }

        ApiResult<T> result = JSON.parseObject(response, new TypeReference<ApiResult<T>>(responseClass) {
        });

        return result;
    }

    public static <T> ApiResult<T> post(ApiEnum api, JSONObject body, Class<T> responseClass) {

        String requestUrl = SpringUtils.getBean(BotProperties.class).getApiUrl() + api.getRequestUrl();
        // 生成 TraceId 方便排查问题
        String traceId = Ids.getId();

        String response = null;
        try {
            String jsonBody = JSON.toJSONString(body);
            HttpRequest request = HttpUtil.createPost(requestUrl).body(jsonBody, ContentType.JSON.toString());
            HttpResponse execute = request.execute();
            response = execute.body();
            log.info("调用bot服务 {},{},{},{}", traceId, requestUrl, jsonBody, response);
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


    public static ApiResult<Page<BotInfoVO>> getBot(PageDTO data) {
        return ApiClient.post(ApiEnum.GET_BOT, JSONObject.from(data), new TypeReference<Page<BotInfoVO>>(BotInfoVO.class) {
        });
    }

    public static List<BotInfoVO> getBot(Integer size) {
        PageDTO dto = new PageDTO();
        dto.setCurrentPage(1);
        dto.setPageSize(size);
        ApiResult<Page<BotInfoVO>> botResult = getBot(dto);
        if (botResult.isSuccess()) {
            return botResult.getData().getRecords();
        }

        return new ArrayList<>();
    }


    public static ApiResult<String> setBotAdMonitor(AdMonitorDTO data) {
        return ApiClient.post(ApiEnum.SET_BOT_AD_MONITOR, JSONObject.from(data), String.class);
    }


    public static ApiResult<GroupStateVO> getGroup(String chatId){
        return ApiClient.post(ApiEnum.GET_CHAT, chatId, GroupStateVO.class);
    }

}
