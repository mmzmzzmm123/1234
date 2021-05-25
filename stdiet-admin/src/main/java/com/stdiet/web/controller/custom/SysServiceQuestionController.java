package com.stdiet.web.controller.custom;

import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.custom.domain.SysServicesQuestion;
import com.stdiet.custom.service.ISysServicesQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/services/question")
public class SysServiceQuestionController extends BaseController {

    @Autowired
    private ISysServicesQuestionService sysServicesQuestionService;

    @PostMapping("/list")
    public AjaxResult list(@RequestBody SysServicesQuestion sysServicesQuestion) {
        return AjaxResult.success(sysServicesQuestionService.selectSysServicesQuestionByUserIdAndRole(sysServicesQuestion));
    }

    @PutMapping("/updateStatus")
    public AjaxResult status(@RequestBody SysServicesQuestion sysServicesQuestion) {
        return toAjax(sysServicesQuestionService.updateSysServicesQuestionStatus(sysServicesQuestion));
    }

    @PostMapping("/reply")
    public AjaxResult reply(@RequestBody SysServicesQuestion servicesQuestion) {
        return toAjax(sysServicesQuestionService.inserSysServicesQuestionReply(servicesQuestion));
    }
}
