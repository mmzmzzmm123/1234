package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.entity.ProductSku;
import com.ruoyi.system.domain.ScreeningTask;
import com.ruoyi.system.domain.dto.ScreeningTaskDetailDTO;
import com.ruoyi.system.domain.dto.ScreeningTaskExportDTO;
import com.ruoyi.system.domain.dto.ScreeningTaskPageDTO;
import com.ruoyi.system.domain.vo.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 筛查任务表(ScreeningTask)表服务接口
 *
 * @author 朱少波
 * @since 2024-01-25 10:37:23
 */
public interface ScreeningTaskService extends IService<ScreeningTask> {

    boolean checkName(String name,String merchantId);

    ScreeningTask save(String merchantId, Long userId, String taskName, String countryCode, Long productId, ProductSku productSku);


    Page<ScreeningTaskVO> taskPage(String merchantId, ScreeningTaskPageDTO dto);

   List<TaskProgressVO> taskProgress(List<String> taskId);

    TaskBatchProgressVO batchProgress(String taskId);

    List<ScreeningTaskResultVO> taskResult(ScreeningTaskExportDTO dto);

    void addTarget(ScreeningTask task);

    Page<ScreeningTaskDetailVO> taskDetail(ScreeningTaskDetailDTO dto);
}
