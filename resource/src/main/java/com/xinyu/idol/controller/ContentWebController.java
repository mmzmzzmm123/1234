package com.xinyu.idol.controller;


import com.aliyuncs.exceptions.ClientException;
import com.xinyu.idol.common.annotation.Log;
import com.xinyu.idol.common.core.domain.AjaxResult;
import com.xinyu.idol.common.enums.BusinessType;
import com.xinyu.idol.pojo.vo.*;
import com.xinyu.idol.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/content/web")
public class ContentWebController {

    @Autowired
    IContentService contentService;

    @Value("${aliyun.oss.domain}")
    private String resourceDomain;



    @RequestMapping(value = "/pageContent",method = RequestMethod.POST)
    AjaxResult pageContent(@RequestBody PageContentReq pageContentReq){
        return AjaxResult.success(contentService.pageContent(pageContentReq));
    }

    @RequestMapping(value = "/updateContent",method = RequestMethod.POST)
    AjaxResult updateContent(@RequestBody UpdateContentWebListReq updateContentWebListReq){
        contentService.updateContent(updateContentWebListReq);
        return AjaxResult.success();
    }

    @RequestMapping(value = "/download/domains",method = RequestMethod.GET)
    AjaxResult updateContent(){
        return AjaxResult.success(DownloadDomainsResp.builder().resource(resourceDomain).build());
    }

    @RequestMapping(value = "/file/uploadIcon",method = RequestMethod.POST)
    AjaxResult uploadIcon(MultipartFile file) throws ClientException, IOException {
        return AjaxResult.success(contentService.uploadIcon(file));
    }

    @RequestMapping(value = "/file/uploadOrigins",method = RequestMethod.POST)
    AjaxResult pushToEnv(List<MultipartFile> file) throws IOException {
        return AjaxResult.success(contentService.uploadOrigins(file));
    }

    @RequestMapping(value = "/pull",method = RequestMethod.POST)
    AjaxResult pushToEnv(@RequestBody PullResourceFromEnvReq pullResourceFromEnvReq)  {
        contentService.pullResourceFromEnv(pullResourceFromEnvReq);
        return AjaxResult.success();
    }




}
