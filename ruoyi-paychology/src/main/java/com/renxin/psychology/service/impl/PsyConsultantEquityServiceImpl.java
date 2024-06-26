package com.renxin.psychology.service.impl;

import java.util.List;
import com.renxin.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.renxin.psychology.mapper.PsyConsultantEquityMapper;
import com.renxin.psychology.domain.PsyConsultantEquity;
import com.renxin.psychology.service.IPsyConsultantEquityService;

/**
 * 咨询师权益Service业务层处理
 * 
 * @author renxin
 * @date 2024-06-26
 */
@Service
public class PsyConsultantEquityServiceImpl implements IPsyConsultantEquityService 
{
    @Autowired
    private PsyConsultantEquityMapper psyConsultantEquityMapper;

    /**
     * 查询咨询师权益
     * 
     * @param id 咨询师权益主键
     * @return 咨询师权益
     */
    @Override
    public PsyConsultantEquity selectPsyConsultantEquityById(Long id)
    {
        return psyConsultantEquityMapper.selectPsyConsultantEquityById(id);
    }

    /**
     * 查询咨询师权益列表
     * 
     * @param psyConsultantEquity 咨询师权益
     * @return 咨询师权益
     */
    @Override
    public List<PsyConsultantEquity> selectPsyConsultantEquityList(PsyConsultantEquity psyConsultantEquity)
    {
        return psyConsultantEquityMapper.selectPsyConsultantEquityList(psyConsultantEquity);
    }

    /**
     * 新增咨询师权益
     * 
     * @param psyConsultantEquity 咨询师权益
     * @return 结果
     */
    @Override
    public int insertPsyConsultantEquity(PsyConsultantEquity psyConsultantEquity)
    {
        psyConsultantEquity.setCreateTime(DateUtils.getNowDate());
        return psyConsultantEquityMapper.insertPsyConsultantEquity(psyConsultantEquity);
    }

    /**
     * 修改咨询师权益
     * 
     * @param psyConsultantEquity 咨询师权益
     * @return 结果
     */
    @Override
    public int updatePsyConsultantEquity(PsyConsultantEquity psyConsultantEquity)
    {
        psyConsultantEquity.setUpdateTime(DateUtils.getNowDate());
        return psyConsultantEquityMapper.updatePsyConsultantEquity(psyConsultantEquity);
    }

    /**
     * 批量删除咨询师权益
     * 
     * @param ids 需要删除的咨询师权益主键
     * @return 结果
     */
    @Override
    public int deletePsyConsultantEquityByIds(Long[] ids)
    {
        return psyConsultantEquityMapper.deletePsyConsultantEquityByIds(ids);
    }

    /**
     * 删除咨询师权益信息
     * 
     * @param id 咨询师权益主键
     * @return 结果
     */
    @Override
    public int deletePsyConsultantEquityById(Long id)
    {
        return psyConsultantEquityMapper.deletePsyConsultantEquityById(id);
    }
}
