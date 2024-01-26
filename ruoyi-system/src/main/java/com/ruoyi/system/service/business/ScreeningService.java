package com.ruoyi.system.service.business;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.ruoyi.common.core.domain.entity.Product;
import com.ruoyi.common.core.domain.entity.ProductSku;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.enums.ProductCategoryType;
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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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


    public void addTask(MultipartFile file, String merchantId, Long userId, String taskName, String countryCode, Long productId) {
        Country country = countryService.getById(countryCode);
        Assert.notNull(country, "国家不存在");

        Product product = productMapper.selectById(productId);
        Assert.notNull(product, "商品不存在");
        Assert.isTrue(ObjectUtil.equal(product.getCategoryId(), ProductCategoryType.SCREENING.getId()), "商品类型异常");
        ProductSku productSku = product.getProductSku();

        List<String> phones = analysisFileInfo(file);
        Assert.notEmpty(phones, "文件内容不能为空");

        Assert.isFalse(screeningTaskService.checkName(taskName, merchantId), "任务名字重复");

        //todo 冻结订单
        String orderId = "";
        //todo 调用外部接口
        String taskNo = IdWorker.getIdStr();

        ScreeningTask task = screeningTaskService.save(merchantId, userId, taskName, countryCode, productId, productSku);
        screeningTaskBatchService.save(task, taskNo, orderId);
        screeningTaskTargetService.save(taskNo, phones);
    }


    public void addTask(MultipartFile file, String taskId) {
        ScreeningTask task = screeningTaskService.getById(taskId);
        Assert.notNull(task, "任务不存在");
        Assert.isTrue(ObjectUtil.notEqual(task.getTaskState(), 4), "任务已取消");
        Assert.isTrue(ObjectUtil.notEqual(task.getTaskState(), 5), "任务已完成");

        List<String> phones = analysisFileInfo(file);
        Assert.notEmpty(phones, "文件内容不能为空");

        Product product = productMapper.selectById(task.getProductId());
        Assert.notNull(product, "商品不存在");
        screeningTaskService.addTarget(task);
        //todo 冻结订单
        String orderId = "";
        //todo 调用外部接口
        String taskNo = IdWorker.getIdStr();
        screeningTaskBatchService.save(task, taskNo, orderId);
        screeningTaskTargetService.save(taskNo, phones);

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
            screeningTaskBatchService.updateBatchStatus(screeningTaskTarget.getBatchId(), 1);
        }
        if (screeningTaskTargetService.countUnchecked(screeningTaskTarget.getBatchId()) <= 0) {
            screeningTaskBatchService.updateBatchStatus(screeningTaskTarget.getBatchId(), 4);
            if (screeningTaskBatchService.countRunning(screeningTaskBatch.getTaskId()) <= 0) {
                screeningTaskService.updateBatchStatus(screeningTaskBatch.getTaskId(), 5);
            }
        }
    }

    public void cancel(List<String> taskIds) {
        List<ScreeningTask> tasks = screeningTaskService.listByIds(taskIds);
        Assert.notEmpty(tasks, "任务不存在");
        tasks = tasks.stream().filter(p -> ObjectUtil.notEqual(p.getTaskState(), 4) && ObjectUtil.notEqual(p.getTaskState(), 5)).collect(Collectors.toList());
        Assert.notEmpty(tasks, "没有可取消的任务");
        String lockKey = "screeningTask:%s";
        tasks = tasks.stream().filter(p -> redisLock.tryLock(String.format(lockKey, p.getTaskId()), 30, TimeUnit.SECONDS)).collect(Collectors.toList());
        Assert.notEmpty(tasks, "任务正在变动中,请稍后再操作");
        try {

        } finally {
            tasks.forEach(p -> redisLock.unlock(String.format(lockKey, p.getTaskId())));
        }
    }

    public boolean cancel(ScreeningTask task) {
        RLock rLock = redisLock.getRLock("screeningTask:" + task.getTaskId());
        if (rLock.isLocked()) {
            return false;
        }
        rLock.lock(60, TimeUnit.SECONDS);
        try {


            return true;
        } catch (Exception e) {
            log.info("ScreeningService.cancel.error={}", task, e);
            return false;
        } finally {
            rLock.unlock();
        }
    }

    public void restart(List<String> taskIds) {
    }

    public void stop(List<String> taskIds) {
    }
}
