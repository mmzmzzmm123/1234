package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.vo.*;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.IBatchExecuteService;
import com.ruoyi.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * Author: WangJinpeng
 * Date: 2022/8/6
 * Time: 20:32
 */
@Service
public class BatchExecuteServiceImpl implements IBatchExecuteService{

    @Autowired
    private WorkReactionBatchMapper workReactionBatchMapper;
    @Autowired
    private WorkFeedingRecordMapper workFeedingRecordMapper;
    @Autowired
    private WorkHeatingRecordMapper workHeatingRecordMapper;
    @Autowired
    private WorkConcentrateRecordMapper workConcentrateRecordMapper;
    @Autowired
    private WorkExtractionRecordMapper workExtractionRecordMapper;
    @Autowired
    private WorkGelRecordMapper workGelRecordMapper;

    @Override
    @Transactional
    public void addAndUpdateBatch(BatchVo batchVo) {
        //id为空是新增；不为空是编辑
        WorkReactionBatch workReactionBatch = new WorkReactionBatch();
        if (StringUtils.isNull(batchVo.getBatchId())) {
            String batchNo = DateUtils.dateTimeNow();
            BeanUtils.copyBeanProp(workReactionBatch, batchVo);
            workReactionBatch.setBatchNo(batchNo);
            workReactionBatch.setStatus(1L);
        } else {
            workReactionBatchMapper.deleteWorkReactionBatchByBatchNo(batchVo.getBatchNo());
            workConcentrateRecordMapper.deleteWorkConcentrateRecordByBatchNo(batchVo.getBatchNo());
            workFeedingRecordMapper.deleteWorkFeedingRecordByBatchNo(batchVo.getBatchNo());
            workExtractionRecordMapper.deleteWorkExtractionRecordByBatchNo(batchVo.getBatchNo());
            workHeatingRecordMapper.deleteWorkHeatingRecordByBatchNo(batchVo.getBatchNo());
            workGelRecordMapper.deleteWorkGelRecordByBatchNo(batchVo.getBatchNo());
            BeanUtils.copyBeanProp(workReactionBatch, batchVo);
            workReactionBatch.setStatus(1L);
        }
        for (BatchReactionVo batchReactionVo : batchVo.getBatchReactionVos()){
            workReactionBatch.setReactionSort(batchReactionVo.getReactionSort());
            workReactionBatch.setReactionCode(batchReactionVo.getReactionVo().getReactionCode());
            for (WorkReactionDetail workReactionDetail : batchReactionVo.getReactionVo().getWorkReactionDetails()){
                workReactionBatch.setSort(workReactionDetail.getSort());
                workReactionBatch.setProcessName(workReactionDetail.getProcessName());
                workReactionBatch.setProcessCode(workReactionDetail.getProcessCode());
                workReactionBatch.setPrecondition(workReactionDetail.getPrecondition());
                workReactionBatch.setParam(workReactionDetail.getParam());
                workReactionBatch.setEndif(workReactionDetail.getEndif());
                workReactionBatchMapper.insertWorkReactionBatch(workReactionBatch);
            }
            //过程记录
            WorkConcentrateRecord workConcentrateRecord = new WorkConcentrateRecord();
            BeanUtils.copyBeanProp(workConcentrateRecord, batchReactionVo.getReactionVo().getWorkConcentrate());
            workConcentrateRecord.setBatchNo(workReactionBatch.getBatchNo());
            workConcentrateRecord.setType(1L);
            workConcentrateRecordMapper.insertWorkConcentrateRecord(workConcentrateRecord);

            WorkHeatingRecord workHeatingRecord = new WorkHeatingRecord();
            BeanUtils.copyBeanProp(workHeatingRecord, batchReactionVo.getReactionVo().getWorkHeating());
            workHeatingRecord.setBatchNo(workReactionBatch.getBatchNo());
            workHeatingRecord.setType(1L);
            workHeatingRecordMapper.insertWorkHeatingRecord(workHeatingRecord);

            WorkExtractionRecord workExtractionRecord = new WorkExtractionRecord();
            BeanUtils.copyBeanProp(workExtractionRecord, batchReactionVo.getReactionVo().getWorkExtraction());
            workExtractionRecord.setBatchNo(workReactionBatch.getBatchNo());
            workExtractionRecord.setType(1L);
            workExtractionRecordMapper.insertWorkExtractionRecord(workExtractionRecord);

            WorkFeedingRecord workFeedingRecord = new WorkFeedingRecord();
            BeanUtils.copyBeanProp(workFeedingRecord, batchReactionVo.getReactionVo().getWorkFeeding());
            workFeedingRecord.setBatchNo(workReactionBatch.getBatchNo());
            workFeedingRecord.setType(1L);
            workFeedingRecordMapper.insertWorkFeedingRecord(workFeedingRecord);

            WorkGelRecord workGelRecord = new WorkGelRecord();
            BeanUtils.copyBeanProp(workGelRecord, batchReactionVo.getReactionVo().getWorkGel());
            workGelRecord.setBatchNo(workReactionBatch.getBatchNo());
            workGelRecord.setType(1L);
            workGelRecordMapper.insertWorkGelRecord(workGelRecord);
        }
    }

