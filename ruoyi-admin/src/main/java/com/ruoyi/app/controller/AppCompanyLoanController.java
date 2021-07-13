package com.ruoyi.app.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.DataCompanyLoan;
import com.ruoyi.system.service.IDataCompanyLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public AjaxResult add(@RequestBody DataCompanyLoan dataCompanyLoan)
    {
        //TODO:鉴权，鉴定闽政通 TOKEN、用户 ID 是否合法，剥离到shiro过滤器
        //TODO:requestBody 去掉省市区，信用码等；增加图片验证码uuid
        if (UserConstants.NOT_UNIQUE.equals(dataCompanyLoanService.checkCompanyNameUnique(dataCompanyLoan)))
        {
            return AjaxResult.error("新增'" + dataCompanyLoan.getCompanyName() + "'失败，该企业名称已存在");
        }

        //TODO:组装区域等数据
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
