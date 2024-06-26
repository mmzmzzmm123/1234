package com.renxin.psychology.service.impl;

import java.util.List;
import com.renxin.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.renxin.psychology.mapper.PsyConsultantEquityVerificationMapper;
import com.renxin.psychology.domain.PsyConsultantEquityVerification;
import com.renxin.psychology.service.IPsyConsultantEquityVerificationService;

/**
 * 咨询师权益核销Service业务层处理
 * 
 * @author renxin
 * @date 2024-06-26
 */
@Service
public class PsyConsultantEquityVerificationServiceImpl implements IPsyConsultantEquityVerificationService 
{
    @Autowired
    private PsyConsultantEquityVerificationMapper psyConsultantEquityVerificationMapper;

    /**
     * 查询咨询师权益核销
     * 
     * @param id 咨询师权益核销主键
     * @return 咨询师权益核销
     */
    @Override
    public PsyConsultantEquityVerification selectPsyConsultantEquityVerificationById(Long id)
    {
        return psyConsultantEquityVerificationMapper.selectPsyConsultantEquityVerificationById(id);
    }

    /**
     * 查询咨询师权益核销列表
     * 
     * @param psyConsultantEquityVerification 咨询师权益核销
     * @return 咨询师权益核销
     */
    @Override
    public List<PsyConsultantEquityVerification> selectPsyConsultantEquityVerificationList(PsyConsultantEquityVerification psyConsultantEquityVerification)
    {
        return psyConsultantEquityVerificationMapper.selectPsyConsultantEquityVerificationList(psyConsultantEquityVerification);
    }

    /**
     * 新增咨询师权益核销
     * 
     * @param psyConsultantEquityVerification 咨询师权益核销
     * @return 结果
     */
    @Override
    public int insertPsyConsultantEquityVerification(PsyConsultantEquityVerification psyConsultantEquityVerification)
    {
        psyConsultantEquityVerification.setCreateTime(DateUtils.getNowDate());
        return psyConsultantEquityVerificationMapper.insertPsyConsultantEquityVerification(psyConsultantEquityVerification);
    }

    /**
     * 修改咨询师权益核销
     * 
     * @param psyConsultantEquityVerification 咨询师权益核销
     * @return 结果
     */
    @Override
    public int updatePsyConsultantEquityVerification(PsyConsultantEquityVerification psyConsultantEquityVerification)
    {
        psyConsultantEquityVerification.setUpdateTime(DateUtils.getNowDate());
        return psyConsultantEquityVerificationMapper.updatePsyConsultantEquityVerification(psyConsultantEquityVerification);
    }

    /**
     * 批量删除咨询师权益核销
     * 
     * @param ids 需要删除的咨询师权益核销主键
     * @return 结果
     */
    @Override
    public int deletePsyConsultantEquityVerificationByIds(Long[] ids)
    {
        return psyConsultantEquityVerificationMapper.deletePsyConsultantEquityVerificationByIds(ids);
    }

    /**
     * 删除咨询师权益核销信息
     * 
     * @param id 咨询师权益核销主键
     * @return 结果
     */
    @Override
    public int deletePsyConsultantEquityVerificationById(Long id)
    {
        return psyConsultantEquityVerificationMapper.deletePsyConsultantEquityVerificationById(id);
    }
}
