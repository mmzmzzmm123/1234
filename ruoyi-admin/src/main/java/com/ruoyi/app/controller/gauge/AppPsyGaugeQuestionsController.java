package com.ruoyi.app.controller.gauge;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.gauge.domain.PsyGaugeQuestions;
import com.ruoyi.gauge.service.IPsyGaugeQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 心理测评问题Controller
 * 
 * @author ruoyi
 * @date 2022-09-06
 */
@RestController
@RequestMapping("/app/gauge/questions")
public class AppPsyGaugeQuestionsController extends BaseController
{
    @Autowired
    private IPsyGaugeQuestionsService psyGaugeQuestionsService;

    /**
     * 查询心理测评问题列表
     */
    @PreAuthorize("@ss.hasPermi('gauge:questions:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyGaugeQuestions psyGaugeQuestions)
    {
        startPage();
        List<PsyGaugeQuestions> list = psyGaugeQuestionsService.selectPsyGaugeQuestionsList(psyGaugeQuestions);
        return getDataTable(list);
    }


}
