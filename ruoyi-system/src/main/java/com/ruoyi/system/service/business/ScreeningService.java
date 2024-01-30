package com.ruoyi.system.service.business;

import cc.linkmaster.telegram.search.client.TelegramSearchApiResult;
import cc.linkmaster.telegram.search.client.TelegramSearchClient;
import cc.linkmaster.telegram.search.client.input.AddTgTaskInputDTO;
import cc.linkmaster.telegram.search.client.input.GetTgTaskInputDTO;
import cc.linkmaster.telegram.search.client.input.SearchTaskInputDTO;
import cc.linkmaster.telegram.search.client.output.AccountLoginOutputDTO;
import cc.linkmaster.telegram.search.client.output.GetTgTaskOutputDTO;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.config.dymic.TelegramSearchConfigure;
import com.ruoyi.common.core.domain.app.OrderProduceRequest;
import com.ruoyi.common.core.domain.entity.Product;
import com.ruoyi.common.core.domain.entity.ProductSku;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.enums.ProductCategoryType;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.OrderTools;
import com.ruoyi.system.domain.Country;
import com.ruoyi.system.domain.ScreeningTask;
import com.ruoyi.system.domain.ScreeningTaskBatch;
import com.ruoyi.system.domain.ScreeningTaskTarget;
import com.ruoyi.system.domain.dto.TaskCallBackDTO;
import com.ruoyi.system.mapper.ProductMapper;
import com.ruoyi.system.service.CountryService;
import com.ruoyi.system.service.ScreeningTaskBatchService;
import com.ruoyi.system.service.ScreeningTaskService;
import com.ruoyi.system.service.ScreeningTaskTargetService;
import com.ruoyi.system.service.impl.OrderServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 朱少波
 * @date 2024/1/25/025 16:20
 */
@Service
@AllArgsConstructor
@Slf4j
public class ScreeningService {

    private final ProductMapper productMapper;

    private final CountryService countryService;

    private final ScreeningTaskService screeningTaskService;

    private final ScreeningTaskBatchService screeningTaskBatchService;

    private final ScreeningTaskTargetService screeningTaskTargetService;

    private final RedisLock redisLock;

    private final RedisCache redisCache;

    private final TelegramSearchClient tgtClient;

    private final TelegramSearchConfigure tgSearchConfigure;

