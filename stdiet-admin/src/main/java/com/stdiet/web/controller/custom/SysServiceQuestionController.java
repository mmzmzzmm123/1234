package com.stdiet.web.controller.custom;

import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.core.page.TableDataInfo;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.SysServicesQuestion;
import com.stdiet.custom.service.ISysServicesQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services/question")
public class SysServiceQuestionController extends BaseController {

    @Autowired
    private ISysServicesQuestionService sysServicesQuestionService;

    @GetMapping("/list")
    public TableDataInfo list(SysServicesQuestion sysServicesQuestion) {
        startPage();
        return getDataTable(sysServicesQuestionService.selectSysServicesQuestionByUserIdAndRole(sysServicesQuestion));
    }

//    @PutMapping("/update/status")
//    public AjaxResult status(@RequestBody SysServicesQuestion sysServicesQuestion) {
//        return toAjax(sysServicesQuestionService.updateSysServicesQuestionStatus(sysServicesQuestion));
//    }

    @PostMapping("/reply")
    public AjaxResult reply(@RequestBody SysServicesQuestion servicesQuestion) {
        int row = sysServicesQuestionService.inserSysServicesQuestionReply(servicesQuestion);
        if (row > 0) {
            // 更新customer未读，id不能有值
            servicesQuestion.setRead(0);
            sysServicesQuestionService.updateSysServicesQuestionStatus(servicesQuestion);
        }
        return toAjax(row);
    }

    @GetMapping("/detail")
    public AjaxResult detail(@RequestParam String queId, @RequestParam Long id) {
        List<SysServicesQuestion> questions = sysServicesQuestionService.selectSysServicesQuestionSessionByQueId(queId);
        if (StringUtils.isNotNull(questions)) {
            // 更新问题对应角色的状态
            SysServicesQuestion servicesQuestion = new SysServicesQuestion();
            servicesQuestion.setRead(1);
            servicesQuestion.setId(id);
            sysServicesQuestionService.updateSysServicesQuestionStatus(servicesQuestion);
        }
        return AjaxResult.success(questions);
    }
}
