package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.ScreeningTaskTarget;
import com.ruoyi.system.mapper.ScreeningTaskTargetMapper;
import com.ruoyi.system.service.ScreeningTaskTargetService;
import org.springframework.stereotype.Service;

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
}
