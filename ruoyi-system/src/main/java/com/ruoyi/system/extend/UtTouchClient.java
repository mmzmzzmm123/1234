package com.ruoyi.system.extend;

import cn.hutool.http.*;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.TypeReference;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.extend.data.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
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
public abstract class UtTouchClient {

    private static RedisTemplate<String, String> redisTemplate;

    private static JSONWriter.Feature[] features =
            new JSONWriter.Feature[]{
                    JSONWriter.Feature.WriteNullStringAsEmpty,
                    JSONWriter.Feature.WriteNullListAsEmpty,
                    JSONWriter.Feature.WriteMapNullValue
            };

    static {
        redisTemplate = SpringUtils.getBean("redisTemplate");
    }


    public static <T> UtTouchResult<T> post(UtTouchApiEnum api, JSONObject body, Class<T> responseClass) {

        String requestUrl = SpringUtils.getBean(UtTouchProperties.class).getApiUrl() + api.getRequestUrl();

        String token = null;
        if (!UtTouchApiEnum.MERCHANT_LOGIN.equals(api)) {
            token = UtTouchClient.getToken();
        }

        // 如果body中UserCode为空 填充默认的UserCode
        UtTouchClient.fillDefaultUserCode(body);

        // 生成 TraceId 方便排查问题
        String traceId = Ids.getId();

        String response = null;
        try {
            String jsonBody = JSON.toJSONString(body, "yyyy-MM-ddTHH:mm:ss", features);
            log.info("调用Ut-Touch相关接口 {} {} {} {} {}", traceId, requestUrl, jsonBody, token, api.name());
            HttpRequest request = HttpUtil.createPost(requestUrl).header("token", token).body(jsonBody, ContentType.JSON.toString());
            HttpResponse execute = request.execute();
            response = execute.body();
            log.info("调用Ut-Touch相关接口获得响应 {} {}", traceId, response);
        } catch (HttpException e) {
            log.info("调用Ut-Touch相关接口发生异常 {} {}", traceId, e.getMessage(), e);
            return UtTouchResult.failed("系统繁忙,请稍后重试" + traceId);
        }

        UtTouchResult<T> result = JSON.parseObject(response, new TypeReference<UtTouchResult<T>>(responseClass) {
        });

        // 检查结果是否正常转换
        if (result == null || result.getCode() == null) {
            UtTouchClient.catchApiException(traceId, response);
        }

        return result;

    }


    /**
     * 补偿一下 调用.Net接口各种异常 返回接口和文档接口不一致的问题
     * {
     *   "type": "https://tools.ietf.org/html/rfc7231#section-6.5.1",
     *   "title": "One or more validation errors occurred.",
     *   "status": 400,
     *   "traceId": "00-0d24cb6d0bd2a48b56c88cb5213a18c6-42711366f81222f2-00",
     *   "errors": {
     *     "UserCode": [
     *       "The UserCode field is required."
     *     ]
     *   }
     * }
     *
     * @param traceId  追踪Id
     * @param response 响应信息
     */
    private static void catchApiException(String traceId, String response) {

        JSONObject json = JSONObject.parseObject(response);
        String title = json.getString("title");
        if (StringUtils.isNotBlank(title)) {
            throw new com.ruoyi.common.exception.GlobalException("服务繁忙,请稍后再试:" + traceId);
        }

    }

    private static void fillDefaultUserCode(JSONObject body) {
        if (null == body || StringUtils.isNotBlank(body.getString("userCode"))) {
            return;
        }
        String merchantId = SpringUtils.getBean(UtTouchProperties.class).getTouchMerchantId();
        body.put("userCode", merchantId);
    }


    public static <T> UtTouchResult<T> post(UtTouchApiEnum api, JSONObject body, TypeReference<T> typeReference) {

        // 先使用 String 接收
        UtTouchResult<String> tempResult = UtTouchClient.post(api, body, String.class);

        T data = Optional.ofNullable(tempResult)
                .map(UtTouchResult::getData)
                .map(it -> JSONObject.parseObject(it, typeReference))
                .orElse(null);

        UtTouchResult<T> result = new UtTouchResult<>();
        BeanUtils.copyProperties(tempResult, result);
        result.setData(data);

        return result;
    }


