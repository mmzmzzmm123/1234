package com.ruoyi.psychology.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.IDhelper;
import com.ruoyi.psychology.constant.ConsultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.psychology.mapper.PsyConsultContractMapper;
import com.ruoyi.psychology.domain.PsyConsultContract;
import com.ruoyi.psychology.service.IPsyConsultContractService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 咨询师合同协议Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-16
 */
@Service
public class PsyConsultContractServiceImpl implements IPsyConsultContractService 
{
    @Autowired
    private PsyConsultContractMapper psyConsultContractMapper;

    /**
     * 查询咨询师合同协议
     * 
     * @param id 咨询师合同协议主键
     * @return 咨询师合同协议
     */
    @Override
    public PsyConsultContract getOne(Long id)
    {
        return psyConsultContractMapper.selectById(id);
    }

    /**
     * 查询咨询师合同协议列表
     * 
     * @param psyConsultContract 咨询师合同协议
     * @return 咨询师合同协议
     */
    @Override
    public List<PsyConsultContract> getList(PsyConsultContract psyConsultContract)
    {
        return psyConsultContractMapper.getList(psyConsultContract);
    }

    @Override
    public int countExistContract(PsyConsultContract req)
    {
        return psyConsultContractMapper.countExistContract(req);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int relaunch(PsyConsultContract entity)
    {
        Long id = entity.getId();
        PsyConsultContract one = getOne(id);
        one.setStatus(ConsultConstant.CONTRACT_STATUS_2);
        update(one);

        entity.setId(IDhelper.getNextId());
        return add(entity);
    }

    /**
     * 新增咨询师合同协议
     * 
     * @param psyConsultContract 咨询师合同协议
     * @return 结果
     */
    @Override
    public int add(PsyConsultContract psyConsultContract)
    {
        return psyConsultContractMapper.insert(psyConsultContract);
    }

    /**
     * 修改咨询师合同协议
     * 
     * @param psyConsultContract 咨询师合同协议
     * @return 结果
     */
    @Override
    public int update(PsyConsultContract psyConsultContract)
    {
        return psyConsultContractMapper.updateById(psyConsultContract);
    }

}
