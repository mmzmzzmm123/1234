package com.ruoyi.common.openapi;

import cn.hutool.http.*;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.ruoyi.common.openapi.model.input.*;
import com.ruoyi.common.openapi.model.output.ExtTgBatchRobotSimpInfoData;
import com.ruoyi.common.openapi.model.output.TgBaseOutputDTO;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.common.utils.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class OpenApiClient {

    public static <T> OpenApiResult<T> post(OpenApiEnum api, JSONObject body, Class<T> responseClass) {

        String requestUrl = SpringUtils.getBean(OpenApiProperties.class).getOpenApiHost() + api.getRequestUrl();

        String token = null;
        if (!OpenApiEnum.THIRD_USER_LOGIN_CLIENT.equals(api)) {
            token = OpenApiClient.getToken();
        }

        // 生成 TraceId 方便排查问题
        String traceId = Ids.getId();

        String response = null;
        try {
            String jsonBody = JSON.toJSONString(body);
            log.info("调用openapi相关接口 {} {} {} {} {}", traceId, requestUrl, jsonBody, token, api.name());
            HttpRequest request = HttpUtil.createPost(requestUrl).header("Authorization", token).body(jsonBody, ContentType.JSON.toString());
            HttpResponse execute = request.execute();
            response = execute.body();
            log.info("调用openapi相关接口获得响应 {} {}", traceId, response);
        } catch (HttpException e) {
            log.info("调用openapi相关接口发生异常 {} {}", traceId, e.getMessage(), e);
            return OpenApiResult.failed("系统繁忙,请稍后重试" + traceId);
        }

        OpenApiResult<T> result = JSON.parseObject(response, new TypeReference<OpenApiResult<T>>(responseClass) {
        });

        return result;
    }

    public static <T> OpenApiResult<T> post(OpenApiEnum api, JSONObject body, TypeReference<T> typeReference) {

        // 先使用 String 接收
        OpenApiResult<String> tempResult = OpenApiClient.post(api, body, String.class);

        T data = Optional.ofNullable(tempResult)
                .map(OpenApiResult::getData)
                .map(it -> JSONObject.parseObject(it, typeReference))
                .orElse(null);

        OpenApiResult<T> result = new OpenApiResult<>();
        BeanUtils.copyProperties(tempResult, result);
        result.setData(data);

        return result;
    }

    private static String login() {
        //String merchantAccount = SpringUtils.getBean(UtTouchProperties.class).getMerchantAccount();
        //String merchantPassword = SpringUtils.getBean(UtTouchProperties.class).getMerchantPassword();

//        MerchantLoginData data = new MerchantLoginData();
//        data.setMerchantAccount(merchantAccount)
//                .setMerchantPwd(merchantPassword);
//        UtTouchResult<MerchantLoginResult> result = UtTouchClient.merchantLogin(data);
//        return Optional.ofNullable(result)
//                .map(UtTouchResult::getData)
//                .map(MerchantLoginResult::getToken)
//                .orElseThrow(() -> new GlobalException("调用UT-TOUCH登录发生错误"));
        return "";
    }

    private static String getToken() {
        RedisTemplate<String, String> redisTemplate = SpringUtils.getBean("redisTemplate");
        String token = redisTemplate.boundValueOps(getKey()).get();
        if (StringUtils.isBlank(token)) {

            // 调用接口获取Token
            token = OpenApiClient.login();

            // Redis中缓存Token
            if (StringUtils.isNotBlank(token)) {
                redisTemplate.boundValueOps(getKey()).set(token);
                redisTemplate.boundValueOps(getKey()).expire(1, TimeUnit.HOURS);
            }
        }
        return token;
    }

    private static String getKey() {
        return "ut-buleprint-backend:OpenApiToken";
    }


    /**
     * 比邻第三方(开平TG)：-修改群头像
     *
     * @return
     */
    public static OpenApiResult<TgBaseOutputDTO> modifyChatroomHeadImageByThirdKpTg(ThirdTgModifyChatroomHeadImageInputDTO data) {
        return OpenApiClient.post(OpenApiEnum.THIRD_KP_TG_MODIFY_CHATROOM_HEAD_IMAGE, JSONObject.from(data), TgBaseOutputDTO.class);
    }

    /**
     * 比邻第三方(开平TG)：-修改群名称
     *
     * @return
     */
    public static OpenApiResult<TgBaseOutputDTO> modifyChatroomNameByThirdKpTg(ThirdTgModifyChatroomNameInputDTO data) {
        return OpenApiClient.post(OpenApiEnum.THIRD_KP_TG_MODIFY_CHATROOM_NAME, JSONObject.from(data), TgBaseOutputDTO.class);
    }

    /**
     * 比邻第三方(开平TG)：-设置群管理员
     *
     * @return
     */
    public static OpenApiResult<TgBaseOutputDTO> setChatroomAdminByThirdKpTg(ThirdTgSetChatroomAdminInputDTO data) {
        return OpenApiClient.post(OpenApiEnum.THIRD_KP_TG_SET_CHATROOM_ADMIN, JSONObject.from(data), TgBaseOutputDTO.class);
    }

    /**
     * 比邻第三方(开平TG)：-通过群链接入群
     *
     * @return
     */
    public static OpenApiResult<TgBaseOutputDTO> joinChatroomByUrlByThirdKpTg(ThirdTgJoinChatroomByUrlInputDTO data) {
        return OpenApiClient.post(OpenApiEnum.THIRD_KP_TG_JOIN_CHATROOM_BY_URL, JSONObject.from(data), TgBaseOutputDTO.class);
    }

    /**
     * 比邻第三方(开平TG)：-退出群
     *
     * @return
     */
    public static OpenApiResult<TgBaseOutputDTO> exitChatroomByThirdKpTg(ThirdTgExitChatroomInputDTO data) {
        return OpenApiClient.post(OpenApiEnum.THIRD_KP_TG_EXIT_CHATROOML, JSONObject.from(data), TgBaseOutputDTO.class);
    }

    /**
     * 比邻第三方(开平TG)：-修改号头像
     *
     * @return
     */
    public static OpenApiResult<TgBaseOutputDTO> modifyRobotHeadImgByThirdKpTg(ThirdTgModifyRobotHeadImgInputDTO data) {
        return OpenApiClient.post(OpenApiEnum.THIRD_KP_TG_MODIFY_ROBOT_HEAD_IMG, JSONObject.from(data), TgBaseOutputDTO.class);
    }

    /**
     * 比邻第三方(开平TG)：-修改号姓氏和名字和简介
     *
     * @return
     */
    public static OpenApiResult<TgBaseOutputDTO> modifyNameByThirdKpTg(ThirdTgModifyNameInputDTO data) {
        return OpenApiClient.post(OpenApiEnum.THIRD_KP_TG_MODIFY_NAME, JSONObject.from(data), TgBaseOutputDTO.class);
    }

    /**
     * 比邻第三方(开平TG)：-设置用户名接口
     *
     * @return
     */
    public static OpenApiResult<TgBaseOutputDTO> modifyUserNameByThirdKpTg(ThirdTgModifyUserNameInputDTO data) {
        return OpenApiClient.post(OpenApiEnum.THIRD_KP_TG_MODIFY_USERNAME, JSONObject.from(data), TgBaseOutputDTO.class);
    }

    /**
     * 比邻第三方(开平TG)：-获取hw账号基础信息
     *
     * @return
     */
    public static OpenApiResult<TgBaseOutputDTO<List<ExtTgBatchRobotSimpInfoData>>> batchRobotSimpInfoByThirdKpTg(ThirdTgBatchRobotSimpInfoInputDTO data) {
        return OpenApiClient.post(OpenApiEnum.THIRD_KP_TG_BATCH_ROBOT_SIMPINFO,
                JSONObject.from(data),
                new TypeReference<TgBaseOutputDTO<List<ExtTgBatchRobotSimpInfoData>>>(ExtTgBatchRobotSimpInfoData.class) {
        });
    }

    /**
     * 比邻第三方(开平TG)：-获取群成员列表
     *
     * @return
     */
    public static OpenApiResult<TgBaseOutputDTO> searchMemberByThirdKpTg(ThirdTgSearchMemberInputDTO data) {
        return OpenApiClient.post(OpenApiEnum.THIRD_KP_TG_SEARCH_MEMBER, JSONObject.from(data), TgBaseOutputDTO.class);
    }

}
