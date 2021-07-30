package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.user.SmsException;
import com.ruoyi.common.exception.user.UserException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.DataCompanyLoan;
import com.ruoyi.system.domain.model.DataCodeMsgResponse;
import com.ruoyi.system.domain.model.DataCompanyLoanBody;
import com.ruoyi.system.mapper.DataCompanyLoanMapper;
import com.ruoyi.system.service.IDataCompanyLoanOracleService;
import com.ruoyi.system.service.IDataCompanyLoanService;
import com.ruoyi.system.service.IDataSmsService;
import com.ruoyi.system.utils.ShareInterface;
import com.sun.xml.internal.bind.v2.TODO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 企业贷款信息Service业务层处理
 * 
 * @author genius
 * @date 2021-07-13
 */
@Service
public class DataCompanyLoanServiceImpl implements IDataCompanyLoanService 
{
    @Autowired
    private DataCompanyLoanMapper dataCompanyLoanMapper;

    @Autowired
    private IDataCompanyLoanOracleService companyLoanOracleService;

    @Autowired
    private IDataSmsService smsService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询企业贷款信息
     * 
     * @param companyId 企业贷款信息ID
     * @return 企业贷款信息
     */
    @Override
    public DataCompanyLoan selectDataCompanyLoanById(Long companyId)
    {
        return dataCompanyLoanMapper.selectDataCompanyLoanById(companyId);
    }

    /**
     * 查询企业贷款信息列表
     * 
     * @param dataCompanyLoan 企业贷款信息
     * @return 企业贷款信息
     */
    @Override
    public List<DataCompanyLoan> selectDataCompanyLoanList(DataCompanyLoan dataCompanyLoan)
    {
        return dataCompanyLoanMapper.selectDataCompanyLoanList(dataCompanyLoan);
    }

    /**
     * 新增企业贷款信息
     * 
     * @param dataCompanyLoan 企业贷款信息
     * @return 结果
     */
    @Override
    public int insertDataCompanyLoan(DataCompanyLoan dataCompanyLoan)
    {
        dataCompanyLoan.setCreateTime(DateUtils.getNowDate());
        return dataCompanyLoanMapper.insertDataCompanyLoan(dataCompanyLoan);
    }

    //TODO:多数据源的事务如何处理？
    @Override
    public int insertDataCompanyLoan(DataCompanyLoanBody dataCompanyLoanBody) {

        //验证短信
        String mobile = dataCompanyLoanBody.getContactPhone();
        String code = dataCompanyLoanBody.getCode();
        String mztUserId = dataCompanyLoanBody.getMztUserId();

        String verifyKey = Constants.SMS_CODE_KEY + mobile;
        String realCode = redisCache.getCacheObject(verifyKey);

//        TODO 测试
//        if (!StringUtils.equals(code,realCode)){
//            throw new SmsException();
//        }

        redisCache.deleteObject(verifyKey);

        DataCompanyLoan dataCompanyLoan = new DataCompanyLoan();

        dataCompanyLoan.setMztUserId(mztUserId);
        dataCompanyLoan.setCreateTime(DateUtils.getNowDate());
        dataCompanyLoan.setCompanyName(dataCompanyLoanBody.getCompanyName());
        dataCompanyLoan.setContactName(dataCompanyLoanBody.getContactName());
        dataCompanyLoan.setLoanBand(dataCompanyLoanBody.getLoanBand());
        dataCompanyLoan.setLoanAmount(dataCompanyLoanBody.getLoanAmount());
        dataCompanyLoan.setLoanFirst(dataCompanyLoanBody.getLoanFirst());
        dataCompanyLoan.setMztUserId(dataCompanyLoanBody.getMztUserId());
        dataCompanyLoan.setLoanPurpose(dataCompanyLoanBody.getLoanPurpose());
        dataCompanyLoan.setContactPhone(mobile);

        //根据企业名称组装企业相关数据：企业划型、所在行业、主营业务、省市区
        Map<String, String> map = ShareInterface.queryCompanyInfo(dataCompanyLoanBody.getCompanyName());
        String xydm = map.get("tyshxydm");
        dataCompanyLoan.setCompanyCreditCode(xydm);
        dataCompanyLoan.setCompanyType(map.get("companytype"));
        dataCompanyLoan.setCompanyIndustry(map.get("indurstryname"));
        dataCompanyLoan.setCompanyBusiness(map.get("managerange"));

        boolean isTrust = ShareInterface.isTrust(xydm);
        if (!isTrust) {
            throw new UserException(null, null, "保存'" + dataCompanyLoan.getCompanyName() + "'失败，该企业存在失信记录");
        }

        dataCompanyLoanMapper.insertDataCompanyLoan(dataCompanyLoan);
        Long companyId = dataCompanyLoan.getCompanyId();

        DataCompanyLoan loanClone = (DataCompanyLoan) dataCompanyLoan.clone();
        loanClone.setCompanyId(companyId);
        loanClone.setDelFlag(null);
        //插入 Oracle 表
        return companyLoanOracleService.insertDataCompanyLoan(loanClone);
    }

    /**
     * 修改企业贷款信息
     * 
     * @param dataCompanyLoan 企业贷款信息
     * @return 结果
     */
    @Override
    public int updateDataCompanyLoan(DataCompanyLoan dataCompanyLoan)
    {
        dataCompanyLoan.setUpdateTime(DateUtils.getNowDate());
        return dataCompanyLoanMapper.updateDataCompanyLoan(dataCompanyLoan);
    }

    /**
     * 批量删除企业贷款信息
     * 
     * @param companyIds 需要删除的企业贷款信息ID
     * @return 结果
     */
    @Override
    public int deleteDataCompanyLoanByIds(Long[] companyIds)
    {
        return dataCompanyLoanMapper.deleteDataCompanyLoanByIds(companyIds);
    }

    /**
     * 删除企业贷款信息信息
     * 
     * @param companyId 企业贷款信息ID
     * @return 结果
     */
    @Override
    public int deleteDataCompanyLoanById(Long companyId)
    {
        return dataCompanyLoanMapper.deleteDataCompanyLoanById(companyId);
    }

    @Override
    public String checkCompanyNameUnique(String companyName) {
        DataCompanyLoan info = dataCompanyLoanMapper.checkCompanyNameUnique(companyName);
        if (StringUtils.isNotNull(info))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String senSmsCode(String phone) {
        String verifyKey = Constants.SMS_CODE_KEY + phone;
        String code = numRandom(6);
        DataCodeMsgResponse response = smsService.sendVerifyCode(phone,code);
        if (StringUtils.equals(DataCodeMsgResponse.SUCCESS_CODE,response.getCode())){//发送短信成功
            redisCache.setCacheObject(verifyKey, code, Constants.SMS_CODE_EXPIRATION, TimeUnit.MINUTES);
            return code;
        }else {
            throw new UserException(null, null, JSON.toJSONString(response));
        }

    }

    private String numRandom(int bit) {
        if (bit == 0)
            bit = 6; // 默认6位
        String str = "";
        str = "0123456789";// 初始化种子
        return RandomStringUtils.random(bit, str);// 返回6位的字符串
    }
}
