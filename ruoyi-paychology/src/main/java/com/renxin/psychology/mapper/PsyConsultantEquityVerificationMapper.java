package com.renxin.psychology.mapper;

import java.util.List;
import com.renxin.psychology.domain.PsyConsultantEquityVerification;

/**
 * 咨询师权益核销Mapper接口
 * 
 * @author renxin
 * @date 2024-06-26
 */
public interface PsyConsultantEquityVerificationMapper 
{
    /**
     * 查询咨询师权益核销
     * 
     * @param id 咨询师权益核销主键
     * @return 咨询师权益核销
     */
    public PsyConsultantEquityVerification selectPsyConsultantEquityVerificationById(Long id);

    /**
     * 查询咨询师权益核销列表
     * 
     * @param psyConsultantEquityVerification 咨询师权益核销
     * @return 咨询师权益核销集合
     */
    public List<PsyConsultantEquityVerification> selectPsyConsultantEquityVerificationList(PsyConsultantEquityVerification psyConsultantEquityVerification);

    /**
     * 新增咨询师权益核销
     * 
     * @param psyConsultantEquityVerification 咨询师权益核销
     * @return 结果
     */
    public int insertPsyConsultantEquityVerification(PsyConsultantEquityVerification psyConsultantEquityVerification);

    /**
     * 修改咨询师权益核销
     * 
     * @param psyConsultantEquityVerification 咨询师权益核销
     * @return 结果
     */
    public int updatePsyConsultantEquityVerification(PsyConsultantEquityVerification psyConsultantEquityVerification);

    /**
     * 删除咨询师权益核销
     * 
     * @param id 咨询师权益核销主键
     * @return 结果
     */
    public int deletePsyConsultantEquityVerificationById(Long id);

    /**
     * 批量删除咨询师权益核销
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePsyConsultantEquityVerificationByIds(Long[] ids);
}
