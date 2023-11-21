package com.ruoyi.psychology.service;

import java.util.List;
import com.ruoyi.psychology.domain.PsyConsultContract;

/**
 * 咨询师合同协议Service接口
 * 
 * @author ruoyi
 * @date 2023-11-16
 */
public interface IPsyConsultContractService 
{
    /**
     * 查询咨询师合同协议
     * 
     * @param id 咨询师合同协议主键
     * @return 咨询师合同协议
     */
    public PsyConsultContract getOne(Long id);

    /**
     * 查询咨询师合同协议列表
     * 
     * @param psyConsultContract 咨询师合同协议
     * @return 咨询师合同协议集合
     */
    public List<PsyConsultContract> getList(PsyConsultContract psyConsultContract);

    public PsyConsultContract getListByUserName(String userName);

    public int countExistContract(PsyConsultContract psyConsultContract);

    public int relaunch(PsyConsultContract psyConsultContract);

    public void doJob();

    public void initData();

    /**
     * 新增咨询师合同协议
     * 
     * @param psyConsultContract 咨询师合同协议
     * @return 结果
     */
    public int add(PsyConsultContract psyConsultContract);

    /**
     * 修改咨询师合同协议
     * 
     * @param psyConsultContract 咨询师合同协议
     * @return 结果
     */
    public int update(PsyConsultContract psyConsultContract);

}
