package com.renxin.psychology.service;

import com.renxin.psychology.domain.PsyConsultantAccountRecord;

import java.util.List;

/**
 * 账户明细流水Service接口
 * 
 * @author renxin
 * @date 2024-06-20
 */
public interface IPsyConsultantAccountRecordService 
{
    /**
     * 查询账户明细流水
     * 
     * @param recordId 账户明细流水主键
     * @return 账户明细流水
     */
    public PsyConsultantAccountRecord selectPsyConsultantAccountRecordByRecordId(Long recordId);

    /**
     * 查询账户明细流水列表
     * 
     * @param psyConsultantAccountRecord 账户明细流水
     * @return 账户明细流水集合
     */
    public List<PsyConsultantAccountRecord> selectPsyConsultantAccountRecordList(PsyConsultantAccountRecord psyConsultantAccountRecord);

    /**
     * 新增账户明细流水
     * 
     * @param psyConsultantAccountRecord 账户明细流水
     * @return 结果
     */
    public int insertPsyConsultantAccountRecord(PsyConsultantAccountRecord psyConsultantAccountRecord);

    /**
     * 修改账户明细流水
     * 
     * @param psyConsultantAccountRecord 账户明细流水
     * @return 结果
     */
    public int updatePsyConsultantAccountRecord(PsyConsultantAccountRecord psyConsultantAccountRecord);

    /**
     * 批量删除账户明细流水
     * 
     * @param recordIds 需要删除的账户明细流水主键集合
     * @return 结果
     */
    public int deletePsyConsultantAccountRecordByRecordIds(Long[] recordIds);

    /**
     * 删除账户明细流水信息
     * 
     * @param recordId 账户明细流水主键
     * @return 结果
     */
    public int deletePsyConsultantAccountRecordByRecordId(Long recordId);
}
