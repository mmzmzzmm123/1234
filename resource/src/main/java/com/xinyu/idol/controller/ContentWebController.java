package com.xinyu.idol.controller;


import com.xinyu.idol.common.annotation.Log;
import com.xinyu.idol.common.core.domain.AjaxResult;
import com.xinyu.idol.common.enums.BusinessType;
import com.xinyu.idol.pojo.vo.*;
import com.xinyu.idol.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content/web")
public class ContentWebController {

    @Autowired
    IContentService contentService;



    @RequestMapping(value = "/pageContent",method = RequestMethod.POST)
    AjaxResult pageContent(@RequestBody PageContentReq pageContentReq){
        return AjaxResult.success(contentService.pageContent(pageContentReq));
    }

    @RequestMapping(value = "/updateContent",method = RequestMethod.POST)
    AjaxResult updateContent(@RequestBody UpdateContentWebListReq updateContentWebListReq){
        contentService.updateContent(updateContentWebListReq);
        return AjaxResult.success();
    }




}
