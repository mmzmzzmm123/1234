package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.ScreeningTaskTarget;
import com.ruoyi.system.domain.dto.TaskCallBackDTO;

import java.util.List;

/**
 * 筛查任务筛查目标表(ScreeningTaskTarget)表服务接口
 *
 * @author 朱少波
 * @since 2024-01-25 10:39:26
 */
public interface ScreeningTaskTargetService extends IService<ScreeningTaskTarget> {

    void save(String batchId, List<String> phones);

    ScreeningTaskTarget updateResult(TaskCallBackDTO dto);

    long countUnchecked(String batchId);

    long countCheck(String batchId);
}
