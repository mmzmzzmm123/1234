package com.ruoyi.app.controller.gauge;

import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.framework.web.service.AppTokenService;
import com.ruoyi.gauge.domain.PsyGaugeMultiSetting;
import com.ruoyi.gauge.domain.PsyGaugeQuestions;
import com.ruoyi.gauge.service.IPsyGaugeMultiSettingService;
import com.ruoyi.gauge.service.IPsyGaugeQuestionsService;
import com.ruoyi.gauge.vo.PsyQuestionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 心理测评问题Controller
 * 
 * @author ruoyi
 * @date 2022-09-06
 */
@RestController
@RequestMapping("/app/gauge/questions")
@Api(value = "AppPsyGaugeQuestionsController" ,tags = {"测评问题api"})
public class AppPsyGaugeQuestionsController extends BaseController
{
    @Autowired
    private IPsyGaugeQuestionsService psyGaugeQuestionsService;

    @Autowired
    private IPsyGaugeMultiSettingService psyGaugeMultiSettingService;

    @Autowired
    private AppTokenService appTokenService;

    /**
     * 查询心理测评问题列表
     */
//    @PreAuthorize("@ss.hasPermi('gauge:questions:list')")
    @GetMapping("/list")
    @ApiOperation("查询测评问题列表")
    @RateLimiter
    public AjaxResult list(PsyGaugeQuestions psyGaugeQuestions ,HttpServletRequest request)
    {
        LoginDTO loginUser = appTokenService.getLoginUser(request);
        Integer userId = loginUser.getUserId();
        List<PsyQuestionVO> list = psyGaugeQuestionsService.appQueryQuesList(psyGaugeQuestions ,userId);
        return AjaxResult.success(list);
    }

    @GetMapping("/lats")
    @ApiOperation("查询测评问题列表") // Integer gaugeId
    @RateLimiter
    public AjaxResult lats(PsyGaugeMultiSetting psyGaugeMultiSetting)
    {
        return AjaxResult.success(psyGaugeMultiSettingService.selectPsyGaugeMultiSettingList(psyGaugeMultiSetting));
    }

    @GetMapping("/wrongs/{orderId}/{gaugeId}")
    @ApiOperation("查询测评问题列表")
    @RateLimiter
    public AjaxResult wrongs(@PathVariable("orderId") Integer orderId, @PathVariable("gaugeId") Integer gaugeId)
    {
        return AjaxResult.success(psyGaugeQuestionsService.wrongs(orderId, gaugeId));
    }

}
