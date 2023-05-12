package com.ruoyi.system.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.domain.DataCompanyLoan;
import com.ruoyi.system.domain.model.CreditReport;
import com.ruoyi.system.domain.model.DataCompanyLoanBody;
import com.ruoyi.system.domain.model.DataMatchCompany;

import java.util.List;

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
    public int insertDataCompanyLoan(DataCompanyLoanBody dataCompanyLoan);

    boolean checkSMSCode(String mobile, String code);

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
    String senSmsCode(String phone);

    JSONObject getUserInfo(String userID,String token);

    /**
     * 模糊匹配
     * @param companyName 企业名称
     * @param count 企业法人和个体工商户各取20条
     * @return
     */
    List<DataMatchCompany> matchCompanyName(String companyName,int count);

    CreditReport getReport(String tyshxydm);

}
