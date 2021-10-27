package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.user.SmsException;
import com.ruoyi.common.exception.user.UserException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.DataCompanyLoan;
import com.ruoyi.system.domain.model.DataCompanyLoanBody;
import com.ruoyi.system.domain.model.DataGTGSH;
import com.ruoyi.system.domain.model.DataMatchCompany;
import com.ruoyi.system.mapper.DataCompanyLoanMapper;
import com.ruoyi.system.service.IDataCompanyLoanOracleService;
import com.ruoyi.system.service.IDataCompanyLoanService;
import com.ruoyi.system.service.IDataSmsService;
import com.ruoyi.system.utils.ShareInterface;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.ruoyi.common.utils.mzt.MztSample.sendPostParams;
import static com.ruoyi.system.domain.model.DataCodeMsgResponse.UMS_SUCCESS_CODE;

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

    // @Transactional(rollbackFor = Exception.class)
    // TODO:多数据源的事务处理
    @Override
    public int insertDataCompanyLoan(DataCompanyLoanBody dataCompanyLoanBody) {

        //验证短信
        String mobile = dataCompanyLoanBody.getContactPhone();
        String code = dataCompanyLoanBody.getCode();
        String mztUserId = dataCompanyLoanBody.getMztUserId();

        String verifyKey = Constants.SMS_CODE_KEY + mobile;
        String realCode = redisCache.getCacheObject(verifyKey);
        if (!StringUtils.equals(code,realCode)){
            throw new SmsException();
        }

        redisCache.deleteObject(verifyKey);

        String companyNameFromRequest = dataCompanyLoanBody.getCompanyName();

        DataCompanyLoan dataCompanyLoan = new DataCompanyLoan();

        dataCompanyLoan.setMztUserId(mztUserId);
        dataCompanyLoan.setCreateTime(DateUtils.getNowDate());
        dataCompanyLoan.setCompanyName(companyNameFromRequest);
        dataCompanyLoan.setContactName(dataCompanyLoanBody.getContactName());
        dataCompanyLoan.setLoanBand(dataCompanyLoanBody.getLoanBand());
        dataCompanyLoan.setLoanAmount(dataCompanyLoanBody.getLoanAmount());
        dataCompanyLoan.setLoanFirst(dataCompanyLoanBody.getLoanFirst());
        dataCompanyLoan.setMztUserId(dataCompanyLoanBody.getMztUserId());
        dataCompanyLoan.setLoanPurpose(dataCompanyLoanBody.getLoanPurpose());
        dataCompanyLoan.setContactPhone(mobile);

        dataCompanyLoan.setBankBranch(dataCompanyLoanBody.getBankBranch());
        dataCompanyLoan.setCustomerManager(dataCompanyLoanBody.getCustomerManager());
        dataCompanyLoan.setLoanObjectType(dataCompanyLoanBody.getLoanObjectType() == DataMatchCompany.TYPE_PERSON ? "个体工商户" : "企业法人");
        String xydm = "";

        if (DataMatchCompany.TYPE_PERSON  == dataCompanyLoanBody.getLoanObjectType()){
            xydm = dataCompanyLoanBody.getXydm();
            JSONObject jsonObject = ShareInterface.queryGTGSHByXydm(xydm);
            if (jsonObject != null){
                String name = jsonObject.getString("traname");
                // 用户上传的企业名称与接口匹配数据一致或者包含关系，才进行相关信息补充。
                if (name != null && (StringUtils.equals(name, companyNameFromRequest) || name.contains(companyNameFromRequest))){
                    dataCompanyLoan.setCompanyCreditCode(xydm);
                    dataCompanyLoan.setCompanyBusiness(jsonObject.getString("jyfw"));
                    dataCompanyLoan.setCompanyAddress(jsonObject.getString("jycsdz"));
                }
            }
        }else {
            //根据企业名称组装企业相关数据：企业划型、所在行业、主营业务、省市区
            Map<String, String> map = ShareInterface.queryCompanyInfo(companyNameFromRequest);
            xydm = map.get("tyshxydm");
            dataCompanyLoan.setCompanyCreditCode(xydm);
            dataCompanyLoan.setCompanyType(map.get("companytype"));
            dataCompanyLoan.setCompanyIndustry(map.get("indurstryname"));
            dataCompanyLoan.setCompanyBusiness(map.get("managerange"));
            dataCompanyLoan.setCompanyAddress(map.get("regaddress"));
        }

        if (StringUtils.isNotEmpty(xydm)){
            boolean isTrust = ShareInterface.isTrust(xydm);
            if (!isTrust) {
                throw new UserException(null, null, "保存'" + dataCompanyLoan.getCompanyName() + "'失败，该企业存在失信记录");
            }
        }
        dataCompanyLoanMapper.insertDataCompanyLoan(dataCompanyLoan);
        Long companyId = dataCompanyLoan.getCompanyId();

        DataCompanyLoan loanClone = (DataCompanyLoan) dataCompanyLoan.clone();
        loanClone.setCompanyId(companyId);
        loanClone.setLoanBand(dataCompanyLoanBody.getLoanBandNames());
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
        AjaxResult response = smsService.sendVerifyCodeByUMS(phone,code);
        int resultCode = (int) response.get(AjaxResult.CODE_TAG);
        if (UMS_SUCCESS_CODE == resultCode){//发送短信成功
            redisCache.setCacheObject(verifyKey, code, Constants.SMS_CODE_EXPIRATION, TimeUnit.MINUTES);
            return code;
        }else {

            String msg = (String) response.get(AjaxResult.MSG_TAG);
            //TODO:暂时只处理此编码的错误
            if (32 == resultCode){
                msg = "同一号码发送次数太多,一天内手机号码验证码最大发送次数为5次";
            }else if(33 == resultCode){
                msg = "同一号码验证码提交过快";
            }
            throw new UserException(null , null, msg);
        }

    }

    @Override
    public JSONObject getUserInfo(String userId,String token) {
        String url = "https://mztapp.fujian.gov.cn:8304/"
                + "dataset/AppSerController/invokeservice.do";
        Map<String,Object> paramsMap = new HashMap<String,Object>();

        paramsMap.put("INVOKESERVICE_CODE","033");
        paramsMap.put("INVOKECALLER_CODE","2bf26e96740adbedda3917e8d72f1989f1d3c8d9");
        paramsMap.put("USER_ID",userId);
        paramsMap.put("USER_TOKEN",token);

        String POSTPARAM_JSON = JSON.toJSONString(paramsMap);
        Map<String,Object> clientParam = new HashMap<String,Object>();
        clientParam.put("POSTPARAM_JSON", POSTPARAM_JSON);

        String result = sendPostParams(url, clientParam);
        System.out.println(result);
        return JSONObject.parseObject(result);
    }

    @Override
    public List<DataMatchCompany> matchCompanyName(String companyName,int count) {

        List<DataMatchCompany> list = new ArrayList<>();

        List<String> companyNameList = ShareInterface.queryCompanyName(companyName);
        for (String name : companyNameList){
            DataMatchCompany company = new DataMatchCompany();
            company.setName(name);
            company.setType(DataMatchCompany.TYPE_COMPANY);
            list.add(company);
        }

        if (list.size()>count){
            list = list.subList(0,count);
        }

        List<DataGTGSH> gtgshes = ShareInterface.queryGTGSHByName(companyName);
        for (DataGTGSH gtgsh : gtgshes){
            DataMatchCompany company = new DataMatchCompany();
            company.setName(gtgsh.getName());
            company.setXydm(gtgsh.getXydm());
            company.setType(DataMatchCompany.TYPE_PERSON);
            list.add(company);
        }


        if (list.size()>count){
            list = list.subList(0,count);
        }

        return list;
    }

    private String numRandom(int bit) {
        if (bit == 0)
            bit = 6; // 默认6位
        String str = "";
        str = "0123456789";// 初始化种子
        return RandomStringUtils.random(bit, str);// 返回6位的字符串
    }
}
