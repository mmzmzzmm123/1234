package com.ruoyi.system.service.business;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.ruoyi.common.core.domain.entity.Product;
import com.ruoyi.common.core.domain.entity.ProductSku;
import com.ruoyi.common.enums.ProductCategoryType;
import com.ruoyi.system.domain.Country;
import com.ruoyi.system.domain.ScreeningTask;
import com.ruoyi.system.domain.ScreeningTaskBatch;
import com.ruoyi.system.mapper.ProductMapper;
import com.ruoyi.system.service.CountryService;
import com.ruoyi.system.service.ScreeningTaskBatchService;
import com.ruoyi.system.service.ScreeningTaskService;
import com.ruoyi.system.service.ScreeningTaskTargetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
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
        Assert.isTrue(ObjectUtil.notEqual(task.getTaskState(),4), "任务已取消");
        Assert.isTrue(ObjectUtil.notEqual(task.getTaskState(),5), "任务已完成");

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
}
