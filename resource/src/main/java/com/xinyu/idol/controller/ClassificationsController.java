package com.xinyu.idol.controller;

import com.xinyu.idol.common.annotation.Anonymous;
import com.xinyu.idol.common.core.domain.AjaxResult;
import com.xinyu.idol.pojo.vo.ClassificationsReq;
import com.xinyu.idol.service.IClassificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 多级分类树表 前端控制器
 * </p>
 *
 * @author yu.li
 * @since 2023-10-16
 */
@RestController
@RequestMapping("/classifications")
public class ClassificationsController {


    @Autowired
    private IClassificationsService classificationsService;

    @RequestMapping(value = "/web/listClassifications",method = RequestMethod.POST)
    AjaxResult classifications(@RequestBody ClassificationsReq classificationsReq){
        return AjaxResult.success(classificationsService.classifications(classificationsReq));
    }

    @RequestMapping(value = "/web/listClassificationsByLevel",method = RequestMethod.GET)
    AjaxResult classifications(@RequestParam String level){
        return AjaxResult.success(classificationsService.classificationsByLevel(level));
    }

}
