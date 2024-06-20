package com.renxin.psychology.service.impl;

import java.util.List;
import com.renxin.common.utils.DateUtils;
import com.renxin.psychology.domain.PsyConsultantAccountRecord;
import com.renxin.psychology.mapper.PsyConsultantAccountRecordMapper;
import com.renxin.psychology.service.IPsyConsultantAccountRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 账户明细流水Service业务层处理
 * 
 * @author renxin
 * @date 2024-06-20
 */
@Service
public class PsyConsultantAccountRecordServiceImpl implements IPsyConsultantAccountRecordService
{
    @Autowired
    private PsyConsultantAccountRecordMapper psyConsultantAccountRecordMapper;

    /**
     * 查询账户明细流水
     * 
     * @param recordId 账户明细流水主键
     * @return 账户明细流水
     */
    @Override
    public PsyConsultantAccountRecord selectPsyConsultantAccountRecordByRecordId(Long recordId)
    {
        return psyConsultantAccountRecordMapper.selectPsyConsultantAccountRecordByRecordId(recordId);
    }

    /**
     * 查询账户明细流水列表
     * 
     * @param psyConsultantAccountRecord 账户明细流水
     * @return 账户明细流水
     */
    @Override
    public List<PsyConsultantAccountRecord> selectPsyConsultantAccountRecordList(PsyConsultantAccountRecord psyConsultantAccountRecord)
    {
        return psyConsultantAccountRecordMapper.selectPsyConsultantAccountRecordList(psyConsultantAccountRecord);
    }

    /**
     * 新增账户明细流水
     * 
     * @param psyConsultantAccountRecord 账户明细流水
     * @return 结果
     */
    @Override
    public int insertPsyConsultantAccountRecord(PsyConsultantAccountRecord psyConsultantAccountRecord)
    {
        psyConsultantAccountRecord.setCreateTime(DateUtils.getNowDate());
        return psyConsultantAccountRecordMapper.insertPsyConsultantAccountRecord(psyConsultantAccountRecord);
    }

    /**
     * 修改账户明细流水
     * 
     * @param psyConsultantAccountRecord 账户明细流水
     * @return 结果
     */
    @Override
    public int updatePsyConsultantAccountRecord(PsyConsultantAccountRecord psyConsultantAccountRecord)
    {
        psyConsultantAccountRecord.setUpdateTime(DateUtils.getNowDate());
        return psyConsultantAccountRecordMapper.updatePsyConsultantAccountRecord(psyConsultantAccountRecord);
    }

    /**
     * 批量删除账户明细流水
     * 
     * @param recordIds 需要删除的账户明细流水主键
     * @return 结果
     */
    @Override
    public int deletePsyConsultantAccountRecordByRecordIds(Long[] recordIds)
    {
        return psyConsultantAccountRecordMapper.deletePsyConsultantAccountRecordByRecordIds(recordIds);
    }

    /**
     * 删除账户明细流水信息
     * 
     * @param recordId 账户明细流水主键
     * @return 结果
     */
    @Override
    public int deletePsyConsultantAccountRecordByRecordId(Long recordId)
    {
        return psyConsultantAccountRecordMapper.deletePsyConsultantAccountRecordByRecordId(recordId);
    }
}
