package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.ProductSku;
import com.ruoyi.system.domain.ScreeningTask;
import com.ruoyi.system.domain.dto.ScreeningTaskDetailDTO;
import com.ruoyi.system.domain.dto.ScreeningTaskDetailExportDTO;
import com.ruoyi.system.domain.dto.ScreeningTaskExportDTO;
import com.ruoyi.system.domain.dto.ScreeningTaskPageDTO;
import com.ruoyi.system.domain.vo.*;
import com.ruoyi.system.mapper.ScreeningTaskMapper;
import com.ruoyi.system.service.ScreeningTaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 筛查任务表(ScreeningTask)表服务实现类
 *
 * @author 朱少波
 * @since 2024-01-25 10:37:23
 */
@Service("screeningTaskService")
public class ScreeningTaskServiceImpl extends ServiceImpl<ScreeningTaskMapper, ScreeningTask> implements ScreeningTaskService {

    @Override
    public boolean checkName(String name, String merchantId) {
        return baseMapper.selectCount(new LambdaQueryWrapper<ScreeningTask>().eq(ScreeningTask::getTaskName, name)
                .eq(ScreeningTask::getMerchantId, merchantId)) > 0;
    }

    @Override
    public ScreeningTask save(String merchantId, Long userId, String taskName, String countryCode, Long productId, ProductSku productSku) {
        ScreeningTask task = new ScreeningTask();
        task.setTaskId(IdWorker.getIdStr());
        task.setTaskName(taskName);
        task.setTaskState(0);
        task.setBatchCount(1);
        task.setCreateTime(LocalDateTime.now());
        task.setUpdateTime(LocalDateTime.now());
        task.setCompletionTime(null);
        task.setMerchantId(merchantId);
        task.setUserId(userId);
        task.setProductId(productId);
        task.setPrice(productSku.getPrice());
        task.setCountryCode(countryCode);
        baseMapper.insert(task);
        return task;
    }

    @Override
    public Page<ScreeningTaskVO> taskPage(String merchantId, ScreeningTaskPageDTO dto) {
        Page<ScreeningTaskVO> screeningTaskVOPage = baseMapper.taskPage(new Page<ScreeningTaskVO>(dto.getPage(), dto.getLimit()), merchantId, dto);
        if (CollUtil.isNotEmpty(screeningTaskVOPage.getRecords())) {
            List<String> taskIds = screeningTaskVOPage.getRecords().stream().map(ScreeningTaskVO::getTaskId).collect(Collectors.toList());
            List<TaskProgressVO> taskProgress = taskProgress(taskIds);
            if (CollUtil.isNotEmpty(taskProgress)) {
                Map<String, TaskProgressVO> map = taskProgress.stream().collect(Collectors.toMap(TaskProgressVO::getTaskId, p -> p));
                for (ScreeningTaskVO record : screeningTaskVOPage.getRecords()) {
                    if (map.containsKey(record.getTaskId())) {
                        TaskProgressVO taskProgressVO = map.get(record.getTaskId());
                        record.setScreeningCount(taskProgressVO.getScreeningCount());
                        record.setTargetCount(taskProgressVO.getTargetCount());
                        record.setScreeningRadio(taskProgressVO.getScreeningRadio());
                    }
                }
            }
        }
        return screeningTaskVOPage;
    }

    @Override
    public List<TaskProgressVO> taskProgress(List<String> taskIds) {
        return baseMapper.taskProgress(taskIds).stream().peek(TaskProgressVO::calculateRatio).collect(Collectors.toList());
    }

    @Override
    public TaskBatchProgressVO batchProgress(String taskId) {
        TaskBatchProgressVO vo = new TaskBatchProgressVO();
        List<ScreeningBatchProgressVO> total = baseMapper.batchProgress(taskId);
        if (CollUtil.isNotEmpty(total)) {
            vo.setTotalList(total.stream().peek(ScreeningBatchProgressVO::calculateRatio).collect(Collectors.toList()));
            vo.setWaitList(vo.getTotalList().stream().filter(p -> ObjectUtil.equal(p.getBatchState(), 0)).collect(Collectors.toList()));
            vo.setScreeningList(vo.getTotalList().stream().filter(p -> ObjectUtil.equal(p.getBatchState(), 1)).collect(Collectors.toList()));
            vo.setStopList(vo.getTotalList().stream().filter(p -> ObjectUtil.equal(p.getBatchState(), 2)).collect(Collectors.toList()));
            vo.setCompleteList(vo.getTotalList().stream().filter(p -> ObjectUtil.equal(p.getBatchState(), 4)).collect(Collectors.toList()));
            vo.setCancelList(vo.getTotalList().stream().filter(p -> ObjectUtil.equal(p.getBatchState(), 3)).collect(Collectors.toList()));
        }
        return vo;
    }

    @Override
    public List<ScreeningTaskResultVO> taskResult(ScreeningTaskExportDTO dto) {
        return baseMapper.taskResult(dto);
    }

    @Override
    public void addTarget(ScreeningTask task) {
        task.setBatchCount(task.getBatchCount() + 1);

        ScreeningTask update = new ScreeningTask();
        update.setTaskId(task.getTaskId());
        update.setUpdateTime(LocalDateTime.now());
        update.setBatchCount(task.getBatchCount());
        baseMapper.updateById(update);
    }

    @Override
    public Page<ScreeningTaskDetailVO> taskDetail(ScreeningTaskDetailDTO dto) {
        Page page = new Page<>(dto.getPage(), dto.getLimit());
        if (dto instanceof ScreeningTaskDetailExportDTO) {
            page.setSearchCount(false);
        }
        return baseMapper.page(page, dto);
    }


}
