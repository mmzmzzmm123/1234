package com.ruoyi.app.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.model.DataCompanyLoaBody;
import com.ruoyi.system.service.IDataCompanyLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 企业贷款信息Controller
 * 
 * @author genius
 * @date 2021-07-13
 */
@RestController
@RequestMapping("/app/loan")
public class AppCompanyLoanController extends BaseController
{
    @Autowired
    private IDataCompanyLoanService dataCompanyLoanService;

    /**
     * 新增企业贷款信息
     */
    @Log(title = "企业贷款信息", businessType = BusinessType.INSERT)
    // 以下两行支持表单提交
    // @PostMapping(value = "/add",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    // public @ResponseBody AjaxResult add( DataCompanyLoan dataCompanyLoan)
    @PostMapping( "/add")
    public AjaxResult add(@RequestBody DataCompanyLoaBody dataCompanyLoan)
    {
        if (UserConstants.NOT_UNIQUE.equals(dataCompanyLoanService.checkCompanyNameUnique(dataCompanyLoan.getCompanyName())))
        {
            return AjaxResult.error("新增'" + dataCompanyLoan.getCompanyName() + "'失败，该企业名称已存在");
        }

        return toAjax(dataCompanyLoanService.insertDataCompanyLoan(dataCompanyLoan));
    }

    /**
     * 获取手机验证码
     */
    @GetMapping("/getSMSCode")
    public AjaxResult getCode(@RequestParam String phone){
        dataCompanyLoanService.senSmsCode(phone);
        return AjaxResult.success();
    }

    /**
     * 测试接口是否打通，随时可以删除
     * @return
     */
    @GetMapping("/test")
    public AjaxResult test()
    {
        return AjaxResult.success();
    }
}