    /**
     * 商家登录 - 获取商家的Token
     *
     * @param data 登录数据
     * @return
     */
    public static UtTouchResult<MerchantLoginResult> merchantLogin(MerchantLoginData data) {
        return UtTouchClient.post(UtTouchApiEnum.MERCHANT_LOGIN, JSONObject.from(data), MerchantLoginResult.class);
    }


    /**
     * 创建私聊任务
     *
     * @return
     */
    public static UtTouchResult<String> createPrivateMessageTask(CreatePrivateMessageTaskData data) {
        return UtTouchClient.post(UtTouchApiEnum.CREATE_PRIVATE_MESSAGE_TASK, JSONObject.from(data), String.class);
    }

    /**
     * @param data
     * @return
     * @deprecated deprecated By Jing.Zhang 接口数据不全(已删除)
     */
    @Deprecated
    public static UtTouchResult<UtTouchPage<PrivateMessageTask>> getPrivateMessageTaskByPage(GetPrivateMessageTaskPageData data) {
        return UtTouchClient.post(UtTouchApiEnum.GET_PRIVATE_MESSAGE_TASK_BY_PAGE,
                JSONObject.from(data),
                new TypeReference<UtTouchPage<PrivateMessageTask>>(PrivateMessageTask.class) {
                });
    }

    /**
     * https://ut-openapi.csbilin.com/api/ComPrivateMessageTaskService/GetPrivateMessageTaskPage
     *
     * @param data
     * @return
     */
    public static UtTouchResult<UtTouchPage<GetPrivateMessageTaskPageOutDTO>> getPrivateMessageTaskPage(GetPrivateMessageTaskPageData data) {
        return UtTouchClient.post(UtTouchApiEnum.GET_PRIVATE_MESSAGE_TASK_PAGE,
                JSONObject.from(data),
                new TypeReference<UtTouchPage<GetPrivateMessageTaskPageOutDTO>>(PrivateMessageTask.class) {
                });
    }

    /**
     * https://ut-openapi.csbilin.com/api/PlatformRobot/CountryBalanceSummary
     *
     * @param data
     * @return
     */
    public static UtTouchResult<GetCountryBalanceSummaryOutDTO> getCountryBalanceSummary(GetCountryBalanceSummaryInputDTO data) {
        return UtTouchClient.post(UtTouchApiEnum.COUNTRY_BALANCE_SUMMARY,
                JSONObject.from(data),
                GetCountryBalanceSummaryOutDTO.class);
    }

    /**
     * https://ut-openapi.csbilin.com/api/PlatformRobot/GetPlatformRobotPoolPage
     *
     * @param data
     * @return
     */
    public static UtTouchResult<UtTouchPage<GetPlatformRobotPoolPageOutDTO>> getPlatformRobotPoolPage(GetPlatformRobotPoolPageInputDTO data) {
        return UtTouchClient.post(UtTouchApiEnum.GET_PLATFORM_ROBOT_POOL_PAGE,
                JSONObject.from(data),
                new TypeReference<UtTouchPage<GetPlatformRobotPoolPageOutDTO>>(GetPlatformRobotPoolPageOutDTO.class) {
                });
    }

    public static UtTouchResult<String> startPrivateMessageTask(TaskOperateData data) {
        return UtTouchClient.post(UtTouchApiEnum.START_PRIVATE_MESSAGE_TASK, JSONObject.from(data), String.class);
    }

    public static UtTouchResult<UtTouchPage<TaskDetailResultData>> getPrivateMessageTaskDetail(TaskDetailQueryData data) {
        return UtTouchClient.post(UtTouchApiEnum.GET_PRIVATE_MESSAGE_TASK_DETAIL,
                JSONObject.from(data),
                new TypeReference<UtTouchPage<TaskDetailResultData>>(TaskDetailResultData.class) {
                });
    }


    /**
     * 暂停任务
     *
     * @param data
     * @return
     */
    public static UtTouchResult<String> stopPrivateMessageTask(TaskOperateData data) {
        return UtTouchClient.post(UtTouchApiEnum.STOP_PRIVATE_MESSAGE_TASK, JSONObject.from(data), String.class);
    }