    @Override
    public List<BatchVo> getReactionBatchList(String batchNo) {
        List<BatchVo> batchVos = new ArrayList<>();
        WorkReactionBatch workReactionBatchQuery = new WorkReactionBatch();
        workReactionBatchQuery.setBatchNo(batchNo);
        List<WorkReactionBatch> workReactionBatches = workReactionBatchMapper.selectWorkReactionBatchList(workReactionBatchQuery);
        if (!CollectionUtils.isEmpty(workReactionBatches)){
            //先按批次分组，再按反应分组
            Map<String, Map<String, List<WorkReactionBatch>>> workReactionBatchMap = workReactionBatches.stream()
                    .collect(Collectors.groupingBy(WorkReactionBatch::getBatchNo, Collectors.groupingBy(WorkReactionBatch::getReactionCode)));

            for (Map.Entry<String, Map<String, List<WorkReactionBatch>>> entry : workReactionBatchMap.entrySet()){
                BatchVo batchVo = new BatchVo();
                List<BatchReactionVo> batchReactionVos = new ArrayList<>();
                for (Map.Entry<String, List<WorkReactionBatch>> mapEntry : entry.getValue().entrySet()){
                    batchVo.setBatchId(mapEntry.getValue().get(0).getBatchId());
                    batchVo.setBatchNo(mapEntry.getValue().get(0).getBatchNo());
                    batchVo.setBatchName(mapEntry.getValue().get(0).getBatchName());
                    batchVo.setCreateBy(mapEntry.getValue().get(0).getCreateBy());
                    batchVo.setCreateTime(mapEntry.getValue().get(0).getCreateTime());
                    BatchReactionVo batchReactionVo = new BatchReactionVo();
                    ReactionVo reactionVo = new ReactionVo();
                    batchReactionVo.setReactionSort(mapEntry.getValue().get(0).getReactionSort());
                    List<WorkReactionDetail> workReactionDetails = new ArrayList<>();
                    for (WorkReactionBatch workReactionBatch : mapEntry.getValue()){
                        WorkReactionDetail workReactionDetail = new WorkReactionDetail();
                        BeanUtils.copyBeanProp(workReactionDetail, workReactionBatch);
                        workReactionDetails.add(workReactionDetail);
                    }
                    reactionVo.setReactionCode(mapEntry.getValue().get(0).getReactionCode());
                    reactionVo.setWorkReactionDetails(workReactionDetails);
                    batchReactionVo.setReactionVo(reactionVo);
                    batchReactionVos.add(batchReactionVo);
                }
                batchVo.setBatchReactionVos(batchReactionVos);
                batchVos.add(batchVo);
            }
        }
        return batchVos;
    }

