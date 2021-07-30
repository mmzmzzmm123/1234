package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.DataCompanyLoan;
import com.ruoyi.system.mapper.DataCompanyLoanMapper;
import com.ruoyi.system.service.IDataCompanyLoanOracleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 企业贷款信息Service业务层处理（Oracle数据源）
 * 
 * @author genius
 * @date 2021-07-29
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class DataCompanyLoanServiceOracleImpl implements IDataCompanyLoanOracleService
{
    @Autowired
    private DataCompanyLoanMapper dataCompanyLoanMapper;

    @Override
    public int insertDataCompanyLoan(DataCompanyLoan dataCompanyLoan) {
        dataCompanyLoan.setCreateTime(DateUtils.getNowDate());
        return dataCompanyLoanMapper.insertDataCompanyLoanOracle(dataCompanyLoan);
    }
}
