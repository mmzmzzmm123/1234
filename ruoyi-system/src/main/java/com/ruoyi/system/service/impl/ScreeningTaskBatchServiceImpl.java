package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.ScreeningTask;
import com.ruoyi.system.domain.ScreeningTaskBatch;
import com.ruoyi.system.domain.vo.ScreeningBatchVO;
import com.ruoyi.system.mapper.ScreeningTaskBatchMapper;
import com.ruoyi.system.service.ScreeningTaskBatchService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 筛查任务批次表(ScreeningTaskBatch)表服务实现类
 *
 * @author 朱少波
 * @since 2024-01-25 10:37:36
 */
@Service("screeningTaskBatchService")
public class ScreeningTaskBatchServiceImpl extends ServiceImpl<ScreeningTaskBatchMapper, ScreeningTaskBatch> implements ScreeningTaskBatchService {

    @Override
    public ScreeningTaskBatch save(ScreeningTask task, String batchId, String orderId) {
        ScreeningTaskBatch batch = new ScreeningTaskBatch();
        batch.setBatchId(batchId);
        batch.setBatchCount(task.getBatchCount());
        batch.setTaskId(task.getTaskId());
        batch.setBatchState(0);
        batch.setCreateTime(LocalDateTime.now());
        batch.setOrderId(orderId);
        baseMapper.insert(batch);
        return batch;
    }

    @Override
    public List<ScreeningBatchVO> batchInfo(String taskId) {
        List<ScreeningTaskBatch> batchs = baseMapper.selectList(new LambdaQueryWrapper<ScreeningTaskBatch>().eq(ScreeningTaskBatch::getTaskId, taskId).orderByAsc(ScreeningTaskBatch::getBatchCount));
        if (CollUtil.isEmpty(batchs)) {
            return new ArrayList<>();
        }
        return batchs.stream().map(p -> BeanUtil.copyProperties(p, ScreeningBatchVO.class)).collect(Collectors.toList());
    }
}
