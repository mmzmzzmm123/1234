package com.ruoyi.system.service.impl;

import cn.shuibo.config.SecretKeyConfig;
import cn.shuibo.util.Base64Util;
import cn.shuibo.util.RSAUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.exception.user.SmsException;
import com.ruoyi.common.exception.user.UserException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.DataCompanyLoan;
import com.ruoyi.system.domain.model.*;
import com.ruoyi.system.mapper.DataCompanyLoanMapper;
import com.ruoyi.system.service.IDataCompanyLoanOracleService;
import com.ruoyi.system.service.IDataCompanyLoanService;
import com.ruoyi.system.service.IDataSmsService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
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

    private static final Logger log = LoggerFactory.getLogger(DataCompanyLoanServiceImpl.class);

    @Autowired
    private DataCompanyLoanMapper dataCompanyLoanMapper;

    @Autowired
    private IDataCompanyLoanOracleService companyLoanOracleService;

    @Autowired
    private IDataSmsService smsService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ShareInterface interfaceService;

    @Value("${api.domain}")
    private String domain;

    @Autowired
    private SecretKeyConfig secretKeyConfig;

    @Autowired
    private ProdOpenApi prodOpenApi;

    @Value("${spring.profiles.active}")
    private String active;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertDataCompanyLoan(DataCompanyLoanBody dataCompanyLoanBody) {

        //验证短信
        String mobile = dataCompanyLoanBody.getContactPhone();
        String code = dataCompanyLoanBody.getCode();
        String mztUserId = dataCompanyLoanBody.getMztUserId();

        // 校验手机号码
        String verifyKey = Constants.SMS_CODE_KEY + mobile;
        if (checkSMSCode(mobile, code)){
            redisCache.deleteObject(verifyKey);
        }

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
        dataCompanyLoan.setCompanyCreditCode(dataCompanyLoanBody.getXydm());

        //判断企业类型
        String loanObjectTypeRequest = dataCompanyLoanBody.getLoanObjectType();
        String loanObjectType = Constants.COMPANY_TYPE_MAP.get(loanObjectTypeRequest.toUpperCase());
        dataCompanyLoan.setLoanObjectType(loanObjectType);

        //同一企业，在3天内多次提交信贷直通车需求，且银行、金额完全一致
        String xydmCacheKey = generateXydmCacheKey(dataCompanyLoan.getCompanyCreditCode(), dataCompanyLoan.getLoanBand(), dataCompanyLoan.getLoanAmount() + "");
        log.info("验证是否重复贷款:"+xydmCacheKey);
        Object cacheObject = redisCache.getCacheObject(xydmCacheKey);
        if (cacheObject != null){
            throw new UserException(null, null, "近" + Constants.LOAN_TIME_EXPIRATION + "天内，您已提交贷款需求，请勿重复提交");
        }

        // 保存数据到本系统数据库中（方便数据校对）
        int result = dataCompanyLoanMapper.insertDataCompanyLoan(dataCompanyLoan);
        Long companyId = dataCompanyLoan.getCompanyId();

        // 提送贷款数据至业务方
        DataCompanyLoan loanClone = (DataCompanyLoan) dataCompanyLoan.clone();
        loanClone.setCompanyId(companyId);
        loanClone.setLoanBand(dataCompanyLoanBody.getLoanBandNames());
        loanClone.setDelFlag(null);
        loanClone.setSubmitTime(new Date());

        AjaxResult ajaxResult = prodOpenApi.pushCompanyLoan(loanClone);

        int responseCode = (int) ajaxResult.get(AjaxResult.CODE_TAG);
        if (responseCode != 200){
            log.error(ajaxResult.toString());
            throw new CustomException("贷款数据保存失败", HttpStatus.ERROR);
        }else{
            log.info(ajaxResult.toString());
        }

        //测试环境： 企业信用代码+银行+金额作为key，保存 redis 10分钟
        //redisCache.setCacheObject(xydmCacheKey, true, 10, TimeUnit.MINUTES);
        //企业信用代码+银行+金额作为key，保存 redis 三天
        redisCache.setCacheObject(xydmCacheKey, true, Constants.LOAN_TIME_EXPIRATION, TimeUnit.DAYS);

        return result;
    }

    @Override
    public boolean checkSMSCode(String mobile, String code) {
        // 校验手机号码
        String verifyKey = Constants.SMS_CODE_KEY + mobile;
        String realCode = redisCache.getCacheObject(verifyKey);

        if ("druid-prod".equals(active) || "druid-sit".equals(active)){ // 生产环境校验短信正确性
            if (!StringUtils.equals(code,realCode)){
                throw new SmsException();
            }
        }
        return true;
    }

    private String generateXydmCacheKey(String xydm, String bannerIds, String amount){
        return Constants.LOAN_CODE_KEY + xydm + "-" + bannerIds + "-" + amount;
    }

    private void throwCompanyNotFoundException(){
        throw new UserException(null, null, "查无此企业信息，请检查企业名称是否填写完整");
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

/*    @Override
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

    }*/

    @Override
    public String senSmsCode(String phone) {
        String verifyKey = Constants.SMS_CODE_KEY + phone;
        // 短信发送限制参数校验
        checkSmsLimit(phone);

        String code = numRandom(6);
//        AjaxResult response = smsService.sendVerifyCodeByUMS(phone,code);
        AjaxResult response = smsService.sendVerifyCodeByCredit(phone, code);
        int resultCode = (int) response.get(AjaxResult.CODE_TAG);
        if (HttpStatus.SUCCESS == resultCode) {//发送短信成功
            // 短信发送限制参数设置
            setSmsLimit(phone, code);
            return code;
        }else {
            String msg = (String) response.get(AjaxResult.MSG_TAG);
            //返回其他短信发送错误原因
            throw new UserException(null, null, msg);
        }

    }

    // 短信发送限制校验
    private void checkSmsLimit(String phone) {
        String date = DateUtils.getDate();

        //校验：每日手机号最大发送量
        String verifyCheckKey = Constants.SMS_CODE_CHECK_KEY + date + ":" + phone;
        int sendCount = redisCache.getCacheObject(verifyCheckKey) == null ? 0 :
                redisCache.getCacheObject(verifyCheckKey);
        if (sendCount >= Constants.SMS_CODE_LIMIT_PERDAY) {
            throw new UserException(null, null, "同一号码发送次数太多,一天内手机号码验证码最大发送次数为" + Constants.SMS_CODE_LIMIT_PERDAY + "次");

        }

        //校验： 短信发送间隔 60*1000毫秒
        Long sendTime = System.currentTimeMillis(); // 当前计划发送时间

        String lastSendTimeKey = Constants.SMS_CODE_INTERVAL_KEY + phone;
        // 获取上次短信发送时间
        Long lastSendTime = redisCache.getCacheObject(lastSendTimeKey) == null ? 0L :
                redisCache.getCacheObject(lastSendTimeKey);

        if ((sendTime - lastSendTime) <= Constants.SMS_CODE_LIMIT_INTERVAL) {
            int interval = Constants.SMS_CODE_LIMIT_INTERVAL.intValue() / (60 * 1000);
            throw new UserException(null, null, "同一号码验证码提交过快,每" + (interval == 1 ? "" : interval) +
                    "分钟只允许提交一次");
        }

    }

    // 短信发送成功设置参数
    private void setSmsLimit(String phone, String code) {
        String date = DateUtils.getDate();

        // 设置短信验证码缓存
        String verifyKey = Constants.SMS_CODE_KEY + phone;
        redisCache.setCacheObject(verifyKey, code, Constants.SMS_CODE_EXPIRATION, TimeUnit.MINUTES);

        //设置：每日手机号发送量
        String verifyCheckKey = Constants.SMS_CODE_CHECK_KEY + date + ":" + phone;
        int sendCount = redisCache.getCacheObject(verifyCheckKey) == null ? 0 :
                redisCache.getCacheObject(verifyCheckKey);

        redisCache.setCacheObject(verifyCheckKey, sendCount + 1, 24, TimeUnit.HOURS);

        //设置： 短信发送间隔 60*1000毫秒
        Long sendTime = System.currentTimeMillis(); // 当前计划发送时间

        String SendTimeKey = Constants.SMS_CODE_INTERVAL_KEY + phone;

        redisCache.setCacheObject(SendTimeKey, sendTime, Constants.SMS_CODE_LIMIT_INTERVAL.intValue(),
                TimeUnit.MILLISECONDS);


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
        log.debug(result);
        return JSONObject.parseObject(result);
    }

    @Override
    public List<DataMatchCompany> matchCompanyName(String companyName,int count) {

        List<DataMatchCompany> list = new ArrayList<>();

        List<String> companyNameList = interfaceService.queryCompanyName(companyName);
        for (String name : companyNameList){
            DataMatchCompany company = new DataMatchCompany();
            company.setName(name);
            company.setType(DataMatchCompany.TYPE_COMPANY);
            list.add(company);
        }

        if (list.size()>count){
            list = list.subList(0,count);
        }

        List<DataGTGSH> gtgshes = interfaceService.queryGTGSHByName(companyName);
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

    @Override
    public CreditReport getReport(String tyshxydm)  {
        TyshxydmDTO tyshxydmDTO = new TyshxydmDTO();
        tyshxydmDTO.setTyshxydm(tyshxydm);
        String url = domain + "api/loan/report";
        String response = restTemplate.postForObject(url, tyshxydmDTO, String.class);
        //解密
        try {
            response = new String(RSAUtil.decrypt(Base64Util.decode(response), secretKeyConfig.getPrivateKey()), secretKeyConfig.getCharset());
        } catch (Exception e) {
            throw new UserException(null, null,"解密异常");
        }
        CreditReportWrapper creditReportWrapper = JSON.parseObject(response,CreditReportWrapper.class);
        if (HttpStatus.SUCCESS == creditReportWrapper.getCode()){
            return creditReportWrapper.getData();
        }else {
            throw new UserException(null, null,creditReportWrapper.getMsg());
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