    public String getToken() {
        String key = "TelegramSearchToken";
        String token = redisCache.getCacheObject(key);
        if (StrUtil.isNotBlank(token)) {
            return token;
        }
        try {
            TelegramSearchApiResult<AccountLoginOutputDTO> result = tgtClient.accountLogin();
            if (result.isSuccess()) {
                AccountLoginOutputDTO data = result.getData();
                token = data.getToken();
                try {
                    int expires = (int) (Duration.between(LocalDateTime.now(), LocalDateTime.parse(data.getExpireTime(), DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                            .getSeconds() - 60 * 30);

                    redisCache.setCacheObject(key, token, expires, TimeUnit.SECONDS);
                } catch (Exception ex) {
                    redisCache.setCacheObject(key, token,
                            60 * 60 * 2, TimeUnit.SECONDS);
                }
            }
        } catch (Exception e) {
            log.info("登录失败", e);
            throw new IllegalArgumentException("登录失败");
        }
        return token;
    }

    public List<String> analysisFileInfo(MultipartFile file) {
        List<String> phones = new ArrayList<>();
        try {
            InputStreamReader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader);
            String lineStr;
            while ((lineStr = br.readLine()) != null) {
                if (StrUtil.isNotBlank(lineStr)) {
                    phones.add(lineStr.trim());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return phones.stream().distinct().collect(Collectors.toList());
    }


    public void addTask(MultipartFile file, String merchantId, LoginUser loginUser, String taskName, String countryCode, Long productId) {
        Country country = countryService.getById(countryCode);
        Assert.notNull(country, "国家不存在");

        Product product = productMapper.selectById(productId);
        Assert.notNull(product, "商品不存在");
        Assert.isTrue(ObjectUtil.equal(product.getCategoryId(), ProductCategoryType.SCREENING.getId()), "商品类型异常");
        ProductSku productSku = product.getProductSku();

        List<String> phones = analysisFileInfo(file);
        Assert.notEmpty(phones, "文件内容不能为空");

        Assert.isFalse(screeningTaskService.checkName(taskName, merchantId), "任务名字重复");

        String taskBatchName = taskName + "【第" + 1 + "批】";

        String orderId = StrUtil.isBlank(merchantId) ? "" : generateOrder(loginUser, product, productSku, phones.size(), taskBatchName);

        try {
            String taskNo = requestAddTasks(country, phones, taskBatchName);
            ScreeningTask task = screeningTaskService.save(merchantId, loginUser.getUserId(), taskName, countryCode, productId, productSku);
            screeningTaskBatchService.save(task, taskNo, orderId);
            screeningTaskTargetService.save(taskNo, phones);
        } catch (Exception e) {
            OrderTools.taskGenerationFailed(orderId);
            throw e;
        }
    }

    private static String generateOrder(LoginUser loginUser, Product product, ProductSku productSku, Integer count, String taskBatchName) {
        OrderProduceRequest request = new OrderProduceRequest();
        request.setTaskName(taskBatchName);
        request.setProductId(product.getProductId());
        request.setParams(new OrderProduceRequest.Params());
        request.setLoginUser(loginUser);
        String orderId = SpringUtils.getBean(OrderServiceImpl.class).orderStorage(request, product, Collections.singletonList(productSku), null, productSku.getPrice() * count, 0).getOrderId();
        return orderId;
    }

    private String requestAddTasks(Country country, List<String> phones, String taskBatchName) {
        String taskNo = "";
        AddTgTaskInputDTO input = new AddTgTaskInputDTO();
        try {
            input.setNTimestamp(Instant.now().getEpochSecond() + "");
            input.setTaskName(taskBatchName);
            input.setTaskUser(tgSearchConfigure.getUsername());
            input.setTaskType(1);
            input.setCallbackUrl(tgSearchConfigure.getCallBackUrl());
            input.setPhones(phones.stream().map(phone -> {
                AddTgTaskInputDTO.PhoneData phoneData = new AddTgTaskInputDTO.PhoneData();
                phoneData.setPhone(phone);
                phoneData.setPhoneCountry(country.getPhoneArea());
                return phoneData;
            }).collect(Collectors.toList()));
            TelegramSearchApiResult<String> result = tgtClient.addTgTask(input, getToken());
            log.info("请求生成筛查任务={},{}", JSON.toJSONString(input), JSON.toJSONString(result));
            if (result.isSuccess()) {
                taskNo = result.getData();
            }
        } catch (Exception e) {
            log.info("生成筛查任务失败={}", JSON.toJSONString(input), e);
        }
        Assert.notEmpty(taskNo, "生成筛查任务失败");
        return taskNo;
    }

    private boolean requestStopTasks(String taskNo) {
        SearchTaskInputDTO input = new SearchTaskInputDTO();
        try {
            input.setNTimestamp(Instant.now().getEpochSecond() + "");
            input.setTaskNo(taskNo);
            input.setMemo("");
            TelegramSearchApiResult<String> result = tgtClient.pauseTgTask(input, getToken());
            log.info("请求停止筛查任务={},{}", JSON.toJSONString(input), JSON.toJSONString(result));
            if (result.isSuccess()) {
                return true;
            }
        } catch (Exception e) {
            log.info("请求停止筛查任务失败={}", JSON.toJSONString(input));
        }
        return false;
    }

    private boolean requestRestartTasks(String taskNo) {
        SearchTaskInputDTO input = new SearchTaskInputDTO();
        try {
            input.setNTimestamp(Instant.now().getEpochSecond() + "");
            input.setTaskNo(taskNo);
            input.setMemo("");
            TelegramSearchApiResult<String> result = tgtClient.startTgTask(input, getToken());
            log.info("请求开启筛查任务={},{}", JSON.toJSONString(input), JSON.toJSONString(result));
            if (result.isSuccess()) {
                return true;
            }
        } catch (Exception e) {
            log.info("请求开启筛查任务失败={}", JSON.toJSONString(input));
        }
        return false;
    }

    private boolean requestCancelTasks(String taskNo) {
        SearchTaskInputDTO input = new SearchTaskInputDTO();
        try {
            input.setNTimestamp(Instant.now().getEpochSecond() + "");
            input.setTaskNo(taskNo);
            input.setMemo("");
            TelegramSearchApiResult<String> result = tgtClient.stopTgTask(input, getToken());
            log.info("请求停止筛查任务={},{}", JSON.toJSONString(input), JSON.toJSONString(result));
            if (result.isSuccess()) {
                return true;
            }
        } catch (Exception e) {
            log.info("请求停止筛查任务失败={}", JSON.toJSONString(input));
        }
        return false;
    }


    public void addTask(MultipartFile file, String taskId, LoginUser loginUser) {
        ScreeningTask task = screeningTaskService.getById(taskId);
        Assert.notNull(task, "任务不存在");
        Assert.isTrue(ObjectUtil.notEqual(task.getTaskState(), 4), "任务已取消");
        Assert.isTrue(ObjectUtil.notEqual(task.getTaskState(), 5), "任务已完成");
        task.setBatchCount(task.getBatchCount() + 1);

        List<String> phones = analysisFileInfo(file);
        Assert.notEmpty(phones, "文件内容不能为空");

        Product product = productMapper.selectById(task.getProductId());
        Assert.notNull(product, "商品不存在");
        ProductSku productSku = product.getProductSku();

        String taskBatchName = task.getTaskName() + "【第" + task.getBatchCount() + "批】";

        String orderId = StrUtil.isBlank(task.getMerchantId()) ? "" : generateOrder(loginUser, product, productSku, phones.size(), taskBatchName);

        try {
            Country country = countryService.getById(task.getCountryCode());
            String taskNo = requestAddTasks(country, phones, taskBatchName);
            screeningTaskService.addTarget(task);
            screeningTaskBatchService.save(task, taskNo, orderId);
            screeningTaskTargetService.save(taskNo, phones);
        } catch (Exception e) {
            OrderTools.taskGenerationFailed(orderId);
            throw e;
        }

    }


    public void callback(TaskCallBackDTO dto) {
        if (dto == null) {
            return;
        }
        ScreeningTaskTarget screeningTaskTarget = screeningTaskTargetService.updateResult(dto);
        if (screeningTaskTarget == null) {
            return;
        }
        ScreeningTaskBatch screeningTaskBatch = screeningTaskBatchService.getById(screeningTaskTarget.getBatchId());
        if (ObjectUtil.equal(screeningTaskBatch.getBatchState(), 0)) {
            screeningTaskBatchService.updateBatchStatus(screeningTaskTarget.getBatchId(), 1, "");
        }
        if (screeningTaskTargetService.countUnchecked(screeningTaskTarget.getBatchId()) <= 0) {
            screeningTaskBatchService.updateBatchStatus(screeningTaskTarget.getBatchId(), 4, "");
            if (screeningTaskBatchService.countRunning(screeningTaskBatch.getTaskId()) <= 0) {
                screeningTaskService.updateBatchStatus(screeningTaskBatch.getTaskId(), 5);
            }
        } else {
            ScreeningTask task = screeningTaskService.getById(screeningTaskBatch.getTaskId());
            if (ObjectUtil.equal(task.getTaskState(), 0)) {
                screeningTaskService.updateBatchStatus(screeningTaskBatch.getTaskId(), 1);
            }
        }
    }

    public void cancel(List<String> taskIds) {
        List<ScreeningTask> tasks = screeningTaskService.listByIds(taskIds);
        Assert.notEmpty(tasks, "任务不存在");
        tasks = tasks.stream().filter(p -> ObjectUtil.notEqual(p.getTaskState(), 4) && ObjectUtil.notEqual(p.getTaskState(), 5)).collect(Collectors.toList());
        Assert.notEmpty(tasks, "没有可取消的任务");
        tasks.forEach(this::cancel);
    }

    public boolean cancel(ScreeningTask task) {
        RLock rLock = redisLock.getRLock("screeningTask:" + task.getTaskId());
        if (rLock.isLocked()) {
            return false;
        }
        rLock.lock(60, TimeUnit.SECONDS);
        try {
            List<ScreeningTaskBatch> screeningTaskBatches = screeningTaskBatchService.listRunning(task.getTaskId());
            if (CollUtil.isEmpty(screeningTaskBatches)) {
                return false;
            }
            boolean allOk = true;
            for (ScreeningTaskBatch screeningTaskBatch : screeningTaskBatches) {
                boolean success = requestCancelTasks(screeningTaskBatch.getBatchId());
                if (success) {
                    screeningTaskBatchService.updateBatchStatus(screeningTaskBatch.getBatchId(), 3, "界面请求取消");
                } else {
                    allOk = false;
                }
            }
            if (allOk) {
                screeningTaskService.updateBatchStatus(task.getTaskId(), 4);
            }
            return true;
        } catch (Exception e) {
            log.info("ScreeningService.cancel.error={}", task, e);
            return false;
        } finally {
            rLock.unlock();
        }
    }

    public void restart(List<String> taskIds) {
        List<ScreeningTask> tasks = screeningTaskService.listByIds(taskIds);
        Assert.notEmpty(tasks, "任务不存在");
        tasks = tasks.stream().filter(p -> ObjectUtil.notEqual(p.getTaskState(), 4) && ObjectUtil.notEqual(p.getTaskState(), 5)).collect(Collectors.toList());
        Assert.notEmpty(tasks, "没有可重启的任务");
        tasks.forEach(this::restart);
    }

    public boolean restart(ScreeningTask task) {
        RLock rLock = redisLock.getRLock("screeningTask:" + task.getTaskId());
        if (rLock.isLocked()) {
            return false;
        }
        rLock.lock(60, TimeUnit.SECONDS);
        try {
            List<ScreeningTaskBatch> screeningTaskBatches = screeningTaskBatchService.listRunning(task.getTaskId());
            if (CollUtil.isEmpty(screeningTaskBatches)) {
                return false;
            }
            boolean allOk = false;
            for (ScreeningTaskBatch screeningTaskBatch : screeningTaskBatches) {
                if (ObjectUtil.notEqual(screeningTaskBatch.getBatchState(), 2)) {
                    continue;
                }

                boolean success = requestRestartTasks(screeningTaskBatch.getBatchId());
                if (success) {
                    screeningTaskBatchService.updateBatchStatus(screeningTaskBatch.getBatchId(), 1, "");
                    allOk = true;
                }
            }
            if (allOk) {
                screeningTaskService.updateBatchStatus(task.getTaskId(), 1);
            }

            return true;
        } catch (Exception e) {
            log.info("ScreeningService.cancel.error={}", task, e);
            return false;
        } finally {
            rLock.unlock();
        }
    }

    public void stop(List<String> taskIds) {
        List<ScreeningTask> tasks = screeningTaskService.listByIds(taskIds);
        Assert.notEmpty(tasks, "任务不存在");
        tasks = tasks.stream().filter(p -> ObjectUtil.equal(p.getTaskState(), 0) || ObjectUtil.equal(p.getTaskState(), 1)).collect(Collectors.toList());
        Assert.notEmpty(tasks, "没有可暂停的任务");
        tasks.forEach(this::stop);
    }

    public boolean stop(ScreeningTask task) {
        RLock rLock = redisLock.getRLock("screeningTask:" + task.getTaskId());
        if (rLock.isLocked()) {
            return false;
        }
        rLock.lock(60, TimeUnit.SECONDS);
        try {
            List<ScreeningTaskBatch> screeningTaskBatches = screeningTaskBatchService.listRunning(task.getTaskId());
            if (CollUtil.isEmpty(screeningTaskBatches)) {
                return false;
            }
            boolean allOk = true;
            for (ScreeningTaskBatch screeningTaskBatch : screeningTaskBatches) {
                boolean success = requestStopTasks(screeningTaskBatch.getBatchId());
                if (success) {
                    screeningTaskBatchService.updateBatchStatus(screeningTaskBatch.getBatchId(), 2, "界面请求暂停");
                } else {
                    allOk = false;
                }
            }
            if (allOk) {
                screeningTaskService.updateBatchStatus(task.getTaskId(), 3);
            }
            return true;
        } catch (Exception e) {
            log.info("ScreeningService.cancel.error={}", task, e);
            return false;
        } finally {
            rLock.unlock();
        }
    }

    public void refreshTaskStatus() {
        List<ScreeningTask> runningTasks = screeningTaskService.getRunningTasks();
        if (CollUtil.isEmpty(runningTasks)) {
            return;
        }
        for (ScreeningTask runningTask : runningTasks) {
            refreshTaskStatus(runningTask);
        }
    }


    public void refreshTaskStatus(ScreeningTask task) {
        GetTgTaskInputDTO input = new GetTgTaskInputDTO();
        List<ScreeningTaskBatch> screeningTaskBatches = screeningTaskBatchService.listRunning(task.getTaskId());
        if (CollUtil.isEmpty(screeningTaskBatches)) {
            return;
        }
        int bathStatus = 99;

        for (ScreeningTaskBatch screeningTaskBatch : screeningTaskBatches) {
            try {
                input.setTaskNo(screeningTaskBatch.getBatchId());
                input.setNTimestamp(Instant.now().getEpochSecond() + "");
                TelegramSearchApiResult<GetTgTaskOutputDTO> result = tgtClient.getTgTask(input, getToken());
                log.info("同步筛查任务={},{}", JSON.toJSONString(input), JSON.toJSONString(result));
                if (result.isSuccess()) {
                    GetTgTaskOutputDTO data = result.getData();
                    if (data == null || data.getStatus() == null) {
                        return;
                    }

                    if (ObjectUtil.notEqual(data.getStatus(), screeningTaskBatch.getBatchState())) {
                        screeningTaskBatchService.updateBatchStatus(screeningTaskBatch.getBatchId(), data.getStatus(), data.getMemo());
                    }
                    if (data.getStatus() != 0 && bathStatus > data.getStatus()) {
                        bathStatus = data.getStatus();
                    }
                }
            } catch (Exception ignored) {

            }
        }

        if (bathStatus != 99 && ObjectUtil.notEqual(task.getTaskState(), bathStatus >= 2 ? bathStatus + 1 : bathStatus)) {
            screeningTaskService.updateBatchStatus(task.getTaskId(), bathStatus >= 2 ? bathStatus + 1 : bathStatus);
        }
    }
}
