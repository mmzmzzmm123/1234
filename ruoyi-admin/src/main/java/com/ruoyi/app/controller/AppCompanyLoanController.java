package com.ruoyi.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.framework.interceptor.impl.AppInterceptor;
import com.ruoyi.system.domain.DataCompanyLoan;
import com.ruoyi.system.domain.model.DataCompanyLoanBody;
import com.ruoyi.system.domain.model.DataMatchCompany;
import com.ruoyi.system.domain.model.PhoneAndCode;
import com.ruoyi.system.service.IDataCompanyLoanOracleService;
import com.ruoyi.system.service.IDataCompanyLoanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 企业贷款信息Controller
 * 
 * @author genius
 * @date 2021-07-13
 */
@RestController
@Api(value = "企业贷款信息接口")
@RequestMapping("/app/loan")
public class AppCompanyLoanController extends BaseController
{
    @Autowired
    private IDataCompanyLoanService dataCompanyLoanService;
    @Autowired
    private IDataCompanyLoanOracleService dataCompanyLoanOracleService;

    /**
     * 新增企业贷款信息
     */
    @Log(title = "企业贷款信息", businessType = BusinessType.INSERT)
    // 以下两行支持表单提交
    // @PostMapping(value = "/add",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    // public @ResponseBody AjaxResult add( DataCompanyLoan dataCompanyLoan)
    @PostMapping( "/add")
    @ApiOperation(value = "添加企业贷款信息接口")
    public AjaxResult add(@RequestBody DataCompanyLoanBody dataCompanyLoan)
    {
//        if (UserConstants.NOT_UNIQUE.equals(dataCompanyLoanService.checkCompanyNameUnique(dataCompanyLoan.getCompanyName())))
//        {
//            return AjaxResult.error("新增'" + dataCompanyLoan.getCompanyName() + "'失败，该企业名称已存在");
//        }

        return toAjax(dataCompanyLoanService.insertDataCompanyLoan(dataCompanyLoan));
    }

    /**
     * 获取手机验证码
     */
    @GetMapping("/getVerifyCode")
    @ApiOperation(value = "获取验证码接口")
    public AjaxResult getCode(@RequestParam String phone){
        dataCompanyLoanService.senSmsCode(phone);
        return AjaxResult.success("操作成功");
    }

    /**
     * 验证手机验证码
     */
    @PostMapping("/checkVerifyCode")
    @ApiOperation(value = "校验验证码接口")
    public AjaxResult checkVerifyCode(@RequestBody PhoneAndCode phoneAndCode){
        dataCompanyLoanService.checkSMSCode(phoneAndCode.getPhone(),phoneAndCode.getCode());
        return AjaxResult.success("验证成功");
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/user")
    @ApiOperation(value = "获取用户信息")
    public JSONObject getUser(){

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        String token = request.getHeader(AppInterceptor.HEADER_MZT_USER_TOKEN);
        String userId = request.getHeader(AppInterceptor.HEADER_MZT_USER_ID);

        JSONObject jsonObject = dataCompanyLoanService.getUserInfo(userId,token);
        return jsonObject;
    }

    /**
     * 根据企业名称匹配对应的企业完整名称
     */
//    @GetMapping("/match/v2")
    @ApiOperation(value = "根据企业名称匹配对应的企业完整名称")
    public TableDataInfo matchV2(@RequestParam String companyName)
    {
        List<DataMatchCompany> companyList = dataCompanyLoanService.matchCompanyName(companyName,20);
        if (companyList.size()>20){
            companyList = companyList.subList(0,20);
        }
        return getDataTable(companyList);
    }

//    @GetMapping("/listOracle")
    public TableDataInfo listOracle(DataCompanyLoan dataCompanyLoan)
    {
        startPage();
        List<DataCompanyLoan> list = dataCompanyLoanOracleService.selectDataCompanyLoanList(dataCompanyLoan);
        return getDataTable(list);
    }
}
