package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.DataCompanyLoan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 企业贷款信息Mapper接口
 * 
 * @author genius
 * @date 2021-07-13
 */
public interface DataCompanyLoanMapper 
{
    /**
     * 查询企业贷款信息
     * 
     * @param companyId 企业贷款信息ID
     * @return 企业贷款信息
     */
    public DataCompanyLoan selectDataCompanyLoanById(Long companyId);

    /**
     * 查询企业贷款信息列表
     * 
     * @param dataCompanyLoan 企业贷款信息
     * @return 企业贷款信息集合
     */
    public List<DataCompanyLoan> selectDataCompanyLoanList(DataCompanyLoan dataCompanyLoan);

    /**
     * 新增企业贷款信息
     * 
     * @param dataCompanyLoan 企业贷款信息
     * @return 结果
     */
    public int insertDataCompanyLoan(DataCompanyLoan dataCompanyLoan);

    /**
     * 修改企业贷款信息
     * 
     * @param dataCompanyLoan 企业贷款信息
     * @return 结果
     */
    public int updateDataCompanyLoan(DataCompanyLoan dataCompanyLoan);

    /**
     * 删除企业贷款信息
     * 
     * @param companyId 企业贷款信息ID
     * @return 结果
     */
    public int deleteDataCompanyLoanById(Long companyId);

    /**
     * 批量删除企业贷款信息
     * 
     * @param companyIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataCompanyLoanByIds(Long[] companyIds);

    /**
     * 验证企业名称是否唯一
     * @param companyName
     * @return
     */
    public DataCompanyLoan checkCompanyNameUnique(@Param("companyName") String companyName);
}
