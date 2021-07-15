package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.DataCompanyLoan;
import com.ruoyi.system.domain.model.DataCompanyLoaBody;

/**
 * 企业贷款信息Service接口
 * 
 * @author genius
 * @date 2021-07-13
 */
public interface IDataCompanyLoanService 
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
     * 移动端新增企业贷款信息（带手机验证码）
     * @param dataCompanyLoan
     * @return
     */
    public int insertDataCompanyLoan(DataCompanyLoaBody dataCompanyLoan);

    /**
     * 修改企业贷款信息
     * 
     * @param dataCompanyLoan 企业贷款信息
     * @return 结果
     */
    public int updateDataCompanyLoan(DataCompanyLoan dataCompanyLoan);

    /**
     * 批量删除企业贷款信息
     * 
     * @param companyIds 需要删除的企业贷款信息ID
     * @return 结果
     */
    public int deleteDataCompanyLoanByIds(Long[] companyIds);

    /**
     * 删除企业贷款信息信息
     * 
     * @param companyId 企业贷款信息ID
     * @return 结果
     */
    public int deleteDataCompanyLoanById(Long companyId);

    public String checkCompanyNameUnique(String companyName);

    /**
     * 发送短信验证码
     */
    void senSmsCode(String phone);
}
