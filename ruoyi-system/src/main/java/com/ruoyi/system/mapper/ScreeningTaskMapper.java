package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.ScreeningTask;
import com.ruoyi.system.domain.dto.ScreeningTaskDetailDTO;
import com.ruoyi.system.domain.dto.ScreeningTaskExportDTO;
import com.ruoyi.system.domain.dto.ScreeningTaskPageDTO;
import com.ruoyi.system.domain.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 筛查任务表(ScreeningTask)表数据库访问层
 *
 * @author 朱少波
 * @since 2024-01-25 10:37:23
 */
@Repository
public interface ScreeningTaskMapper extends BaseMapper<ScreeningTask> {

    Page<ScreeningTaskVO> taskPage(Page<ScreeningTaskVO> page, @Param("merchantId") String merchantId, @Param("dto") ScreeningTaskPageDTO dto);

    TaskProgressVO taskProgress(String taskId);

    List<ScreeningBatchProgressVO> batchProgress(String taskId);

    List<ScreeningTaskResultVO> taskResult(ScreeningTaskExportDTO dto);

    Page<ScreeningTaskDetailVO> page(Page page, @Param("dto") ScreeningTaskDetailDTO dto);
}
