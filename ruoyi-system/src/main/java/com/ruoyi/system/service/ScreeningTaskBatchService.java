package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.ScreeningTask;
import com.ruoyi.system.domain.ScreeningTaskBatch;
import com.ruoyi.system.domain.vo.ScreeningBatchVO;

import java.util.List;

/**
 * 筛查任务批次表(ScreeningTaskBatch)表服务接口
 *
 * @author 朱少波
 * @since 2024-01-25 10:37:36
 */
public interface ScreeningTaskBatchService extends IService<ScreeningTaskBatch> {

    ScreeningTaskBatch save(ScreeningTask task, String batchId, String orderId );

    List<ScreeningBatchVO> batchInfo(String taskId);
}
