package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.DataCompanyLoan;

import java.util.List;

/**
 * 企业贷款信息Mapper接口
 * 
 * @author genius
 * @date 2021-07-13
 */
public interface DataCompanyLoanOracleMapper
{

    /**
     * 查询企业贷款信息列表
     * 
     * @param dataCompanyLoan 企业贷款信息
     * @return 企业贷款信息集合
     */
    public List<DataCompanyLoan> selectDataCompanyLoanList(DataCompanyLoan dataCompanyLoan);

}
