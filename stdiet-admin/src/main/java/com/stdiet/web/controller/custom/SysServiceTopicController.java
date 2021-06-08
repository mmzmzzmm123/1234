package com.stdiet.web.controller.custom;

import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.core.page.TableDataInfo;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.SysServicesTopic;
import com.stdiet.custom.service.ISysServicesTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services/topic")
public class SysServiceTopicController extends BaseController {

    @Autowired
    private ISysServicesTopicService servicesTopicService;

    @GetMapping("/list")
    public TableDataInfo list(SysServicesTopic topic) {
        return getDataTable(servicesTopicService.selectTopicListByUid(topic));
    }

    @GetMapping("/customers")
    public TableDataInfo customers(SysServicesTopic topic) {
        startPage();
        return getDataTable(servicesTopicService.selectCustomerListByUserIdAndRole(topic));
    }


//    @PutMapping("/update/status")
//    public AjaxResult status(@RequestBody SysServicesQuestion sysServicesQuestion) {
//        return toAjax(sysServicesQuestionService.updateSysServicesQuestionStatus(sysServicesQuestion));
//    }

    @PostMapping("/reply")
    public AjaxResult reply(@RequestBody SysServicesTopic topic) {
        return AjaxResult.success(servicesTopicService.inserSysServicesTopicReply(topic));
    }

    @PostMapping("/comment")
    public AjaxResult comment(@RequestBody SysServicesTopic topic) {
        return AjaxResult.success(servicesTopicService.inserSysServicesTopicComment(topic));
    }

    @GetMapping("/detail")
    public AjaxResult detail(@RequestParam String topicId, @RequestParam String id) {
        List<SysServicesTopic> questions = servicesTopicService.selectSysServicesTopicSessionByTopicId(topicId);
        if (StringUtils.isNotNull(questions)) {
            // 更新问题对应角色的状态
            SysServicesTopic status = new SysServicesTopic();
            status.setRead(1);
            status.setId(id);
            servicesTopicService.updateSysServicesTopicStatus(status);
        }
        return AjaxResult.success(questions);
    }
}
