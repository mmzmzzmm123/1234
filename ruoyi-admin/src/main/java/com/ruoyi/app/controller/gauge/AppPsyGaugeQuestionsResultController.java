package com.ruoyi.app.controller.gauge;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.framework.web.service.AppTokenService;
import com.ruoyi.gauge.domain.PsyGaugeQuestionsResult;
import com.ruoyi.gauge.service.IPsyGaugeQuestionsResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 心理测评问题结果Controller
 * 
 * @author ruoyi
 * @date 2022-09-10
 */
@RestController
@RequestMapping("/app/gauge/result")
@Api("测评结果提交")
public class AppPsyGaugeQuestionsResultController extends BaseController
{
    @Autowired
    private IPsyGaugeQuestionsResultService psyGaugeQuestionsResultService;

    @Autowired
    private AppTokenService appTokenService;

    /**
     * 新增心理测评问题结果
     */
    @PostMapping
    @ApiOperation("提交测评结果")
    public AjaxResult add(@RequestBody @Validated PsyGaugeQuestionsResult psyGaugeQuestionsResult , HttpServletRequest request)
    {
        LoginDTO loginUser = appTokenService.getLoginUser(request);
        return toAjax(psyGaugeQuestionsResultService.commitResult(psyGaugeQuestionsResult ,loginUser));
    }
}
