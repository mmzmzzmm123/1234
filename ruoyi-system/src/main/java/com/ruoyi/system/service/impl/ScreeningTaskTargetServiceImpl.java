package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.ScreeningTaskTarget;
import com.ruoyi.system.domain.dto.TaskCallBackDTO;
import com.ruoyi.system.mapper.ScreeningTaskTargetMapper;
import com.ruoyi.system.service.ScreeningTaskTargetService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 筛查任务筛查目标表(ScreeningTaskTarget)表服务实现类
 *
 * @author 朱少波
 * @since 2024-01-25 10:39:26
 */
@Service("screeningTaskTargetService")
public class ScreeningTaskTargetServiceImpl extends ServiceImpl<ScreeningTaskTargetMapper, ScreeningTaskTarget> implements ScreeningTaskTargetService {

    @Override
    public void save(String batchId, List<String> phones) {
        saveBatch(phones.stream().map(phone -> {
            ScreeningTaskTarget target = new ScreeningTaskTarget();
            target.setId(batchId + phone);
            target.setTarget(phone);
            target.setBatchId(batchId);
            target.setResult(0);
            return target;
        }).collect(Collectors.toList()));
    }

    @Override
    public ScreeningTaskTarget updateResult(TaskCallBackDTO dto) {
        ScreeningTaskTarget screeningTaskTarget = baseMapper.selectOne(
                new LambdaQueryWrapper<ScreeningTaskTarget>()
                        .eq(ScreeningTaskTarget::getTarget, dto.getPhone())
                        .eq(ScreeningTaskTarget::getBatchId, dto.getTaskNo())
                        .last(" limit 1"));
        if (screeningTaskTarget != null) {
            ScreeningTaskTarget update = new ScreeningTaskTarget();
            update.setId(screeningTaskTarget.getId());
            update.setResult(dto.getStatus());
            update.setUserName(dto.getUserName());
            update.setCallbackTime(LocalDateTime.now());
            baseMapper.updateById(update);
        }
        return screeningTaskTarget;
    }

    @Override
    public long countUnchecked(String batchId) {
        return  baseMapper.selectCount(
                new LambdaQueryWrapper<ScreeningTaskTarget>()
                        .notIn(ScreeningTaskTarget::getResult,1,2)
                        .eq(ScreeningTaskTarget::getBatchId, batchId));
    }
}
