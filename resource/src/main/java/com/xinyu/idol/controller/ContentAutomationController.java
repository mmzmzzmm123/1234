package com.xinyu.idol.controller;

import com.xinyu.idol.common.annotation.Log;
import com.xinyu.idol.common.core.domain.AjaxResult;
import com.xinyu.idol.common.enums.BusinessType;
import com.xinyu.idol.pojo.vo.AddContentVo;
import com.xinyu.idol.pojo.vo.PageContentReq;
import com.xinyu.idol.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 资源冗余大表 前端控制器
 * </p>
 *
 * @author yu.li
 * @since 2023-10-17
 */
@RestController
@RequestMapping("/content/automation")
public class ContentAutomationController {

    @Autowired
    IContentService contentService;

    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/test/list/all",method = RequestMethod.GET)
    AjaxResult listAllTest(){
        return AjaxResult.success(contentService.testList());
    }

    @RequestMapping(value = "/addContent",method = RequestMethod.POST)
    AjaxResult addContent(@RequestBody AddContentVo addContentVo){
        contentService.addContent(addContentVo);
        return AjaxResult.success();
    }

}