    /**
     * 取消批量私信任务
     *
     * @param data
     * @return
     */
    public static UtTouchResult<String> cancelPrivateMessageTask(TaskOperateData data) {
        return UtTouchClient.post(UtTouchApiEnum.CANCEL_PRIVATE_MESSAGE_TASK, JSONObject.from(data), String.class);
    }


    /**
     * 追加目标
     *
     * @param data
     * @return
     */
    public static UtTouchResult<String> addTaskObject(AddTaskObjectData data) {
        return UtTouchClient.post(UtTouchApiEnum.ADD_TASK_OBJECT, JSONObject.from(data), String.class);
    }


    public static UtTouchResult<UtTouchPage<UtTouchMaterialGroup>> getUserMaterialsByPage(UtTouchQueryPage data) {
        return UtTouchClient.post(UtTouchApiEnum.GET_USER_MATERIALS_BY_PAGE,
                JSONObject.from(data),
                new TypeReference<UtTouchPage<UtTouchMaterialGroup>>(UtTouchMaterialGroup.class) {
                });
    }


    public static UtTouchResult<String> addOneMaterialContent(AddOneMaterialContentData data) {
        return UtTouchClient.post(UtTouchApiEnum.ADD_ONE_MATERIAL_CONTENT, JSONObject.from(data), String.class);
    }


    public static UtTouchResult<String> removeOneMaterialContent(RemoveOneMaterialContentData data) {
        return UtTouchClient.post(UtTouchApiEnum.DELETE_ONE_MATERIAL_CONTENT, JSONObject.from(data), String.class);

    }


    @Deprecated
    public static UtTouchResult<UtTouchPage<MaterialContent>> getMaterialContentsByPage(GetMaterialContentsData data) {
        return UtTouchClient.post(UtTouchApiEnum.GET_USER_MATERIAL_CONTENTS_BY_PAGE,
                JSONObject.from(data),
                new TypeReference<UtTouchPage<MaterialContent>>(MaterialContent.class) {
                });

    }


    public static UtTouchResult<String> saveMaterialGroup(SaveMaterialGroupData data) {
        return UtTouchClient.post(UtTouchApiEnum.SAVE_MATERIAL_GROUP, JSONObject.from(data), String.class);
    }


    public static UtTouchResult<String> deleteMaterialGroup(DeleteMaterialGroupData data) {
        return post(UtTouchApiEnum.DELETE_MATERIAL_GROUP, JSONObject.from(data), String.class);
    }


    public static UtTouchResult<List<String>> saveMaterialContent(SaveMaterialContentNewData data) {
        return post(UtTouchApiEnum.SAVE_MATERIAL_CONTENT,
                JSONObject.from(data),
                new TypeReference<List<String>>(String.class) {
                });
    }


    public static UtTouchResult<String> deleteMaterialContent(DeleteMaterialContentData data) {
        return post(UtTouchApiEnum.DELETE_MATERIAL_CONTENT, JSONObject.from(data), String.class);
    }


    public static UtTouchResult<UtTouchPage<MaterialContentPageResult>> getMaterialContentPage(GetMaterialContentPageData data) {
        return UtTouchClient.post(UtTouchApiEnum.GET_MATERIAL_CONTENT_PAGE,
                JSONObject.from(data),
                new TypeReference<UtTouchPage<MaterialContentPageResult>>(MaterialContentPageResult.class) {
                });
    }


    public static UtTouchResult<String> forbiddenMaterialContent(DeleteMaterialContentData data) {
        return post(UtTouchApiEnum.FORBIDDEN_MATERIAL_CONTENT, JSONObject.from(data), String.class);
    }


    public static UtTouchResult<String> deletePrivateMessageTask(DeletePrivateMessageTaskData data) {
        return post(UtTouchApiEnum.DELETE_PRIVATE_MESSAGE_TASK, JSONObject.from(data), String.class);
    }