    @Override
    public WorkFeedingRecord getWorkFeedingRecord(RecordQueryVo recordQueryVo) {
        WorkFeedingRecord workFeedingRecord = new WorkFeedingRecord();
        BeanUtils.copyBeanProp(workFeedingRecord, recordQueryVo);
        List<WorkFeedingRecord> workFeedingRecords = workFeedingRecordMapper.selectWorkFeedingRecordList(workFeedingRecord);
        if (CollectionUtils.isEmpty(workFeedingRecords)){
            return null;
        }
        return workFeedingRecords.get(0);
    }

    @Override
    public WorkHeatingRecord getWorkHeatingRecord(RecordQueryVo recordQueryVo) {
        WorkHeatingRecord workHeatingRecord = new WorkHeatingRecord();
        BeanUtils.copyBeanProp(workHeatingRecord, recordQueryVo);
        List<WorkHeatingRecord> workHeatingRecords = workHeatingRecordMapper.selectWorkHeatingRecordList(workHeatingRecord);
        if (CollectionUtils.isEmpty(workHeatingRecords)){
            return null;
        }
        return workHeatingRecords.get(0);
    }

    @Override
    public WorkExtractionRecord getWorkExtractionRecord(RecordQueryVo recordQueryVo) {
        WorkExtractionRecord workExtractionRecord = new WorkExtractionRecord();
        BeanUtils.copyBeanProp(workExtractionRecord, recordQueryVo);
        List<WorkExtractionRecord> workExtractionRecords = workExtractionRecordMapper.selectWorkExtractionRecordList(workExtractionRecord);
        if (CollectionUtils.isEmpty(workExtractionRecords)){
            return null;
        }
        return workExtractionRecords.get(0);
    }

    @Override
    public WorkConcentrateRecord getWorkConcentrateRecord(RecordQueryVo recordQueryVo) {
        WorkConcentrateRecord workConcentrateRecord = new WorkConcentrateRecord();
        BeanUtils.copyBeanProp(workConcentrateRecord, recordQueryVo);
        List<WorkConcentrateRecord> workConcentrateRecords = workConcentrateRecordMapper.selectWorkConcentrateRecordList(workConcentrateRecord);
        if (CollectionUtils.isEmpty(workConcentrateRecords)){
            return null;
        }
        return workConcentrateRecords.get(0);
    }

    @Override
    public WorkGelRecord getWorkGelRecord(RecordQueryVo recordQueryVo) {
        WorkGelRecord workGelRecord = new WorkGelRecord();
        BeanUtils.copyBeanProp(workGelRecord, recordQueryVo);
        List<WorkGelRecord> workGelRecords = workGelRecordMapper.selectWorkGelRecordList(workGelRecord);
        if (CollectionUtils.isEmpty(workGelRecords)){
            return null;
        }
        return workGelRecords.get(0);
    }

    @Override
    public void updateWorkFeedingRecord(WorkFeedingRecord workFeedingRecord) {
        workFeedingRecordMapper.updateWorkFeedingRecord(workFeedingRecord);
    }

    @Override
    public void updateWorkHeatingRecord(WorkHeatingRecord workHeatingRecord) {
        workHeatingRecordMapper.updateWorkHeatingRecord(workHeatingRecord);
    }

    @Override
    public void updateWorkExtractionRecord(WorkExtractionRecord workExtractionRecord) {
        workExtractionRecordMapper.updateWorkExtractionRecord(workExtractionRecord);
    }

    @Override
    public void updateWorkConcentrateRecord(WorkConcentrateRecord workConcentrateRecord) {
        workConcentrateRecordMapper.updateWorkConcentrateRecord(workConcentrateRecord);
    }

    @Override
    public void updateWorkGelRecord(WorkGelRecord workGelRecord) {
        workGelRecordMapper.updateWorkGelRecord(workGelRecord);
    }

    @Override
    public List<BatchProcessVo> getBatchProcessList(RecordQueryVo recordQueryVo) {
        return null;
    }
}
