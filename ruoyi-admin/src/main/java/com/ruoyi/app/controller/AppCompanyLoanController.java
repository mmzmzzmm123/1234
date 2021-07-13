package com.ruoyi.app.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.DataCompanyLoan;
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
    @PostMapping("/add")
    public AjaxResult add(@RequestBody DataCompanyLoan dataCompanyLoan)
    {
        //TODO:鉴权，鉴定闽政通 TOKEN、用户 ID 是否合法，剥离到shiro过滤器
        return toAjax(dataCompanyLoanService.insertDataCompanyLoan(dataCompanyLoan));
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
