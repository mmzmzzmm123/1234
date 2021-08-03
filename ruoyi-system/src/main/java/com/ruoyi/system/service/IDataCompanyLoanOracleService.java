package com.ruoyi.system.service;

import com.ruoyi.system.domain.DataCompanyLoan;

import java.util.List;

/**
 * Oracle数据源
 * 
 * @author genius
 * @date 2021-07-29
 */
public interface IDataCompanyLoanOracleService
{
    public int insertDataCompanyLoan(DataCompanyLoan dataCompanyLoan);

    public List<DataCompanyLoan> selectDataCompanyLoanList(DataCompanyLoan dataCompanyLoan);
}
