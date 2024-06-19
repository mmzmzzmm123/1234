package com.renxin.psychology.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.renxin.psychology.domain.PsyConsult;
import com.renxin.psychology.domain.PsyConsultContract;

/**
 * 咨询师合同协议Mapper接口
 * 
 * @author renxin
 * @date 2023-11-16
 */
public interface PsyConsultContractMapper extends BaseMapper<PsyConsultContract>
{
    /**
     * 查询咨询师合同协议
     *
     * @param id 咨询师合同协议主键
     * @return 咨询师合同协议
     */
    PsyConsultContract getOne(Long id);

    /**
     * 查询咨询师合同协议列表
     *
     * @param psyConsultContract 咨询师合同协议
     * @return 咨询师合同协议集合
     */
    List<PsyConsultContract> getList(PsyConsultContract psyConsultContract);

    PsyConsultContract getListByUserName(String userName);

    int countExistContract(PsyConsultContract req);

    List<PsyConsult> getConsultList();

}
