package com.ruoyi.system.service;

import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.vo.BatchProcessVo;
import com.ruoyi.system.domain.vo.BatchVo;
import com.ruoyi.system.domain.vo.RecordQueryVo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: WangJinpeng
 * Date: 2022/8/6
 * Time: 20:32
 */
public interface IBatchExecuteService {

    void addAndUpdateBatch(BatchVo batchVo);

    List<BatchVo> getReactionBatchList(String batchNo);

    WorkFeedingRecord getWorkFeedingRecord(RecordQueryVo recordQueryVo);

    WorkHeatingRecord getWorkHeatingRecord(RecordQueryVo recordQueryVo);

    WorkExtractionRecord getWorkExtractionRecord(RecordQueryVo recordQueryVo);

    WorkConcentrateRecord getWorkConcentrateRecord(RecordQueryVo recordQueryVo);

    WorkGelRecord getWorkGelRecord(RecordQueryVo recordQueryVo);

    void updateWorkFeedingRecord(WorkFeedingRecord workFeedingRecord);

    void updateWorkHeatingRecord(WorkHeatingRecord workHeatingRecord);

    void updateWorkExtractionRecord(WorkExtractionRecord workExtractionRecord);

    void updateWorkConcentrateRecord(WorkConcentrateRecord workConcentrateRecord);

    void updateWorkGelRecord(WorkGelRecord workGelRecord);

    List<BatchProcessVo> getBatchProcessList(RecordQueryVo recordQueryVo);

    void buttonSampling(RecordQueryVo recordQueryVo);

    void buttonDilution(RecordQueryVo recordQueryVo);

    void buttonExit(RecordQueryVo recordQueryVo);

    void buttonFinish(RecordQueryVo recordQueryVo);

}