    public static UtTouchResult<List<GetMaterialGroupResult>> getMaterialGroup(GetMaterialGroupData data) {
        return UtTouchClient.post(UtTouchApiEnum.GET_MATERIAL_GROUP,
                JSONObject.from(data),
                new TypeReference<List<GetMaterialGroupResult>>(GetMaterialGroupResult.class) {
                });
    }


    private static String login() {
        String lockKey = "ubpm-plus-login";
        RedisLock redisLock = SpringUtils.getBean(RedisLock.class);
        RLock lock = redisLock.lock(lockKey);
        try {
            if (lock.isLocked()) {
                String merchantAccount = SpringUtils.getBean(UtTouchProperties.class).getMerchantAccount();
                String merchantPassword = SpringUtils.getBean(UtTouchProperties.class).getMerchantPassword();

                MerchantLoginData data = new MerchantLoginData();
                data.setMerchantAccount(merchantAccount)
                        .setMerchantPwd(merchantPassword);
                UtTouchResult<MerchantLoginResult> result = UtTouchClient.merchantLogin(data);
                return Optional.ofNullable(result)
                        .map(UtTouchResult::getData)
                        .map(MerchantLoginResult::getToken)
                        .orElseThrow(() -> new GlobalException("调用UT-TOUCH登录发生错误"));
            } else {
                return null;
            }
        } finally {
            redisLock.unlock(lock);
        }
    }

    private static String getToken() {
        String token = redisTemplate.boundValueOps("ut-touch-token-bule").get();
        if (StringUtils.isBlank(token)) {

            // 调用接口获取Token
            token = UtTouchClient.login();

            // Redis中缓存Token
            if (StringUtils.isNotBlank(token)) {
                redisTemplate.boundValueOps("ut-touch-token-bule").set(token);
                redisTemplate.boundValueOps("ut-touch-token-bule").expire(1, TimeUnit.HOURS);
            }
        }
        return token;
    }

    /**
     * https://ut-openapi.csbilin.com/api/PlatformRobot/GetPoolCountryAreaCounts
     * 获取按照国家统计
     *
     * @param data
     * @return
     */
    public static UtTouchResult<List<GetPoolCountryAreaCountsOutDTO>> getPoolCountryAreaCounts(
            GetPoolCountryAreaCountsInputDTO data) {
        return UtTouchClient.post(UtTouchApiEnum.GET_POOL_COUNTRY_AREA_COUNTS,
                JSONObject.from(data),
                new TypeReference<List<GetPoolCountryAreaCountsOutDTO>>(GetPoolCountryAreaCountsOutDTO.class) {
                });
    }

    /**
     * https://ut-openapi.csbilin.com/api/ComPrivateMessageTaskService/AddTaskMaterialContent
     * 增加任务话术内容（默认加到第一个话术分组下）
     *
     * @param data
     * @return
     */
    public static UtTouchResult<String> addTaskMaterialContent(AddTaskMaterialContentInputDTO data) {
        return post(UtTouchApiEnum.ADD_TASK_MATERIAL_CONTENT,
                JSONObject.from(data),
                String.class);
    }

    /**
     * https://ut-openapi.csbilin.com/api/ComPrivateMessageTaskService/GetPrivateMessageTaskMaterials
     * 获取私信任务的话术统计
     *
     * @param data
     * @return
     */
    public static UtTouchResult<UtTouchPage<GetPrivateMessageTaskMaterialsOutDTO>> getPrivateMessageTaskMaterials(GetPrivateMessageTaskMaterialsInputDTO data) {
        return post(UtTouchApiEnum.GET_PRIVATE_MESSAGE_TASK_MATERIALS,
                JSONObject.from(data),
                new TypeReference<UtTouchPage<GetPrivateMessageTaskMaterialsOutDTO>>(GetPrivateMessageTaskMaterialsOutDTO.class) {
                });
    }


    public static UtTouchResult<List<UtTouchCountryData>> getAllCountries() {
        return UtTouchClient.post(UtTouchApiEnum.GET_ALL_COUNTRYS,
                new JSONObject(),
                new TypeReference<List<UtTouchCountryData>>(UtTouchCountryData.class) {
                });

    }

}
