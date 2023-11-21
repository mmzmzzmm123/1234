package com.ruoyi.psychology.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class PsyConsultContractServiceImpl extends ServiceImpl<PsyConsultContractMapper, PsyConsultContract> implements IPsyConsultContractService
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
    public PsyConsultContract getListByUserName(String userName) {
        return psyConsultContractMapper.getListByUserName(userName);
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doJob()
    {
        // 1-待签署,2-已撤销,3-逾期未签署,4-待生效,5-生效中,6-已失效,7-终止
        // 定时任务处理待生效,逾期未签署
        LambdaQueryWrapper<PsyConsultContract> wp = new LambdaQueryWrapper<>();
        List<String> status = Arrays.asList(ConsultConstant.CONTRACT_STATUS_4, ConsultConstant.CONTRACT_STATUS_1);
        Date nowDate = DateUtils.getNowDate();
        wp.in(PsyConsultContract::getStatus, status);
        wp.le(PsyConsultContract::getStartTime, nowDate);
        List<PsyConsultContract> list = psyConsultContractMapper.selectList(wp);
        list.forEach(item -> {
            if (item.getStatus().equals(ConsultConstant.CONTRACT_STATUS_1)) {
                item.setStatus(ConsultConstant.CONTRACT_STATUS_3);
            } else {
                item.setStatus(item.getEndTime().getTime() > nowDate.getTime() ? ConsultConstant.CONTRACT_STATUS_5 : ConsultConstant.CONTRACT_STATUS_6);
            }
        });

        this.updateBatchById(list);
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
